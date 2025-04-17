package fr.codecake.airbnbclone.listing.application.dto.sub;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record PictureDTO(
        @NotNull byte[] file,
        @NotNull String fileContentType,
        @NotNull boolean isCover
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDTO that = (PictureDTO) o;
        return isCover == that.isCover && Objects.equals(fileContentType, that.fileContentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileContentType, isCover);
    }

    @Override
    public String toString() {
        return "PictureDTO{" +
                "fileContentType='" + fileContentType + '\'' +
                ", isCover=" + isCover +
                '}';
    }
}
