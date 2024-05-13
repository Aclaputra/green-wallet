package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "master_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false)
    private String phone_number;
    private Date created_at = Date.from(Instant.now());
    private Date updated_at = Date.from(Instant.now());
    private Date deleted_at;
}