package sad.fit2021.bookstoreproject.model.composite;


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
public class OrderLineId implements Serializable {

    private static final long serialVersionUID = -3582052521898928329L;

    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Integer orderId;

    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private Integer bookId;

    @Override
    public int hashCode() {
        final int prime = 33;
        int hash = 19;

        hash = hash * prime + orderId.hashCode();
        hash = hash * prime + bookId.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof OrderLineId))
            return false;
        OrderLineId castedObj = (OrderLineId) obj;
        return this.orderId.equals(castedObj.orderId) && this.bookId.equals(castedObj.bookId);
    }
}
