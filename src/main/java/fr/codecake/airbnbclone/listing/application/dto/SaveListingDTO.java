package fr.codecake.airbnbclone.listing.application.dto;

import fr.codecake.airbnbclone.listing.application.dto.sub.DescriptionDTO;
import fr.codecake.airbnbclone.listing.application.dto.sub.ListingInfoDTO;
import fr.codecake.airbnbclone.listing.application.dto.sub.PictureDTO;
import fr.codecake.airbnbclone.listing.application.dto.vo.PriceVO;
import fr.codecake.airbnbclone.listing.domain.BookingCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SaveListingDTO {

    @NotNull
    BookingCategory category;

    @NotNull String location;

    @NotNull @Valid
    ListingInfoDTO infos;

    @NotNull @Valid
    DescriptionDTO description;

    @NotNull @Valid
    PriceVO price;

    @NotNull
    List<PictureDTO> pictures;

    public BookingCategory getCategory() {
        return category;
    }

    public void setCategory(BookingCategory category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ListingInfoDTO getInfos() {
        return infos;
    }

    public void setInfos(ListingInfoDTO infos) {
        this.infos = infos;
    }

    public DescriptionDTO getDescription() {
        return description;
    }

    public void setDescription(DescriptionDTO description) {
        this.description = description;
    }

    public PriceVO getPrice() {
        return price;
    }

    public void setPrice(PriceVO price) {
        this.price = price;
    }

    public List<PictureDTO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureDTO> pictures) {
        this.pictures = pictures;
    }
}
