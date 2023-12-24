package restoran.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restoran.entity.Reservation;
import restoran.entity.Restaurant;
import restoran.entity.User;
import restoran.exceptions.NotFoundException;
import restoran.repo.ReservationRepo;
import restoran.repo.UserRepo;
import restoran.service.ReservationService;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepo reservationRepository;
    private final UserRepo userRepo;
    @Override
    public void submitReservation(User user, Restaurant restaurant) {
        Reservation reservation = Reservation.builder()
                .user(user)
                .restaurant(restaurant)
                .createdAt(ZonedDateTime.now())
                .approved(false)
                .build();

        reservationRepository.save(reservation);
    }
    @Override
    public void processReservation(Long reservationId, boolean approve) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new NotFoundException("Reservation not found by id:"+reservationId));
        if (approve) {
                User user=reservation.getUser();
                userRepo.delete(user);
               reservationRepository.delete(reservation);
            }else {
                reservation.setApproved(true);
                reservationRepository.save(reservation);
            }

    }


}
