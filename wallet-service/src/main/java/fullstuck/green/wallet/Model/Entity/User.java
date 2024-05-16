package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;

import java.util.Date;

@Entity
@Table(name = "master_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@SQLSelect(sql = "SELECT * FROM master_user WHERE isDeleted = false")
@SQLDelete(sql = "UPDATE master_user SET isDeleted = true WHERE id = ?")
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
    @Column(nullable = false, unique = true)
    private String phone;
    private Boolean isDeleted;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}