package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByuser(User user);
}