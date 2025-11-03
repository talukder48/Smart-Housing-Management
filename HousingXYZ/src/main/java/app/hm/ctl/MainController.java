package app.hm.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.hm.entity.PersonalLoanForm;
import app.hm.repo.PersonalLoanFormRepo;

@Controller
public class MainController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }
    
    @GetMapping("/LoanApply")
    public String LoanApply(Model model){
    	model.addAttribute("loanForm", new PersonalLoanForm() );
    	return  "user/LoanApply";
    }
    
    @Autowired PersonalLoanFormRepo personalLoanFormRepo;
    
    @PostMapping("/Loansave")
    public String saveLoan(@ModelAttribute PersonalLoanForm loanForm, Model model) {
        // TODO: Save form data to database via service
        model.addAttribute("message", "Loan Application Submitted Successfully!");
        loanForm.setId(personalLoanFormRepo.count()+1);
        personalLoanFormRepo.save(loanForm);
        model.addAttribute("loanForm", personalLoanFormRepo.findById(1L));
    	return  "user/LoanApply";
    }
}
