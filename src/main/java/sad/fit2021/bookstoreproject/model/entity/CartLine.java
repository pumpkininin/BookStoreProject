package sad.fit2021.bookstoreproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.composite.CartLineId;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_line")
public class CartLine {

    @EmbeddedId
    @JsonIgnore
    private CartLineId id = new CartLineId();

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @MapsId(value = "cartId")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @MapsId(value = "bookId")
    @JsonIgnore
    private Book book;


    @Transient
    public double getSubTotal() {
        return getBook().getPrice() * getQuantity();
    }

}
