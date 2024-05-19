package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.CustomHistoryInterface;
import fullstuck.green.wallet.Model.Response.HistoryDetailResponse;
import fullstuck.green.wallet.Model.Response.TopupResponse;
import fullstuck.green.wallet.Model.Response.TransferResponse;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Repository.TransactionRepository;
import fullstuck.green.wallet.Service.*;
import fullstuck.green.wallet.security.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private MerchantService merchantService;
    @MockBean
    private TopupPartnerService topupPartnerService;
    @MockBean
    private AccountDetailService accountDetailService;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private TransDetailRepository transDetailRepository;
    @MockBean
    private TransDetailService transDetailService;
    @MockBean
    private UserService userService;
    @MockBean
    private JWTUtil jwtUtil;

    @Test
    void testGetAllTransaction() {
        String accountIdFromToken = "123";
        AccountDetails accountDetails = AccountDetails.builder()
                .user(User.builder().build())
                .role(Role.builder().build())
                .build();
        User user = User.builder().build();
        List<Transaction> transactionList = new ArrayList<>();
        when(accountDetailService.getAccountDetailById(accountIdFromToken)).thenReturn(accountDetails);
        when(userService.getUserById("accid123")).thenReturn(user);
        when(transactionRepository.findAllByuser(user)).thenReturn(transactionList);
        List<Transaction> transactionsFound = transactionService.getAllTransaction(accountIdFromToken);
    }

    @Test
    void testTransfer() {
        TransferRequest transferRequest = mock(TransferRequest.class, RETURNS_MOCKS);
        AccountDetails mockAccounDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        User mockUser = mock(User.class, RETURNS_DEFAULTS);
        TransDetail mockTransDetail = mock(TransDetail.class, RETURNS_DEFAULTS);
        Transaction mockTransaction = mock(Transaction.class, RETURNS_MOCKS);

        when(accountDetailService.getAccountDetailById(any())).thenReturn(mockAccounDetails);
        when(accountDetailService.getAccountData(any())).thenReturn(mockAccounDetails);
        when(userService.getUserById(anyString())).thenReturn(mockUser);
        when(transactionRepository.save(any())).thenReturn(mockTransaction);

        TransferResponse response = transactionService.transfer(transferRequest, "idToken");
        assertNotNull(response);
    }

    @Test
    void testTopUp() {
        TopUpRequest topUpRequest = mock(TopUpRequest.class, RETURNS_MOCKS);
        AccountDetails mockAccounDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        User mockUser = mock(User.class, RETURNS_DEFAULTS);
        Transaction mockTransaction = mock(Transaction.class, RETURNS_MOCKS);

        when(accountDetailService.getAccountDetailById(any())).thenReturn(mockAccounDetails);
        when(userService.getUserById(any())).thenReturn(mockUser);
        when(transactionRepository.save(any())).thenReturn(mockTransaction);

        TopupResponse response = transactionService.topUp(topUpRequest, "tokenId");
        assertNotNull(response);
    }

    @Test
    void testPayment() {
        TransferRequest transferRequest = mock(TransferRequest.class, RETURNS_MOCKS);
        AccountDetails mockAccounDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        Merchant mockMerchant = mock(Merchant.class, RETURNS_MOCKS);
        User mockUser = mock(User.class, RETURNS_MOCKS);
        Transaction mockTransaction = mock(Transaction.class, RETURNS_MOCKS);

        when(accountDetailService.getAccountDetailById(any())).thenReturn(mockAccounDetails);
        when(merchantService.getByName(anyString())).thenReturn(mockMerchant);
        when(userService.getUserById(any())).thenReturn(mockUser);
        when(transactionRepository.save(any())).thenReturn(mockTransaction);
        when(accountDetailService.getAccountData(any())).thenReturn(mockAccounDetails);

        TransferResponse response = transactionService.payment(transferRequest, "idToken");
        assertNotNull(response);
    }

    @Test
    void testMerchantTransfer() {
        TransferRequest transferRequest = mock(TransferRequest.class, RETURNS_MOCKS);
        AccountDetails mockAccounDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        Merchant mockMerchant = mock(Merchant.class, RETURNS_MOCKS);
        User mockUser = mock(User.class, RETURNS_MOCKS);
        Transaction mockTransaction = mock(Transaction.class, RETURNS_MOCKS);

        when(accountDetailService.getAccountDetailById(any())).thenReturn(mockAccounDetails);
        when(userService.getUserById(any())).thenReturn(mockUser);
        when(userService.getByPhone(any())).thenReturn(mockUser);
        when(accountDetailService.getAccountData(any())).thenReturn(mockAccounDetails);
        when(transactionRepository.save(any())).thenReturn(mockTransaction);

        TransferResponse response = transactionService.merchantTransfer(transferRequest, "idToken");
        assertNotNull(response);
    }

    @Test
    void testGetById() {
        Transaction transaction = mock(Transaction.class, RETURNS_MOCKS);
        when(transactionRepository.findById(any())).thenReturn(Optional.ofNullable(transaction));
        transactionService.getById("123");
    }

    @Test
    void testFindAllCustomer() {
        AccountDetails accountDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        List customHistoryInterfaceList = mock(List.class, RETURNS_MOCKS);
        when(accountDetailService.getAccountDetailById(any())).thenReturn(accountDetails);
        when(transactionRepository.findAllCustom(any(), any())).thenReturn(customHistoryInterfaceList);
        List result = transactionService.findAllCustom("idFromToken");
        assertEquals(customHistoryInterfaceList, result);
    }
}