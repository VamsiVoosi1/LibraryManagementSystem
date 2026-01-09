package com.example.librarymanagement1.Controller;

import com.example.librarymanagement1.Service.BookService;
import com.example.librarymanagement1.Service.IssueService;
import com.example.librarymanagement1.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @Autowired
    private IssueService issueService;

    // ✅ MAIN ISSUE / RETURN PAGE (ONLY ONE GET MAPPING)
    @GetMapping
    public String issuePage(Model model) {

        model.addAttribute("students", studentService.getAll());
        model.addAttribute("books", bookService.getAllBooks());

        // 🔥 MUST LOAD ALL RECORDS (ISSUED + RETURNED)
        model.addAttribute("issued", issueService.getAllIssued());

        return "issue"; // issue.html
    }

    // ✅ ISSUE BOOK
    @PostMapping("/book")
    public String issueBook(@RequestParam Long studentId,
                            @RequestParam Long bookId,
                            RedirectAttributes redirectAttributes) {

        try {
            issueService.issueBook(studentId, bookId);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Book issued successfully."
            );

        } catch (IllegalStateException ex) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    ex.getMessage()
            );
        }

        return "redirect:/issue";
    }

    // ✅ RETURN BOOK
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {

        issueService.returnBook(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Book returned successfully."
        );

        return "redirect:/issue";
    }
}
