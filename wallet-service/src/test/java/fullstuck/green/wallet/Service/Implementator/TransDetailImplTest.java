package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.TransDetail;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.TransDetailService;
import fullstuck.green.wallet.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransDetailImplTest {

    @Autowired
    private TransDetailService transDetailService;

    @MockBean
    private TransDetailRepository transDetailRepository;

    @Test
    void testSaveTransactionDetail() {
        TransDetail mockData = TransDetail.builder().build();
        transDetailService.saveTransactionDetail(mockData);
        verify(transDetailRepository, times(1)).save(mockData);
    }

}