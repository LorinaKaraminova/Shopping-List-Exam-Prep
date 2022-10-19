package bg.softuni.shoppinglistexamprep.service;

import bg.softuni.shoppinglistexamprep.model.User;
import bg.softuni.shoppinglistexamprep.model.dtos.UserLoginDTO;
import bg.softuni.shoppinglistexamprep.model.dtos.UserRegistrationDTO;
import bg.softuni.shoppinglistexamprep.repository.UserRepository;
import bg.softuni.shoppinglistexamprep.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public AuthService(UserRepository userRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    public boolean register(UserRegistrationDTO userRegistrationDTO) {
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())){
            return false;
        }
        Optional<User> byEmail = this.userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (byEmail.isPresent()){
            throw new RuntimeException("Email should be unique!");
        }

        Optional<User> byUsername = this.userRepository.findByUsername(userRegistrationDTO.getUsername());
        if (byUsername.isPresent()){
            throw new RuntimeException("Username should be unique!");
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(userRegistrationDTO.getPassword());

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {

        Optional<User> byUsernameAndPassword = this.userRepository.findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if (byUsernameAndPassword.isEmpty()){
            return false;
        }

        this.loggedUser.login(byUsernameAndPassword.get());
        return true;
    }

    public boolean isLoggedIn() {
        return this.loggedUser.getId() != null;
    }
}
