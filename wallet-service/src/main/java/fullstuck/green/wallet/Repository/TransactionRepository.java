package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByuser(User user);

    // NOT WORK
//    @Query(
//            nativeQuery = true,
//            value = "SELECT id, transDetail_id FROM master_transaction WHERE user_id=:param"
//    )
//    List<Transaction> findByUserId(String id);
}