package fullstuck.green.wallet.Model.Response;

import java.math.BigDecimal;

public interface DailySum {
    BigDecimal getTotal();
    String getType();
    String getTransDate();
}
