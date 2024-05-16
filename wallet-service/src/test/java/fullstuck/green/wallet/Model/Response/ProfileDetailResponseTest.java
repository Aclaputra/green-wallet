package fullstuck.green.wallet.Model.Response;

import fullstuck.green.wallet.Strings.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProfileDetailResponseTest {

    @Test
    void testProfileDetailResponse() {
        ProfileDetailResponse profileDetailResponse = ProfileDetailResponse.builder()
                .email("email@gmail.com")
                .name("name")
                .role(String.valueOf(RoleEnum.ROLE_USER))
                .birthDate("12-10-2000")
                .point(new BigDecimal("1000"))
                .updatedAt("now")
                .phoneNumber("02932732")
                .accountId("123")
                .isVerified(false)
                .balance(new BigDecimal("100000"))
                .build();

        ProfileDetailResponse profileDetailResponseGetterSetter = new ProfileDetailResponse();
        profileDetailResponseGetterSetter.setName(profileDetailResponse.getName());
        profileDetailResponseGetterSetter.setEmail(profileDetailResponse.getEmail());
        profileDetailResponseGetterSetter.setRole(profileDetailResponse.getRole());
        profileDetailResponseGetterSetter.setBirthDate(profileDetailResponse.getBirthDate());
        profileDetailResponseGetterSetter.setPoint(profileDetailResponse.getPoint());
        profileDetailResponseGetterSetter.setUpdatedAt(profileDetailResponse.getUpdatedAt());
        profileDetailResponseGetterSetter.setPhoneNumber(profileDetailResponse.getPhoneNumber());
        profileDetailResponseGetterSetter.setAccountId(profileDetailResponse.getAccountId());
        profileDetailResponseGetterSetter.setIsVerified(profileDetailResponse.getIsVerified());
        profileDetailResponseGetterSetter.setBalance(profileDetailResponse.getBalance());

        assertEquals(profileDetailResponse, profileDetailResponseGetterSetter);
    }
}