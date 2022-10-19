package bg.softuni.shoppinglistexamprep.repository;

import bg.softuni.shoppinglistexamprep.model.Category;
import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(CategoryNameEnum name);
}
