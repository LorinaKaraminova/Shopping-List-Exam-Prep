package bg.softuni.shoppinglistexamprep.init;

import bg.softuni.shoppinglistexamprep.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final CategoryService categoryService;

    @Autowired
    public DatabaseInit(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.init();
    }
}

