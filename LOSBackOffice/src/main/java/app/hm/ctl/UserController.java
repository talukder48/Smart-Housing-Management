package app.hm.ctl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.hm.entity.LoanApplication;
import app.hm.entity.LoanDocument;
import app.hm.entity.User;
import app.hm.enums.LoanStatus;
import app.hm.repo.LoanApplicationRepository;
import app.hm.repo.LoanDocumentRepository;
import app.hm.service.UserService;

@Controller
public class UserController {
	
	@GetMapping("/")
    public String index(Principal principal, Model model) {
		if (principal!=null) {
			 return "redirect:/dashboard";
		}
		  return "index";
       
    }
    
	@Autowired UserService userService;
    @Autowired LoanDocumentRepository loanDocumentRepository;
    
    
    @Autowired LoanApplicationRepository loanApplicationRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        // 1️⃣ Logged-in officer
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        // 2️⃣ Fetch all applications (for demonstration, later filter by branch or officer if needed)
        List<LoanApplication> applications = loanApplicationRepository.findAll();

        // 3️⃣ Dashboard counts
        long totalApplications = applications.size();
        long verifiedApplications = applications.stream()
                .filter(a -> a.getStatus() == LoanStatus.VERIFIED).count();
        long pendingReview = applications.stream()
                .filter(a -> a.getStatus() == LoanStatus.PENDING).count();
        long approvedCount = applications.stream()
                .filter(a -> a.getStatus() == LoanStatus.APPROVED).count();

        // 4️⃣ Recent Activities (stub example)
        List<Map<String, String>> recentActivities = new ArrayList<>();
        recentActivities.add(Map.of("description", "Verified Loan #1003", "timeAgo", "2 hours ago"));
        recentActivities.add(Map.of("description", "Approved Loan #1001", "timeAgo", "5 hours ago"));

        // 5️⃣ Add data to model
        model.addAttribute("applications", applications);
        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("verifiedApplications", verifiedApplications);
        model.addAttribute("pendingReview", pendingReview);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("recentActivities", recentActivities);

        // 6️⃣ Load dashboard content fragment inside office layout
        model.addAttribute("content", "home-dashboard");

        return "user-layout";
    }

   

}
