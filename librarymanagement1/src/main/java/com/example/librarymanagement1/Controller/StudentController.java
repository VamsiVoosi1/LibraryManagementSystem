package com.example.librarymanagement1.Controller;

import com.example.librarymanagement1.Entity.Student;
import com.example.librarymanagement1.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    // 👉 Students menu page
    @GetMapping
    public String studentsHome() {
        return "students-home"; // students-home.html
    }

    // 👉 Add Student form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // 👉 Save Student
    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students/view";
    }

    // 👉 View all students
    @GetMapping("/view")
    public String viewStudents(Model model) {
        model.addAttribute("students", service.getAll());
        return "students"; // students.html
    }

    // 👉 Edit Student Form
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.getById(id));
        return "add-student"; // reuse same form
    }

    // 👉 Delete Student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students/view";
    }
}
