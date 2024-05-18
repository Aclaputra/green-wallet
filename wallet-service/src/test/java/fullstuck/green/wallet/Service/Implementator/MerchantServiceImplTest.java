package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Merchant;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Repository.RoleRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.UserService;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

    @Autowired
    private MerchantServiceImpl merchantService;
    @MockBean
    private MerchantRepository merchantRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private AccountDetailsRepository accountDetailsRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private AccountDetailService accountDetailService;

    @Test
    void testCreateMerchant() {
        MerchantDTO merchantDTO = mock(MerchantDTO.class, RETURNS_MOCKS);
        AccountDetails accountDetails = AccountDetails.builder()
                .user(User.builder().build())
                .role(Role.builder().build())
                .created_at(Date.from(Instant.now()))
                .build();
        when(accountDetailService.getAccountDetailById(anyString())).thenReturn(accountDetails);
        merchantService.createMerchant(merchantDTO, "id");
        verify(merchantRepository, times(1)).save(any());
    }

    @Test
    void testUpdateMerchant() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        MerchantDTO merchantDTO = mock(MerchantDTO.class, RETURNS_MOCKS);
        when(merchantRepository.findByname(anyString())).thenReturn(merchant);
        merchantService.updateMerchant(merchantDTO);
        verify(merchantRepository, times(1)).findByname(anyString());
    }

    @Test
    void testUpdateBalance() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        when(merchantRepository.findById(anyString())).thenReturn(Optional.ofNullable(merchant));
        merchantService.updateBalance("id", new BigDecimal("10000"), 1);
        merchantService.updateBalance("id", new BigDecimal("10000"), 2);
    }

    @Test
    void testDeleteMerchant() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        when(merchantRepository.findById(any())).thenReturn(Optional.ofNullable(merchant));
        merchantService.deleteMerchant("id");
    }

    @Test
    void testMerchantGreen() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        when(merchantRepository.findByname("merchant")).thenReturn(merchant);
        merchantService.merchantGreen("merchant");
    }

    @Test
    void testGetMerchantData() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        when(merchantRepository.findById(anyString())).thenReturn(Optional.of(merchant));
        Merchant result = merchantService.getMerchantData("id");
        assertEquals(merchant, result);
    }

    @Test
    void testGetByName() {
        Merchant merchant = mock(Merchant.class, RETURNS_MOCKS);
        when(merchantRepository.findByname(anyString())).thenReturn(merchant);
        Merchant result = merchantService.getByName("acla");
        assertEquals(merchant, result);
    }

}