package sad.fit2021.bookstoreproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "title")
    private String title;
    @Column(name = "img")
    private String img;
    @Column(name = "in_stock")
    private int inStock;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "long_description")
    private String longDescription;
    @Column(name = "price")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    List<CartLine> cartLines = new ArrayList<>();
}
