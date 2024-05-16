package fullstuck.green.wallet.Model.DataTransferObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MerchantDTOTest {
    @Test
    void testMerchantDTO() {
        MerchantDTO merchantDTO = MerchantDTO.builder()
                .name("merchant")
                .type(1)
                .build();
        MerchantDTO merchantDTOGetSet = new MerchantDTO();
        merchantDTOGetSet.setName(merchantDTO.getName());
        merchantDTOGetSet.setType(merchantDTO.getType());

        assertNotNull(merchantDTO);
        assertNotNull(merchantDTOGetSet);
    }
}