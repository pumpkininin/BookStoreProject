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
public class BookRankId implements Serializable {
    private static final long serialVersionUID = 3128147998246840843L;

    @Column(name = "book_id)")
    private Integer bookId;

    @Column(name = "user_id")
    private Integer userId;

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 17;

        hash = hash * prime + userId.hashCode();
        hash = hash * prime + bookId.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof CartLineId))
            return false;
        BookRankId castedObj = (BookRankId) obj;
        return this.bookId.equals(castedObj.bookId) && this.userId.equals(castedObj.userId);
    }
}
