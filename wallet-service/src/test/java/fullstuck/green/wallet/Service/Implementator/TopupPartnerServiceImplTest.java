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

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TopupPartnerServiceImplTest {

    @Autowired
    private TopupPartnerServiceImpl topupPartnerService;
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
        TopupPartner topupPartner = mock(TopupPartner.class, RETURNS_MOCKS);
        when(topupPartnerRepository.findById("id")).thenReturn(Optional.ofNullable(topupPartner));
        topupPartnerService.updateBalance("id",new BigDecimal("100000"));
        verify(topupPartnerRepository, times(1)).save(any());
    }

    @Test
    void deletePartner() {
        TopupPartner topupPartner = mock(TopupPartner.class, RETURNS_MOCKS);
        when(topupPartnerRepository.findById("id")).thenReturn(Optional.ofNullable(topupPartner));
        topupPartnerService.deletePartner("id");
        verify(topupPartnerRepository, times(1)).save(any());
    }

    @Test
    void updatePartner() {
        TopupPartner topupPartner = mock(TopupPartner.class, RETURNS_MOCKS);
        NewPartnerDTO mockNewPartnerDTO = mock(NewPartnerDTO.class, RETURNS_DEFAULTS);
        when(topupPartnerRepository.findById(any())).thenReturn(Optional.ofNullable(topupPartner));
        topupPartnerService.updatePartner(mockNewPartnerDTO);
        verify(topupPartnerRepository, times(1)).save(any());
    }

    @Test
    void getPartnerId() {
        TopupPartner mockUpPartner = mock(TopupPartner.class, RETURNS_DEFAULTS);
        when(topupPartnerRepository.findById("id")).thenReturn(Optional.ofNullable(mockUpPartner));
        topupPartnerService.getPartnerId("id");
        verify(topupPartnerRepository, times(1)).findById(any());
    }

    @Test
    void getPartnerData() {
        TopupPartner mockUpPartner = mock(TopupPartner.class, RETURNS_DEFAULTS);
        when(topupPartnerRepository.findById(any())).thenReturn(Optional.ofNullable(mockUpPartner));
        topupPartnerService.getPartnerData("id");
        verify(topupPartnerRepository, times(1)).findById(any());
    }

    @Test
    void getPartnerByName() {
        TopupPartner mockToupPartner = mock(TopupPartner.class, RETURNS_DEFAULTS);
        when(topupPartnerRepository.findByname(any())).thenReturn(mockToupPartner);
        topupPartnerService.getPartnerByName("Muhammad");
        verify(topupPartnerRepository, times(1)).findByname(any());
    }
}