package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountDetailImplTest {

    @Autowired
    private AccountDetailImpl accountDetailService;
    @MockBean
    private AccountDetailsRepository accountDetailsRepository;
    private AccountDetails accountDetails;
    private User user;
    private BigDecimal amount;
    private int transferType;
    private int topupType;
    private BigDecimal point;

    @BeforeEach
    void setUp() {
        user = User.builder().build();
        amount = new BigDecimal("100000");
        point = new BigDecimal("5000");
        transferType = 1;
        topupType = 2;
        accountDetails = AccountDetails.builder()
                .id("123")
                .user(User.builder().build())
                .role(Role.builder().build())
                .balance(amount)
                .point(point)
                .isDeleted(false)
                .build();
    }
    @Test
    void getAccountDetailById() {
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(accountDetails));
        AccountDetails result = accountDetailService.getAccountDetailById(accountDetails.getId());
        verify(accountDetailsRepository, times(1)).findById(any());
        assertEquals(accountDetails, result);
    }

    @Test
    void updateAccountData() {
        accountDetailService.updateAccountData(accountDetails);
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void deleteAccount() {
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(accountDetails));
        accountDetailService.deleteAccount(accountDetails.getId());
        verify(accountDetailsRepository, times(1)).findById(any());
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void deleteAccountAlreadyDeleted() {
        AccountDetails accountDetailsIsDeleted = accountDetails;
        accountDetailsIsDeleted.setIsDeleted(true);

        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(accountDetails));
        assertThrows(IllegalArgumentException.class, () -> {
            accountDetailService.deleteAccount(accountDetails.getId());
        });
    }

    @Test
    void getAccountData() {
        when(accountDetailsRepository.findByuser(user)).thenReturn(accountDetails);
        AccountDetails result = accountDetailService.getAccountData(user);
        assertEquals(accountDetails, result);
    }


    @Test
    void createAccount() {
        accountDetailService.createAccount(accountDetails);
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void updateBalance() {
        AccountDetails mockAccountDetail = mock(AccountDetails.class, RETURNS_MOCKS);
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(mockAccountDetail));
        accountDetailService.updateBalance(accountDetails.getId(), amount, transferType);
        verify(accountDetailsRepository, times(1)).save(any());
    }

    @Test
    void testUpdateBalanceLesserThanZeroValidation() {
        AccountDetails lesserThanZeroAccount = accountDetails;
        lesserThanZeroAccount.setBalance(new BigDecimal("-1000000"));
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(accountDetails));
        assertThrows(IllegalArgumentException.class, ()-> {
            accountDetailService.updateBalance(accountDetails.getId(), amount, transferType);
        });
    }

    @Test
    void testUpdateBalanceTopUp() {
        AccountDetails mockAccountDetail = mock(AccountDetails.class, RETURNS_MOCKS);
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(mockAccountDetail));
        accountDetailService.updateBalance(accountDetails.getId(), amount, topupType);
        verify(accountDetailsRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePoint() {
        AccountDetails mockAccountDetail = mock(AccountDetails.class, RETURNS_MOCKS);
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(mockAccountDetail));
        accountDetailService.updatePoints(accountDetails.getId(), amount, transferType);
        verify(accountDetailsRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePointLesserThanZeroValidation() {
        AccountDetails lesserThanZeroAccount = accountDetails;
        lesserThanZeroAccount.setBalance(new BigDecimal("-1000000"));
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(accountDetails));
        assertThrows(IllegalArgumentException.class, ()-> {
            accountDetailService.updatePoints(accountDetails.getId(), amount, transferType);
        });
    }

    @Test
    void testUpdatePointTopUp() {
        AccountDetails mockAccountDetail = mock(AccountDetails.class, RETURNS_MOCKS);
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.ofNullable(mockAccountDetail));
        accountDetailService.updatePoints(accountDetails.getId(), amount, topupType);
        verify(accountDetailsRepository, times(1)).save(any());
    }
}