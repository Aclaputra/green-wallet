package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.TransferResponse;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Repository.TransactionRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.Service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountDetailService accountDetailService;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private TransDetailRepository transDetailRepository;
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
        assertEquals(transactionList, transactionsFound);
    }

    @Test
    void testTransfer() {
//        String accountIdFromToken = "123";
//        String phone = "0827382333";
//
//        TransDetail transDetail = TransDetail.builder().build();
//        Transaction transaction = Transaction.builder().build();
//        User user = User.builder().build();
//        AccountDetails accountDetails = AccountDetails.builder()
////                .id("123")
//                .user(User.builder().build())
//                .role(Role.builder().build())
//                .build();
//        BigDecimal amount = new BigDecimal("100000");
//        int transType = 1;
//
//        TransferRequest transferRequest = TransferRequest.builder()
//                .type(transType)
//                .amount(amount)
//                .destination("321")
//                .build();
////        when(accountDetailService.getAccountDetailById(accountIdFromToken)).thenReturn(accountDetails);
////        when(userService.getUserById(accountDetails.getId())).thenReturn(user);
////        when(userService.getByPhone(phone)).thenReturn(user);
////        when(accountDetailService.getAccountData(user)).thenReturn(accountDetails);
////        when(transactionRepository.save(transaction)).thenReturn(transaction);
////        when(accountDetailService.updateBalance(accountDetails.getId(), amount, transType))
//
//        TransferResponse transferResponse = transactionService.transfer(transferRequest, accountIdFromToken);
//        verify(accountDetailService, times(2)).updateBalance(any(), amount, transType);
//        verify(accountDetailService, times(2)).getAccountData(user);
//
//        assertNotNull(transferResponse);
    }
}