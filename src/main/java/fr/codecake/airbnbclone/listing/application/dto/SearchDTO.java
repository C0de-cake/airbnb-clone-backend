package fr.codecake.airbnbclone.listing.application.dto;

import fr.codecake.airbnbclone.booking.application.dto.BookedDateDTO;
import fr.codecake.airbnbclone.listing.application.dto.sub.ListingInfoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record SearchDTO(@Valid BookedDateDTO dates,
                        @Valid ListingInfoDTO infos,
                        @NotEmpty String location) {
}
