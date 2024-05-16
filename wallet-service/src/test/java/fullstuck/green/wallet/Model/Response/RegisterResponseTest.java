package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RegisterResponseTest {

    @Test
    void testRegisterResponse() {
        RegisterResponse response = RegisterResponse.builder()
                .id("123")
                .build();
        RegisterResponse responseGetterSetter = new RegisterResponse();
        responseGetterSetter.setId(response.getId());
    }
}