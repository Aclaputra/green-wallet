package fullstuck.green.wallet.Service;

public interface EmailService {
    void simpleResetPassword(String to, String url);
    void upgradedResetPassword(String to, String url);
}