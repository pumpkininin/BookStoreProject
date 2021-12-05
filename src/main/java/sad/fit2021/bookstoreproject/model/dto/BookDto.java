package sad.fit2021.bookstoreproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.entity.Author;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.Category;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {
    private Integer bookId;
    private String title;
    private double price;
    private String img;
    private int inStock;
    private String longDescription;
    private int sold;
    private double rate;
    private List<Category> category;
    private Author authors;

    public BookDto(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.img = book.getImg();
        this.price = book.getPrice();
        this.inStock = book.getInStock();
        this.longDescription = book.getLongDescription();
        this.authors = book.getAuthors();
        this.category = book.getCategory();
//        this.sold = book.getBookRank().getSold();
//        this.rate = book.getBookRank().getRate();
    }

}
