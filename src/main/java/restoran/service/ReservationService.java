package restoran.service;

import restoran.entity.Restaurant;
import restoran.entity.User;

public interface ReservationService {
    void submitReservation(User user, Restaurant restaurant);
    void processReservation(Long reservationId, boolean approve);
}
