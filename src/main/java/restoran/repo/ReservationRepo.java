package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restoran.entity.Reservation;
@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Long> {
}
