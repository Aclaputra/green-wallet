package fullstuck.green.wallet.Model.DataTransferObject;

import fullstuck.green.wallet.Model.Entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDetailDTOTest {
    @Test
    void testAccountDetailDTO() {
        AccountDetailDTO accountDetailDTO = AccountDetailDTO.builder()
                .email("acla@gmail.com")
                .isVerified(false)
                .role(Role.builder().build())
                .password("password")
                .build();
        AccountDetailDTO accountDetailDTOGetSet = new AccountDetailDTO();
        accountDetailDTOGetSet.setEmail(accountDetailDTO.getEmail());
        accountDetailDTOGetSet.setRole(accountDetailDTO.getRole());
        accountDetailDTOGetSet.setPassword(accountDetailDTO.getPassword());
        accountDetailDTOGetSet.setIsVerified(accountDetailDTO.getIsVerified());

        assertNotNull(accountDetailDTO);
        assertNotNull(accountDetailDTOGetSet);
    }
}