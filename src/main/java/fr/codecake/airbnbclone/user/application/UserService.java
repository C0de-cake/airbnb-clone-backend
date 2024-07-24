package fr.codecake.airbnbclone.user.application;

import com.auth0.json.auth.UserInfo;
import fr.codecake.airbnbclone.infrastructure.config.SecurityUtils;
import fr.codecake.airbnbclone.user.application.dto.ReadUserDTO;
import fr.codecake.airbnbclone.user.domain.User;
import fr.codecake.airbnbclone.user.mapper.UserMapper;
import fr.codecake.airbnbclone.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final String UPDATED_AT_KEY = "updated_at";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Auth0Service auth0Service;

    public UserService(UserRepository userRepository, UserMapper userMapper, Auth0Service auth0Service) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.auth0Service = auth0Service;
    }

    @Transactional(readOnly = true)
    public ReadUserDTO getAuthenticatedUserFromSecurityContext() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = auth0Service.getUserInfo(principal);
        User user = SecurityUtils.mapOauth2AttributesToUser(userInfo.getValues());
        return getByEmail(user.getEmail()).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Optional<ReadUserDTO> getByEmail(String email) {
        Optional<User> oneByEmail = userRepository.findOneByEmail(email);
        return oneByEmail.map(userMapper::readUserDTOToUser);
    }

    public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
        UserInfo userInfo = auth0Service.getUserInfo(jwtToken);
        Map<String, Object> attributes = userInfo.getValues();
        User user = SecurityUtils.mapOauth2AttributesToUser(attributes);
        Optional<User> existingUser = userRepository.findOneByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (attributes.get(UPDATED_AT_KEY) != null) {
                Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if (attributes.get(UPDATED_AT_KEY) instanceof Instant instant) {
                    idpModifiedDate = instant;
                } else {
                    idpModifiedDate = Instant.parse(attributes.get(UPDATED_AT_KEY).toString());
                }
                if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                    updateUser(user);
                }
            }
        } else {
            userRepository.saveAndFlush(user);
        }
    }

    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findOneByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setAuthorities(user.getAuthorities());
            userToUpdate.setImageUrl(user.getImageUrl());
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    public Optional<ReadUserDTO> getByPublicId(UUID publicId) {
        Optional<User> oneByPublicId = userRepository.findOneByPublicId(publicId);
        return oneByPublicId.map(userMapper::readUserDTOToUser);
    }

}
