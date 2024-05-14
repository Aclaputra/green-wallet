package fullstuck.green.wallet.Model.DataTransferObject;

import fullstuck.green.wallet.Model.Entity.Role;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailDTO {
    private Role role;
    private String email;
    private String password;
    private Boolean isVerified;
}