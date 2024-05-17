package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.NewPartnerDTO;
import fullstuck.green.wallet.Model.Entity.TopupPartner;

import java.math.BigDecimal;

public interface TopupPartnerService {
    void registerNewPartner(NewPartnerDTO newPartnerDTO);
    void updateBalance(String id, BigDecimal amount);
    void deletePartner(String id);
    Boolean updatePartner(NewPartnerDTO newPartnerDTO);
    String getPartnerId(String id);
    TopupPartner getPartnerData(String id);
    TopupPartner getPartnerByName(String name);
}