package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.TransactionDTO;
import fullstuck.green.wallet.Model.Entity.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(TransactionDTO transactionDTO);

    // Get All for Current User
    List<Transaction> getAllTransaction();
    Transaction getTransactionById(String id);
}