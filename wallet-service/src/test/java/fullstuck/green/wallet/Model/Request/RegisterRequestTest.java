package fullstuck.green.wallet.Model.Request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testRegisterRequest() {
        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .email("email@gmail.com")
                .username("username")
                .phoneNumber("0273986321")
                .birthDate("12-11-2000")
                .password("password")
                .build();
        RegisterRequest requestGetSet = new RegisterRequest();
        requestGetSet.setUsername(request.getUsername());
        requestGetSet.setName(request.getName());
        requestGetSet.setEmail(request.getEmail());
        requestGetSet.setPassword(request.getPassword());
        requestGetSet.setBirthDate(request.getBirthDate());
        assertNotNull(requestGetSet);
        assertNotNull(request);
    }

}