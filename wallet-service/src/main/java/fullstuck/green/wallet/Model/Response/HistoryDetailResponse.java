package fullstuck.green.wallet.Model.Response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDetailResponse {
    private String id;
    private String userId;
    private String transType;
    private Date transDate;
    private String message;
    private String target;
    private String source;
    private BigDecimal amount;
    private BigDecimal currBalance;
}
