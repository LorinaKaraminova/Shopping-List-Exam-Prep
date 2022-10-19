package bg.softuni.shoppinglistexamprep.web;

import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import bg.softuni.shoppinglistexamprep.service.AuthService;
import bg.softuni.shoppinglistexamprep.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ProductService productService;
    private final AuthService authService;

    @Autowired
    public HomeController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }


    @GetMapping("/index")
    public String loggedOutIndex(){
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model){
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        model.addAttribute("totalPrice", productService.getTotalSum());
        model.addAttribute("food", productService.getProductsByCategory(CategoryNameEnum.FOOD));
        model.addAttribute("drink", productService.getProductsByCategory(CategoryNameEnum.DRINK));
        model.addAttribute("household", productService.getProductsByCategory(CategoryNameEnum.HOUSEHOLD));
        model.addAttribute("other", productService.getProductsByCategory(CategoryNameEnum.OTHER));

        return "home";
    }
}
