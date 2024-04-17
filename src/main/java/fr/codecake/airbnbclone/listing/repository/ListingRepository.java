package fr.codecake.airbnbclone.listing.repository;

import fr.codecake.airbnbclone.listing.domain.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
