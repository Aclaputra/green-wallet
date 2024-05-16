package fullstuck.green.wallet.Model.Request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testLoginRequest() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("acla@gmail.com")
                .password("password")
                .build();
        LoginRequest loginRequestGetSet = new LoginRequest();
        loginRequestGetSet.setEmail(loginRequest.getEmail());
        loginRequestGetSet.setPassword(loginRequest.getPassword());
        assertNotNull(loginRequest);
        assertNotNull(loginRequestGetSet);
    }
}