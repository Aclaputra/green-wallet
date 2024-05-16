package fullstuck.green.wallet.Model.DataTransferObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewPartnerDTOTest {
    @Test
    void testNewPartnerDTO() {
        NewPartnerDTO newPartnerDTO = NewPartnerDTO.builder()
                .id("123")
                .name("name")
                .build();
        NewPartnerDTO newPartnerDTOGetSet = new NewPartnerDTO();
        newPartnerDTOGetSet.setId(newPartnerDTO.getId());
        newPartnerDTOGetSet.setName(newPartnerDTO.getName());

        assertNotNull(newPartnerDTO);
        assertNotNull(newPartnerDTOGetSet);
    }
}