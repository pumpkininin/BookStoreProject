package sad.fit2021.bookstoreproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.CartLine;
import sad.fit2021.bookstoreproject.model.entity.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer cartId;
    private Integer userId;
    private Book book;
    private int quantity;
    private double subTotal;

    public CartDto(CartLine cartLine, Integer userId) {
        this.cartId = cartLine.getCart().getId();
        this.userId = userId;
        this.book = cartLine.getBook();
        this.quantity = cartLine.getQuantity();
        this.subTotal = cartLine.getSubTotal();
    }
}
