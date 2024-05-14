package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.TransDetail;
import fullstuck.green.wallet.Model.Entity.Transaction;

public interface TransDetailService {
    void saveTransactionDetail(TransDetail transDetail);
}