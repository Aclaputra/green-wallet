package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Merchant;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Repository.RoleRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.Strings.MerchantEnum;
import fullstuck.green.wallet.Strings.RoleEnum;
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
    private final UserService userService;
    private final AccountDetailService accountDetailService;
    private final RoleRepository roleRepository;

    @Override
    public void createMerchant(MerchantDTO merchantDTO, String id) {
        System.out.println("Masuk ke create merchant");
        if(accountDetailService.getAccountDetailById(id).getUser().getMerchant() == null){
            MerchantEnum temp = switch (merchantDTO.getType()) {
                case 0 -> MerchantEnum.MINI_MARKET;
                case 1 -> MerchantEnum.DROP_SHIPPER;
                case 2 -> MerchantEnum.RESELLER;
                case 3 -> MerchantEnum.OTHER;
                default -> null;
            };

            // Save newly created merchant
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

            // Get user info from Token, update user Merchant from Null to Merchant Id
            User user = accountDetailService.getAccountDetailById(id).getUser();
            user.setMerchant(merchant);

            // Save existing update
            userService.userUpdateMerchant(user);

            AccountDetails accountDetails = accountDetailService.getAccountDetailById(id);
            Role role = accountDetails.getRole();
            role.setName(RoleEnum.ROLE_MERCHANT);
            accountDetails.setRole(role);

            // Update account detail existing role, and update the role into merchant
            roleRepository.save(role);
            accountDetailService.updateAccountData(accountDetails);

        } else {
            throw new IllegalArgumentException("User can only have 1 merchant ! Or Merchant name must be unique");
        }
    }

    @Override
    public void updateMerchant(MerchantDTO merchantDTO) {
        // Belom di test ga tau work / kaga
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
