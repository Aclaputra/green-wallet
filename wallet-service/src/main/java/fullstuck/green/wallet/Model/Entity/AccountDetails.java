package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;

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
@SQLSelect(sql = "SELECT * FROM master_account WHERE isDeleted = false")
@SQLDelete(sql = "UPDATE master_account SET deleted = true WHERE email = ?")
@ToString
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

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
//    @Column(columnDefinition = "numeric(38,2) default 0.0", nullable = false)
    @ColumnDefault("0")
    private BigDecimal balance;
//    @Column(columnDefinition = "numeric(38,2) default 0.0", nullable = false)
    @ColumnDefault("0")
    private BigDecimal point;
//    @ColumnDefault("false")
    private Boolean isVerified;
    private Boolean isDeleted = Boolean.FALSE;
//    @Column(columnDefinition = "timestamp default now()")
    private Date created_at = Date.from(Instant.now());
//    @Column(columnDefinition = "timestamp default now()")
    private Date updated_at = Date.from(Instant.now());
    private Date deleted_at;
}
