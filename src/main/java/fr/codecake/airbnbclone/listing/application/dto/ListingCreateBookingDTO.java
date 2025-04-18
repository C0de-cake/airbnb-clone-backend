package fr.codecake.airbnbclone.listing.application.dto;

import fr.codecake.airbnbclone.listing.application.dto.vo.PriceVO;

import java.util.UUID;

public record ListingCreateBookingDTO(
        UUID listingPublicId, PriceVO price) {
}
