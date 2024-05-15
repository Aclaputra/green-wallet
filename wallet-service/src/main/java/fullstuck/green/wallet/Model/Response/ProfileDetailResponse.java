package fullstuck.green.wallet.Model.Response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailResponse {
    private String accountId;
    private String name;
    private String birthDate;
    private String phoneNumber;
    private String role;
    private String email;
    private BigDecimal balance;
    private BigDecimal point;
    private Boolean isVerified;
    private String updatedAt;
}
