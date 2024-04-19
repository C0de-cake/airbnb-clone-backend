package fr.codecake.airbnbclone.listing.application;

import fr.codecake.airbnbclone.listing.application.dto.DisplayCardListingDTO;
import fr.codecake.airbnbclone.listing.domain.BookingCategory;
import fr.codecake.airbnbclone.listing.domain.Listing;
import fr.codecake.airbnbclone.listing.mapper.ListingMapper;
import fr.codecake.airbnbclone.listing.repository.ListingRepository;
import fr.codecake.airbnbclone.user.application.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final ListingRepository listingRepository;

    private final ListingMapper listingMapper;

    private final UserService userService;


    public TenantService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userService = userService;
    }

    public Page<DisplayCardListingDTO> getAllByCategory(Pageable pageable, BookingCategory category) {
        Page<Listing> allOrBookingCategory;
        if (category == BookingCategory.ALL) {
            allOrBookingCategory = listingRepository.findAllWithCoverOnly(pageable);
        } else {
            allOrBookingCategory = listingRepository.findAllByBookingCategoryWithCoverOnly(pageable, category);
        }

        return allOrBookingCategory.map(listingMapper::listingToDisplayCardListingDTO);
    }
}
