package sad.fit2021.bookstoreproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sad.fit2021.bookstoreproject.model.entity.Category;

import javax.persistence.criteria.CriteriaBuilder;

public interface CategoryRepository extends JpaRepository<Category, CriteriaBuilder.In> {
}
