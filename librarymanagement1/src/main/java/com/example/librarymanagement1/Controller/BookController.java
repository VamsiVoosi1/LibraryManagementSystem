package com.example.librarymanagement1.Controller;

import com.example.librarymanagement1.Entity.Book;
import com.example.librarymanagement1.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 👉 Books menu page
    @GetMapping
    public String booksHome() {
        return "books-home"; // books-home.html
    }

    // 👉 Open Add Book form
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // add-book.html
    }

    // 👉 Save Book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book);
        return "redirect:/books/view";
    }

    // 👉 View all books
    @GetMapping("/view")
    public String viewBooks(Model model) {

        List<Book> books = bookService.getAllBooks();

        Map<Long, Boolean> issuedMap = new HashMap<>();

        for (Book b : books) {
            issuedMap.put(b.getId(),
                    bookService.isBookIssued(b.getId()));
        }

        model.addAttribute("books", books);
        model.addAttribute("issuedMap", issuedMap);

        return "allbooks";
    }


    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "add-book"; // reuse same form
    }


    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books/view";
    }



}
