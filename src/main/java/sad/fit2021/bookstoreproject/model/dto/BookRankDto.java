package sad.fit2021.bookstoreproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.composite.BookRankId;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.User;

import javax.persistence.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookRankDto {
    private double rate;

    private int sold;
    private String feedback;

    private User user;

    private Book book;
}
