package com.example.librarymanagement1.Service;

import com.example.librarymanagement1.Repository.BookRepository;
import com.example.librarymanagement1.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private IssueRepository issueRepo;

    public int getAvailableBooks() {
        Integer count = bookRepo.getTotalAvailableBooks();
        return count == null ? 0 : count;
    }

    public long getIssuedBooks() {
        return issueRepo.countByReturnedFalse();
    }

    public long getTotalBooks() {
        return getAvailableBooks() + getIssuedBooks();
    }
}
