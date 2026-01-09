package com.example.librarymanagement1.Service;

import com.example.librarymanagement1.Entity.Student;
import com.example.librarymanagement1.Repository.IssueRepository;
import com.example.librarymanagement1.Repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private IssueRepository issueRepo;

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void save(Student student) {
        repository.save(student);
    }
    @Transactional
    public void delete(Long studentId) {

        // ❌ Do not delete if student currently has issued books
        if (issueRepo.existsByStudentIdAndReturnedFalse(studentId)) {
            return; // silently ignore
        }

        // ✅ Remove issue history first
        issueRepo.deleteByStudentId(studentId);

        // ✅ Now delete student
        repository.deleteById(studentId);
    }
}
