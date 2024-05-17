package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Response.CustomHistoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByuser(User user);

    @Query(
            nativeQuery = true,
            value = "SELECT t.id, t.user_id as userId, td.type as transType, td.created_at as transDate, " +
                    "td.description as message, td.target_id as targetId, td.source_id as source, " +
                    "td.amount, td.curr_balance as currBalance " +
                    "FROM master_transaction t " +
                    "LEFT JOIN transaction_detail td " +
                    "ON t.trans_detail_id = td.id " +
                    "WHERE t.user_id=:id"
    )
    List<CustomHistoryInterface> findAllCustom(@Param("id")String id);
}