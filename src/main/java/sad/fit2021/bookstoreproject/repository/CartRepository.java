package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.entity.Cart;
import sad.fit2021.bookstoreproject.model.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findAllByUser_UserId(Integer userId);

    Cart findAllByUser(User user);
}
