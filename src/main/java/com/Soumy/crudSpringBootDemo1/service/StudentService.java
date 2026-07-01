package com.Soumy.crudSpringBootDemo1.service;

import com.Soumy.crudSpringBootDemo1.entity.Student;
import com.Soumy.crudSpringBootDemo1.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq) {
        System.out.println("Inside studentService");
        Student studentResp = studentRepository.saveStudent(studentReq);
        System.out.println("Exiting studentService");

        return studentResp;

    }
}
