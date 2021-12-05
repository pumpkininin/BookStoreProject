package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sad.fit2021.bookstoreproject.model.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByTitleContaining(String title);
    @Override
    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByTitleContaining(String title, Pageable pageable);

    List<Book> findAll(Sort sort);

    Book findBookByBookId(Integer bookId);
    @Query(value = "select b from Book b")
    public List<Book> findWithPageable(Pageable pageable);
    List<Book> findTop4ByCategory(String category);
}
