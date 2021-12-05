package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.composite.CartLineId;
import sad.fit2021.bookstoreproject.model.entity.Cart;
import sad.fit2021.bookstoreproject.model.entity.CartLine;

import java.util.List;

public interface CartLineRepository extends JpaRepository<CartLine, CartLineId> {
    CartLine findCartLinesById(CartLineId id);

    List<CartLine> findAllByCart(Cart cart);
}
