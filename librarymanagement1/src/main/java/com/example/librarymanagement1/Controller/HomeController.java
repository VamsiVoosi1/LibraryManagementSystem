package com.example.librarymanagement1.Controller;

import com.example.librarymanagement1.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("totalBooks", dashboardService.getTotalBooks());
        model.addAttribute("issuedBooks", dashboardService.getIssuedBooks());
        model.addAttribute("availableBooks", dashboardService.getAvailableBooks());

        return "index";
    }
}
