package sad.fit2021.bookstoreproject.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.composite.OrderLineId;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_line")
public class OrderLine implements Serializable {

    @EmbeddedId
    private OrderLineId id = new OrderLineId();

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Long unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId(value = "orderId")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @MapsId(value = "bookId")
    @JsonIgnore
    private Book book;

}
