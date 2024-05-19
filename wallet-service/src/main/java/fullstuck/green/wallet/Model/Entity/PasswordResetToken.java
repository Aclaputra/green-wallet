package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "master_reset_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    @OneToOne
    @JoinColumn(name = "accountDetails_id")
    private AccountDetails accountDetails;
    private String email;
    private Date expired_at;
}