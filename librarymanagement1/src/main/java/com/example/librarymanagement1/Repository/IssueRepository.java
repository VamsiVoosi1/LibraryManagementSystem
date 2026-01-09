package com.example.librarymanagement1.Repository;

import com.example.librarymanagement1.Entity.IssueRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface IssueRepository extends JpaRepository<IssueRecord, Long> {
    long countByReturnedFalse();

    List<IssueRecord> findByReturnedFalse();

    List<IssueRecord> findByStudentIdAndReturnedFalse(Long studentId);

    List<IssueRecord> findAllByOrderByReturnedAscIssueDateTimeDesc();


    boolean existsByStudentIdAndBookIdAndReturnedFalse(Long studentId, Long bookId);

    boolean existsByBookId(Long bookId);


    boolean existsByBookIdAndReturnedFalse(Long bookId);

    @Modifying
    @Transactional
    void deleteByBookId(Long bookId);

    boolean existsByStudentIdAndReturnedFalse(Long studentId);

    @Modifying
    @Transactional
    void deleteByStudentId(Long studentId);









}

