package app.hm.ctl;

import app.hm.entity.LoanApplication;
import app.hm.entity.LoanDocument;
import app.hm.repo.LoanApplicationRepository;
import app.hm.repo.LoanDocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class LoanDocumentController {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanDocumentRepository loanDocumentRepository;


    // ===== Display Upload Form & List of Documents =====
    @GetMapping("/upload-documents")
    public String uploadDocumentsForm(@RequestParam("loanId") Long loanId, Model model) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        List<LoanDocument> documents = loanDocumentRepository.findByLoanApplication(loan);

        model.addAttribute("loan", loan);
        model.addAttribute("documents", documents);
        model.addAttribute("content", "user/loan-documents"); // Thymeleaf fragment

        return "user-layout";
    }

    private final String uploadDir = "C:/BHBFC/uploads/"; // Absolute path

    @PostMapping("/upload-documents")
    public String handleFileUpload(@RequestParam("loanId") Long loanId,
                                   @RequestParam("type") String type,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!file.isEmpty()) {
            // Create folder if it doesn't exist
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadFolder, fileName);

            // Save file
            file.transferTo(dest);

            // Save document record in DB
            LoanDocument document = new LoanDocument();
            document.setLoanApplication(loan);
            document.setType(type);
            document.setFileName(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setFilePath(dest.getAbsolutePath());

            loanDocumentRepository.save(document);
        }

        return "redirect:/customer/upload-documents?loanId=" + loanId;
    }

    // ===== Download Document =====
    @GetMapping("/download-document/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable("id") Long id) throws IOException {
        LoanDocument document = loanDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        File file = new File(document.getFilePath());
        if (!file.exists()) {
            throw new RuntimeException("File not found on server");
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, document.getContentType())
                .body(resource);
    }
}
