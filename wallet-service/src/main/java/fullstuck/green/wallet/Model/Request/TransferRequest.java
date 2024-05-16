package fullstuck.green.wallet.Model.Request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    // Transfer : Destination == No telp
    // Payment : Destination == Nama merchant
    private String destination;
    private BigDecimal amount;
    private String message;
}