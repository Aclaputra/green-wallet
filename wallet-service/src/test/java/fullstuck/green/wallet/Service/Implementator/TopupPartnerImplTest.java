package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.NewPartnerDTO;
import fullstuck.green.wallet.Model.Entity.TopupPartner;
import fullstuck.green.wallet.Repository.TopupPartnerRepository;
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
class TopupPartnerImplTest {

    @Autowired
    private TopupPartnerImpl topupPartnerService;
    @MockBean
    private TopupPartnerRepository topupPartnerRepository;

    @Test
    void registerNewPartner() {
        NewPartnerDTO mockNewPartnerDTO = mock(NewPartnerDTO.class, RETURNS_SMART_NULLS);
        topupPartnerService.registerNewPartner(mockNewPartnerDTO);
        verify(topupPartnerRepository, times(1)).save(any());
    }

    @Test
    void updateBalance() {
    }

    @Test
    void deletePartner() {
    }

    @Test
    void updatePartner() {
    }

    @Test
    void getPartnerId() {
    }

    @Test
    void getPartnerData() {
    }

    @Test
    void getPartnerByName() {
    }
}