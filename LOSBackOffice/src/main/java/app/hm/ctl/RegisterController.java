package app.hm.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.hm.entity.User;
import app.hm.enums.UserRole;
import app.hm.repo.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show register form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    // Handle registration
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user,
                                  BindingResult result,
                                  Model model) {

        if (userRepo.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already exists");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepo.save(user);

        return "redirect:/register?success";
    }
}
