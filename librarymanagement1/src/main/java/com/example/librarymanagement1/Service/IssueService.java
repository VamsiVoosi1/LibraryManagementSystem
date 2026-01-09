package com.example.librarymanagement1.Service;

import com.example.librarymanagement1.Entity.Book;
import com.example.librarymanagement1.Entity.IssueRecord;
import com.example.librarymanagement1.Entity.Student;
import com.example.librarymanagement1.Repository.BookRepository;
import com.example.librarymanagement1.Repository.IssueRepository;
import com.example.librarymanagement1.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private BookRepository bookRepo;

    // 👉 Issue Book (ID based)
    @Transactional
    public void issueBook(Long studentId, Long bookId) {

        // ❌ Already issued to same student
        boolean alreadyIssued =
                issueRepo.existsByStudentIdAndBookIdAndReturnedFalse(studentId, bookId);

        if (alreadyIssued) {
            throw new IllegalStateException(
                    "This book is already issued to the selected student."
            );
        }

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        // ❌ Book not available
        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("Book is not available");
        }

        IssueRecord record = new IssueRecord();
        record.setStudent(student);
        record.setBook(book);
        record.setIssueDateTime(LocalDateTime.now());        record.setReturned(false);

        book.setQuantity(book.getQuantity() - 1);

        bookRepo.save(book);
        issueRepo.save(record);
    }

    // 👉 Return Book
    @Transactional
    public void returnBook(Long issueId) {

        IssueRecord record = issueRepo.findById(issueId)
                .orElseThrow(() -> new IllegalStateException("Issue record not found"));

        if (record.isReturned()) {
            return; // already returned
        }

        Book book = record.getBook();
        book.setQuantity(book.getQuantity() + 1);

        record.setReturned(true);
        record.setReturnDateTime(LocalDateTime.now());

        bookRepo.save(book);
        issueRepo.save(record);
    }

    // 👉 Issued (not returned) list
    public List<IssueRecord> getAllIssuedNotReturned() {
        return issueRepo.findByReturnedFalse();
    }

//    // 👉 All issue history (optional)
//    public List<IssueRecord> getAllIssued() {
//        return issueRepo.findAll();
//    }

    // 👉 Check duplicate issue (used earlier / optional)
    public boolean isBookAlreadyIssuedToStudent(Long studentId, Long bookId) {
        return issueRepo.existsByStudentIdAndBookIdAndReturnedFalse(studentId, bookId);
    }

    public List<IssueRecord> getAllIssued() {
        return issueRepo.findAllByOrderByReturnedAscIssueDateTimeDesc();
    }




}
