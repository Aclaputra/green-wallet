package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Entity.Merchant;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Strings.MerchantEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;

    @Override
    public void createMerchant(MerchantDTO merchantDTO) {
        MerchantEnum temp = switch (merchantDTO.getType()) {
            case 0 -> MerchantEnum.MINI_MARKET;
            case 1 -> MerchantEnum.DROP_SHIPPER;
            case 2 -> MerchantEnum.RESELLER;
            case 3 -> MerchantEnum.OTHER;
            default -> null;
        };

        Merchant merchant = Merchant.builder()
                .name(merchantDTO.getName())
                .type(temp)
                .isGreen(Boolean.FALSE)
                .balance(new BigDecimal("0.0"))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .deleted_at(null)
                .isDeleted(Boolean.FALSE)
                .build();
        merchantRepository.save(merchant);
    }

    @Override
    public void updateMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = merchantRepository.findByname(merchantDTO.getName());
        if(merchant != null){
            merchant.setName(merchantDTO.getName());
            switch (merchantDTO.getType()){
                case 0: merchant.setType(MerchantEnum.MINI_MARKET); break;
                case 1: merchant.setType(MerchantEnum.DROP_SHIPPER); break;
                case 2: merchant.setType(MerchantEnum.RESELLER); break;
                case 3: merchant.setType(MerchantEnum.OTHER); break;
            }
        } else {
            throw new NoSuchElementException("Merchant with name : " + merchantDTO.getName() + " does not exist !");
        }
        // Kayaknya buat update isGreen manual admin kali ya ? ( pisahin method nya plg nanti )
    }

    @Override
    public void updateBalance(BalanceDTO balanceDTO) {
        Merchant merchant = merchantRepository.findByname(balanceDTO.getData());
        if(merchant != null){
            if(balanceDTO.getTransType() == 1){
                if(merchant.getBalance().compareTo(balanceDTO.getAmount()) < 0){
                    throw new IllegalArgumentException("You don't have enough money !");
                } else {
                    merchant.setBalance(merchant.getBalance().subtract(balanceDTO.getAmount()));
                }
            } else if (balanceDTO.getTransType() == 2){
                merchant.setBalance(merchant.getBalance().add(balanceDTO.getAmount()));
            }
        } else {
            throw new NoSuchElementException("Merchant with name : " + balanceDTO.getData() + " does not exist !");
        }
    }

    @Override
    public void deleteMerchant(String name) {
        Merchant merchant = merchantRepository.findByname(name);
        if(!merchant.getIsDeleted()){
            merchant.setDeleted_at(Date.from(Instant.now()));
            merchant.setIsDeleted(Boolean.TRUE);
            merchantRepository.save(merchant);
        } else {
            throw new IllegalArgumentException("Already deleted !");
        }
    }

    @Override
    public void merchantGreen(String name) {
        Merchant merchant = merchantRepository.findByname(name);
        if(merchant != null){
            merchant.setIsGreen(Boolean.TRUE);
        } else {
            throw new NoSuchElementException("Merchant with name : " + name + " does not exist !");
        }
    }

    @Override
    public Merchant getMerchantData(String name) {
        return merchantRepository.findByname(name);
    }
}
