package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TopupResponseTest {

    @Test
    void testTopUpResponse() {
        TopupResponse topupResponse = TopupResponse.builder()
                .amount("1224214")
                .via("bank")
                .build();
        TopupResponse topupResponseGetterSetter = new TopupResponse();
        topupResponseGetterSetter.setAmount(topupResponse.getAmount());
        topupResponseGetterSetter.setVia(topupResponse.getVia());
        assertEquals(topupResponse, topupResponseGetterSetter);
    }
}