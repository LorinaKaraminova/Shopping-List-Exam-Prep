package bg.softuni.shoppinglistexamprep.repository;

import bg.softuni.shoppinglistexamprep.model.Product;
import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    @Query("SELECT SUM(p.price) FROM Product p")
    BigDecimal findTotalPriceOfAllProducts();

    List<Product> findAllByCategoryName(CategoryNameEnum categoryName);


}
