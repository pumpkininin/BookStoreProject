package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
}
