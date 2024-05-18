package fullstuck.green.wallet.Config;

import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PatcherTest {

    @Test
    void testProfilePatcher() throws IllegalAccessException {
        UpdateProfileRequest mockUpdateProfileRequest = mock(UpdateProfileRequest.class, RETURNS_MOCKS);
        Patcher.profilePatcher(mockUpdateProfileRequest, mockUpdateProfileRequest);
    }
}