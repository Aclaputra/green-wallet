package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.ResetResponse;
import fullstuck.green.wallet.Model.Response.ForgotPasswordResponse;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest req) throws Exception;
    LoginResponse login(LoginRequest req) throws Exception;
    ForgotPasswordResponse resetPassword(AccountDetails accountDetails, String token) throws Exception;
    ResetResponse validateResetToken(String token);
    void deleteOldToken(String token);
}