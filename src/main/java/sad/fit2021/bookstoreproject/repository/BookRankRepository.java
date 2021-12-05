package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.composite.BookRankId;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.BookRank;

import java.util.List;
import java.util.Optional;

public interface BookRankRepository extends JpaRepository<BookRank, BookRankId> {
    Optional<BookRank> findAllByBookBookId(Integer bookId);

    BookRank findAllById(BookRankId id);
}
