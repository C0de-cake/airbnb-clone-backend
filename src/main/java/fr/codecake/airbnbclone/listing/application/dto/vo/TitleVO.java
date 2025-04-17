package fr.codecake.airbnbclone.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record TitleVO(@NotNull(message = "Title value must be present") String value) {
}
