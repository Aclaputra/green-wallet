package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import org.springframework.web.server.ResponseStatusException;

public interface AuthService {
    RegisterResponse register(RegisterRequest req) throws Exception;
    LoginResponse login(LoginRequest req) throws Exception;
}
