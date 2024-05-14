package fullstuck.green.wallet.Model.DataTransferObject;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantDTO {
    private String name;
    private int type;
//    private Boolean isGreen;
}
