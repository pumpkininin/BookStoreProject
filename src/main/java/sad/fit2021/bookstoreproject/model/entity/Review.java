package sad.fit2021.bookstoreproject.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviews_id")
    private Integer reviewId;

    @Column(name = "review")
    private String review;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
