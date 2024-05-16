package fullstuck.green.wallet.Model.Request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String birthDate;
}
