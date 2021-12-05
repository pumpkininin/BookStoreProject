package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.composite.OrderLineId;
import sad.fit2021.bookstoreproject.model.entity.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLineId> {
}
