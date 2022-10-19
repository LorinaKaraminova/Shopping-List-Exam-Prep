package bg.softuni.shoppinglistexamprep.service;

import bg.softuni.shoppinglistexamprep.model.Category;
import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import bg.softuni.shoppinglistexamprep.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void init() {
        if (categoryRepository.count() > 0) {
            return;
        }

        CategoryNameEnum[] values = CategoryNameEnum.values();

        Arrays.stream(values).forEach(this::createAndSaveCategory);
    }


    public Optional<Category> findByName(CategoryNameEnum name) {
        return this.categoryRepository.findByName(name);
    }

    private void createAndSaveCategory(CategoryNameEnum value) {
        Category category = new Category();

        category.setName(value);

        categoryRepository.save(category);
    }
}
