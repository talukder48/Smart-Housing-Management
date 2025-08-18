package app.hm.ctl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.hm.entity.Project;
import app.hm.entity.User;
import app.hm.service.ProjectService;
import app.hm.service.UserService;

@Controller
public class UserController {
    
	@Autowired ProjectService projectService;
	@Autowired UserService userService;
    
    @GetMapping("/home")
    public String userHome(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        
        // Get projects owned by user
        List<Project> ownedProjects = projectService.getProjectsOwnedBy(user);
        
        // Get projects where user is a member
        List<Project> memberProjects = projectService.getProjectsWhereMember(user);
        
        model.addAttribute("user", user);
        model.addAttribute("ownedProjects", ownedProjects);
        model.addAttribute("memberProjects", memberProjects);
        
        return "user/home";
    }
}
