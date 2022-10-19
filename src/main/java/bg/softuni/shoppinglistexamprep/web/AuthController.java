package bg.softuni.shoppinglistexamprep.web;

import bg.softuni.shoppinglistexamprep.model.dtos.UserLoginDTO;
import bg.softuni.shoppinglistexamprep.model.dtos.UserRegistrationDTO;
import bg.softuni.shoppinglistexamprep.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO initRegistrationDTO(){
        return new UserRegistrationDTO();
    }

    @ModelAttribute("userLoginDTO")
    public UserLoginDTO initLoginDTO(){
        return new UserLoginDTO();
    }

    @GetMapping("/register")
    public String register() {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid UserRegistrationDTO userRegistrationDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !this.authService.register(userRegistrationDTO)) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            return "redirect:/register";
        }

        return "redirect:/login";

    }

    @GetMapping("/login")
    public String login(Model model){
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("badCredentials")) {
            model.addAttribute("badCredentials", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid UserLoginDTO userLoginDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
            return "redirect:/login";
        }

        if (!this.authService.login(userLoginDTO)){
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", false);

            return "redirect:/login";
        }
        return "redirect:/home";

    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
