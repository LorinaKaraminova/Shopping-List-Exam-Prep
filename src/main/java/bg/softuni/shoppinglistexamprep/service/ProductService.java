package bg.softuni.shoppinglistexamprep.service;

import bg.softuni.shoppinglistexamprep.model.Category;
import bg.softuni.shoppinglistexamprep.model.Product;
import bg.softuni.shoppinglistexamprep.model.User;
import bg.softuni.shoppinglistexamprep.model.dtos.CreateProductDTO;
import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import bg.softuni.shoppinglistexamprep.model.views.ProductViewModel;
import bg.softuni.shoppinglistexamprep.repository.CategoryRepository;
import bg.softuni.shoppinglistexamprep.repository.ProductRepository;
import bg.softuni.shoppinglistexamprep.repository.UserRepository;
import bg.softuni.shoppinglistexamprep.security.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LoggedUser loggedUser;
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, LoggedUser loggedUser, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    public boolean createProduct(CreateProductDTO createProductDTO) {
        Optional<Product> byName = this.productRepository.findByName(createProductDTO.getName());
        if (byName.isPresent()){
            return false;
        }
        Product productEntity = this.modelMapper.map(createProductDTO, Product.class);
        Optional<Category> optCategory = this.categoryRepository.findByName(createProductDTO.getCategory());
        productEntity.setCategory(optCategory.get());
        Optional<User> optUser = this.userRepository.findById(this.loggedUser.getId());
        productEntity.setOwner(optUser.get());
        productEntity.setName(createProductDTO.getName());
        productEntity.setPrice(createProductDTO.getPrice());
        productEntity.setNeededBefore(createProductDTO.getNeededBefore());
        productEntity.setDescription(createProductDTO.getDescription());
        this.productRepository.save(productEntity);
        return true;
    }


    public List<ProductViewModel> getProductsByCategory(CategoryNameEnum categoryName) {
        return productRepository.findAllByCategoryName(categoryName).stream()
                .map(p -> modelMapper.map(p, ProductViewModel.class)
                ).collect(Collectors.toList());
    }
    public BigDecimal getTotalSum() {
        BigDecimal value = productRepository.findTotalPriceOfAllProducts();
        return value != null ? value : BigDecimal.ZERO;
    }


    public void buyById(Long id) {
        productRepository.deleteById(id);
    }


    public void buyAllProducts() {
        productRepository.deleteAll();
    }
}
