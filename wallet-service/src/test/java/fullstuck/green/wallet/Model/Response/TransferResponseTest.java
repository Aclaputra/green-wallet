package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class TransferResponseTest {
    @Test
    void testTransferResponse() {
        TransferResponse transferResponse = TransferResponse.builder()
                .description("desc")
                .amount("291732913")
                .build();
        TransferResponse transferResponseGetSet = new TransferResponse();
        transferResponseGetSet.setAmount(transferResponse.getAmount());
        transferResponseGetSet.setDescription(transferResponse.getDescription());

        assertEquals(transferResponse, transferResponseGetSet);
    }
}