package com.Soumy.crudSpringBootDemo1.repository;

import com.Soumy.crudSpringBootDemo1.entity.Student;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class StudentRepository {
    public Student saveStudent(Student student) {
        // save to DB
        System.out.println("Inside studentRepository");
        System.out.println("Exiting studentRepository");
        Student s1 = new Student();
        s1.setName("Soumy");
        s1.setAge(30);
        s1.setEmail("Tripathi@gmail.com");
        s1.setRollNo(62);
        s1.setSubject("This is the subject");

        return s1;
    }

}
