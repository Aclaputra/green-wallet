package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.*;
//import fullstuck.green.wallet.Model.Response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    // Get All for Current User
    List<Transaction> getAllTransaction(String userIdFromToken);
    List<CustomHistoryInterface> findAllCustom(String userIdFromToken);
    TransferResponse transfer(TransferRequest req, String accountDetailIdToken);
    TopupResponse topUp(TopUpRequest req, String accountDetailIdToken);
    TransferResponse payment(TransferRequest req, String accountDetailIdToken);
    TransferResponse merchantTransfer(TransferRequest req, String accountDetailIdToken);
    HistoryDetailResponse getById(String id);
}