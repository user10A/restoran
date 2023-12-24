package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.cheque.ChequeResponse;
import restoran.entity.Cheque;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque,Long> {

    @Query("select new restoran.dto.cheque.ChequeResponse2(u.cMenus) from Cheque u")
    List<ChequeResponse> getALL();

    @Query("select c from Cheque c where c.cUser.email= :email")
    List<Cheque> getChequeByWaiterEmail(String email);

}
