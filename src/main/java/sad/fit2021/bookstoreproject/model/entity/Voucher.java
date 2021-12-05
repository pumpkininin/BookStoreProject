package sad.fit2021.bookstoreproject.model.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

//    @OneToMany(mappedBy = "voucher")
//    @JsonIgnore
//    private List<Order> orders = new ArrayList<>();

}
