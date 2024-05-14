package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest req);
}
