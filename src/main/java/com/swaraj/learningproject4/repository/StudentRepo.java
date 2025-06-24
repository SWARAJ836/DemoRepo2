package com.swaraj.learningproject4.repository;

import com.swaraj.learningproject4.entity.student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<student, Long> {
}
