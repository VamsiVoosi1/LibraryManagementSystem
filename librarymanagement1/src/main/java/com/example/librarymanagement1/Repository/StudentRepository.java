package com.example.librarymanagement1.Repository;

import com.example.librarymanagement1.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

