package fullstuck.green.wallet.Model.Response;

import fullstuck.green.wallet.Model.Entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JsonResponseTest {

    @Test
    void testJsonResponse() {
        JsonResponse<Object> jsonResponse = JsonResponse.builder()
                .message("success")
                .statusCode(200)
                .data(User.builder().build())
                .build();
        JsonResponse<Object> jsonResponseGetterSetter = new JsonResponse<>();
        jsonResponseGetterSetter.setMessage(jsonResponse.getMessage());
        jsonResponseGetterSetter.setStatusCode(jsonResponse.getStatusCode());
        jsonResponseGetterSetter.setData(jsonResponse.getData());
        assertNotNull(jsonResponse);
        assertNotNull(jsonResponseGetterSetter);
    }
}