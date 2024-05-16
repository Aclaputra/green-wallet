package fullstuck.green.wallet.Model.DataTransferObject;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewPartnerDTO {
    private String name;
    private String id;
}