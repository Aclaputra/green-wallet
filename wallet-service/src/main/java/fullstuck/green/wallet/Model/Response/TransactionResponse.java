package fullstuck.green.wallet.Model.Response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String id;
    private String description;
    private BigDecimal amount;
    private String type;
    private String from;
    private String destination;
    private String via;
}
