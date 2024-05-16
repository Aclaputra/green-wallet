package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
class LoginResponseTest {

    @Test
    void testLoginResponseTest() {
        LoginResponse loginResponse = LoginResponse.builder()
                .jwtToken("jjwt")
                .build();
        LoginResponse loginResponseGetterSetter = new LoginResponse();
        loginResponseGetterSetter.setJwtToken(loginResponse.getJwtToken());
        assertEquals(loginResponse, loginResponseGetterSetter);
    }
}