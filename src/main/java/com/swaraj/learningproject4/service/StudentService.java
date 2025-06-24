package com.swaraj.learningproject4.service;

import com.swaraj.learningproject4.entity.student;
import com.swaraj.learningproject4.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public List<student> getAllStudents(){
        return studentRepo.findAll();
    }

    public student saveStudent(student stu){
        return studentRepo.save(stu);
    }
}
