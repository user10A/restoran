package restoran.api;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import restoran.dto.user.SimpleResponse;
import restoran.service.Impl.ReservationServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationApi {
    private final ReservationServiceImpl reservationService;

    @PutMapping("/process/{id}")
    public SimpleResponse processReservation(@PathVariable Long id, @RequestParam boolean approve) {
        reservationService.processReservation(id, approve);
        return new SimpleResponse("Reservation processed", HttpStatus.OK);
    }
}
