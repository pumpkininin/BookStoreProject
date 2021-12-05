package sad.fit2021.bookstoreproject.model.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CartLineId implements Serializable {

    private static final long serialVersionUID = 3128147993246840843L;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "book_id")
    private Integer bookId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;

        hash = hash * prime + cartId.hashCode();
        hash = hash * prime + bookId.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof CartLineId))
            return false;
        CartLineId castedObj = (CartLineId) obj;
        return this.cartId.equals(castedObj.cartId) && this.bookId.equals(castedObj.bookId);
    }
}
