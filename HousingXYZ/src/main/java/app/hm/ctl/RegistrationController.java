package app.hm.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.hm.dto.UserRegistrationDto;
import app.hm.service.UserService;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {
    
    @Autowired UserService userService;
    
    
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @PostMapping("/register")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserRegistrationDto registrationDto,
            BindingResult result) {
        
        // Check if passwords match
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Passwords do not match");
        }
        
        // Check if username/email already exists
        if (userService.emailExists(registrationDto.getEmail())) {
            result.rejectValue("email", null, "Email already registered");
        }
        
        if (userService.usernameExists(registrationDto.getUsername())) {
            result.rejectValue("username", null, "Username already taken");
        }
        
        if (result.hasErrors()) {
            return "register";
        }
        
        userService.registerUser(registrationDto);
        return "redirect:/register?success";
    }
}
