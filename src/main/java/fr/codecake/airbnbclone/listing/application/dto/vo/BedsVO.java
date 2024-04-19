package fr.codecake.airbnbclone.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record BedsVO(@NotNull(message = "beds value must be present") int value) {
}
