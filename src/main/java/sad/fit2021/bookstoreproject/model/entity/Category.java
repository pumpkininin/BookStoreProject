package sad.fit2021.bookstoreproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "category")
    private List<Book> book;
}
