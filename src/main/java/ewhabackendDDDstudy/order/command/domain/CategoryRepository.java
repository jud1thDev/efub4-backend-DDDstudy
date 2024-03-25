package ewhabackendDDDstudy.order.command.domain;

import ewhabackendDDDstudy.order.command.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategory(Long categoryId);
}