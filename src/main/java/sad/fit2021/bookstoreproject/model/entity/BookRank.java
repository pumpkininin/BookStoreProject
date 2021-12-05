package sad.fit2021.bookstoreproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.composite.BookRankId;

import javax.persistence.*;

@Entity
@Table(name = "book_rank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRank {

    @EmbeddedId
    @JsonIgnore
    private BookRankId id  = new BookRankId();

    @Column(name = "rate")
    private double rate;

    @Column(name = "sold")
    private int sold;
    @Column(name = "feedback")
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId(value = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @MapsId(value = "bookId")
    @JsonIgnore
    private Book book;
}
