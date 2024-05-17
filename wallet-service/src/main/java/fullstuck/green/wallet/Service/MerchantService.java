package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Entity.Merchant;

import java.math.BigDecimal;

public interface MerchantService {
    Merchant createMerchant(MerchantDTO merchantDTO, String id);
    void updateMerchant(MerchantDTO merchantDTO);
    void updateBalance(String id, BigDecimal amount, int type);
    void deleteMerchant(String name);
    void merchantGreen(String name);
    Merchant getMerchantData(String id);
    Merchant getByName(String name);
}
