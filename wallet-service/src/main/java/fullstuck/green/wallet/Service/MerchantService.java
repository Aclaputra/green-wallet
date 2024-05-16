package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Entity.Merchant;

import java.util.List;

public interface MerchantService {
    void createMerchant(MerchantDTO merchantDTO, String id);
    void updateMerchant(MerchantDTO merchantDTO);
    void updateBalance(BalanceDTO balanceDTO);
    void deleteMerchant(String name);
    void merchantGreen(String name);
    Merchant getMerchantData(String name);
}
