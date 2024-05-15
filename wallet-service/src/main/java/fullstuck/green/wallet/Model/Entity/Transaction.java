package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @OneToOne
    @JoinColumn(name = "transDetail_id")
    private TransDetail transDetail;
}