package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
