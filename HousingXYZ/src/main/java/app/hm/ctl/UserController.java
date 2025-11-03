package app.hm.ctl;

import java.security.Principal;
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
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // Fetch loans
        List<LoanApplication> applications = loanApplicationRepository.findByUser(user);

        // Fetch documents for each loan
        Map<Long, List<LoanDocument>> documentsMap = new HashMap<>();
        for (LoanApplication loan : applications) {
            List<LoanDocument> docs = loanDocumentRepository.findByLoanApplication(loan);
            documentsMap.put(loan.getId(), docs);
        }

        // Stats
        long totalApplications = applications.size();
        long approvedApplications = applications.stream()
                .filter(app -> app.getStatus() == LoanStatus.APPROVED).count();
        long pendingApplications = applications.stream()
                .filter(app -> app.getStatus() == LoanStatus.PENDING).count();
        long rejectedApplications = applications.stream()
                .filter(app -> app.getStatus() == LoanStatus.REJECTED).count();

        // Add attributes
        model.addAttribute("user", user);
        model.addAttribute("applications", applications);
        model.addAttribute("documentsMap", documentsMap);
        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("approvedApplications", approvedApplications);
        model.addAttribute("pendingApplications", pendingApplications);
        model.addAttribute("rejectedApplications", rejectedApplications);
        model.addAttribute("content", "home-dashboard");

        return "user-layout";
    }


   

}
