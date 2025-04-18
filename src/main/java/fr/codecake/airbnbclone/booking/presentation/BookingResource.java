package fr.codecake.airbnbclone.booking.presentation;

import fr.codecake.airbnbclone.booking.application.BookingService;
import fr.codecake.airbnbclone.booking.application.dto.BookedDateDTO;
import fr.codecake.airbnbclone.booking.application.dto.BookedListingDTO;
import fr.codecake.airbnbclone.booking.application.dto.NewBookingDTO;
import fr.codecake.airbnbclone.infrastructure.config.SecurityUtils;
import fr.codecake.airbnbclone.sharedkernel.service.State;
import fr.codecake.airbnbclone.sharedkernel.service.StatusNotification;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingResource {

    private final BookingService bookingService;

    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@Valid @RequestBody NewBookingDTO newBookingDTO) {
        State<Void, String> createState = bookingService.create(newBookingDTO);
        if (createState.getStatus().equals(StatusNotification.ERROR)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, createState.getError());
            return ResponseEntity.of(problemDetail).build();
        } else {
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("check-availability")
    public ResponseEntity<List<BookedDateDTO>> checkAvailability(@RequestParam UUID listingPublicId) {
        return ResponseEntity.ok(bookingService.checkAvailability(listingPublicId));
    }

    @GetMapping("get-booked-listing")
    public ResponseEntity<List<BookedListingDTO>> getBookedListing() {
        return ResponseEntity.ok(bookingService.getBookedListing());
    }

    @DeleteMapping("cancel")
    public ResponseEntity<UUID> cancel(@RequestParam UUID bookingPublicId,
                                       @RequestParam UUID listingPublicId,
                                       @RequestParam boolean byLandlord) {
        State<UUID, String> cancelState = bookingService.cancel(bookingPublicId, listingPublicId, byLandlord);
        if (cancelState.getStatus().equals(StatusNotification.ERROR)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, cancelState.getError());
            return ResponseEntity.of(problemDetail).build();
        } else {
            return ResponseEntity.ok(bookingPublicId);
        }
    }

    @GetMapping("get-booked-listing-for-landlord")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<List<BookedListingDTO>> getBookedListingForLandlord() {
        return ResponseEntity.ok(bookingService.getBookedListingForLandlord());
    }
}
