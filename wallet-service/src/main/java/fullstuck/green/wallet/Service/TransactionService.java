package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.TransactionDTO;
import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.TopupResponse;
//import fullstuck.green.wallet.Model.Response.TransactionResponse;
import fullstuck.green.wallet.Model.Response.TransferResponse;

import java.util.List;

public interface TransactionService {
//    void addTransaction(TransactionDTO transactionDTO);

    // Get All for Current User
    List<Transaction> getAllTransaction(String userIdFromToken);
    //    Transaction getTransactionById(String id);

    /*
    User : Topup dana ke akun, transfer ke user lain, bayar merchant ( 3 )
    Merchant : Transfer dana dari akun merchant ke akun pribad ( 1 )
     */
    TransferResponse transfer(TransferRequest req, String accountDetailIdToken);
    TopupResponse topUp(TopUpRequest req, String accountDetailIdToken);
    TransferResponse payment(TransferRequest req, String accountDetailIdToken);
    TransferResponse merchantTransfer(TransferRequest req, String accountDetailIdToken);
    Transaction getById(String id);
}