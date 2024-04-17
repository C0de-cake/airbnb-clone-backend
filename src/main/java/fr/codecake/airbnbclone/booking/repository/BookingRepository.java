package fr.codecake.airbnbclone.booking.repository;

import fr.codecake.airbnbclone.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
