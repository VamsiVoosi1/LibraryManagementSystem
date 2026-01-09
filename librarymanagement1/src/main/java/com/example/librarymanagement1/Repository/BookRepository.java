package com.example.librarymanagement1.Repository;

import com.example.librarymanagement1.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT SUM(b.quantity) FROM Book b")
    Integer getTotalAvailableBooks();
}

