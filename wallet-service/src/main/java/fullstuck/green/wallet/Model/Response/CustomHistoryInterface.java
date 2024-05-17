package fullstuck.green.wallet.Model.Response;

public interface CustomHistoryInterface {
    String getId();
    String getUserId();
    String getTransType();
    String getTransDate();
    String getMessage();
    String getTargetId();
    String getSource();
    String getAmount();
    String getCurrBalance();
}