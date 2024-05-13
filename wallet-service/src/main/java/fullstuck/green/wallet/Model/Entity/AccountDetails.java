package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "master_account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "accountDetails_user"),
            inverseJoinColumns = @JoinColumn(name = "role_roleid")
    )
    private Role role;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private BigDecimal balance = new BigDecimal("0.0");
    private BigDecimal point = new BigDecimal("0.0");
    private Boolean isVerified;
    private Date created_at = Date.from(Instant.now());
    private Date updated_at = Date.from(Instant.now());
    private Date deleted_at;
}
