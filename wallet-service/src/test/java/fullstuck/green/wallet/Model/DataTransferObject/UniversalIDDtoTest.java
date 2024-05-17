package fullstuck.green.wallet.Model.DataTransferObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversalIDDtoTest {
    @Test
    void testUniversalDDto() {
        UniversalIDDto universalIDDto = UniversalIDDto.builder()
                .id("123")
                .build();
        UniversalIDDto universalIDDtoGetSet = new UniversalIDDto();
        universalIDDtoGetSet.setId(universalIDDto.getId());

        assertNotNull(universalIDDto);
        assertNotNull(universalIDDtoGetSet);
    }
}