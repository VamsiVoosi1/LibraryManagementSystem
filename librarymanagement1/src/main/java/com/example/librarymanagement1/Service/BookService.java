package com.example.librarymanagement1.Service;

import com.example.librarymanagement1.Entity.Book;
import com.example.librarymanagement1.Repository.BookRepository;
import com.example.librarymanagement1.Repository.IssueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    @Autowired
    private IssueRepository issueRepo;

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public void save(Book book) {
        repo.save(book);
    }

    public Book getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public boolean isBookIssued(Long bookId) {
        return issueRepo.existsByBookIdAndReturnedFalse(bookId);
    }

    @Transactional
    public void delete(Long bookId) {

        // ❌ do not delete if currently issued
        if (issueRepo.existsByBookIdAndReturnedFalse(bookId)) {
            return;
        }

        // ✅ delete issue history first
        issueRepo.deleteByBookId(bookId);

        // ✅ now delete book
        repo.deleteById(bookId);
    }




}

