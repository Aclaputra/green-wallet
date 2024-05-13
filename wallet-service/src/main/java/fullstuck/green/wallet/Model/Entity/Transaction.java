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
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "transDetail_id")
    private TransDetail transDetail;
}