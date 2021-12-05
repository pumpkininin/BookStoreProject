package sad.fit2021.bookstoreproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import sad.fit2021.bookstoreproject.model.dto.CartDto;
import sad.fit2021.bookstoreproject.model.entity.Cart;
import sad.fit2021.bookstoreproject.model.entity.CartLine;
import sad.fit2021.bookstoreproject.repository.CartLineRepository;
import sad.fit2021.bookstoreproject.repository.CartRepository;
import sad.fit2021.bookstoreproject.service.CartService;
import sad.fit2021.bookstoreproject.service.UserService;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class BookStoreApplicationTests {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartLineRepository cartLineRepository;
    @Autowired
    private TestEntityManager entityManager;

    UserService userService = new UserService();

    @Test
    public void testGetCart() {
        Cart cart = cartRepository.findAllByUser_UserId(1);
        assert (cart != null);
    }



}
