package bg.softuni.shoppinglistexamprep.web;

import bg.softuni.shoppinglistexamprep.model.dtos.CreateProductDTO;
import bg.softuni.shoppinglistexamprep.service.AuthService;
import bg.softuni.shoppinglistexamprep.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final AuthService authService;

    public ProductController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @ModelAttribute("createProductDTO")
    public CreateProductDTO initCreateProductDTO(){
        return new CreateProductDTO();
    }

    @GetMapping("/products/add")
    public String addProduct(){
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }
        return "product-add";
    }

    @PostMapping("/products/add")
    public String addProductPost(@Valid CreateProductDTO createProductDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() || !this.productService.createProduct(createProductDTO)){
            redirectAttributes.addFlashAttribute("createProductDTO", createProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createProductDTO", bindingResult);

            return "redirect:/products/add";
        }
        return "redirect:/";
    }

    @GetMapping("/products/buy/{id}")
    public String buyById(@PathVariable Long id) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }
        productService.buyById(id);
        return "redirect:/home";
    }

    @GetMapping("/products/buy/all")
    public String buyAll() {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }
        productService.buyAllProducts();
        return "redirect:/home";
    }
}
