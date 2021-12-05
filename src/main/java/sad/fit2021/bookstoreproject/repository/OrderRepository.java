package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser_UserId(Integer id);
}
