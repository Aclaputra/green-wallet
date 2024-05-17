package fullstuck.green.wallet.Model.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    @Email(message = "email should be valid")
    private String email;
    @NotNull
    @Length(min = 8, message = "password length must be greater than equal 8")
    @Length(max = 127, message = "password length cannot be greater than equal 127")
    private String password;
}
