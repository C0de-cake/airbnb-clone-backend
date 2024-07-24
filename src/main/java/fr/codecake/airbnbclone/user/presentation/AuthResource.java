package fr.codecake.airbnbclone.user.presentation;

import fr.codecake.airbnbclone.user.application.UserService;
import fr.codecake.airbnbclone.user.application.dto.ReadUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final UserService userService;

    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ReadUserDTO> getAuthenticatedUser(
            @AuthenticationPrincipal Jwt jwtToken, @RequestParam boolean forceResync) {
        if(jwtToken == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            userService.syncWithIdp(jwtToken, forceResync);
            ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
            return new ResponseEntity<>(connectedUser, HttpStatus.OK);
        }
    }
}
