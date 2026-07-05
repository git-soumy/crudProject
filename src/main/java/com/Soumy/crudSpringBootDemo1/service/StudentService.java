package com.Soumy.crudSpringBootDemo1.service;

import com.Soumy.crudSpringBootDemo1.entity.Student;
import com.Soumy.crudSpringBootDemo1.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq) {
        studentReq.setDeleted(false);
        Student studentResp = studentRepository.save(studentReq);

        return studentResp;

    }

    public Student getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentResp.isPresent()){
            return studentResp.get();
        }return null;
    }

    public List<Student> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentReq) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if(existingStudent.isEmpty()){
            return null;
        }

        Student studentTosave = existingStudent.get();
        studentTosave.setName(studentReq.getName());
        studentTosave.setRollNo(studentReq.getRollNo());
        studentTosave.setAge(studentReq.getAge());
        studentTosave.setEmail(studentReq.getEmail());
        studentTosave.setSubject(studentReq.getSubject());
        studentTosave.setDeleted(false);


        return studentRepository.save(studentTosave);

    }

    public Boolean deleteStudent(Long id) {
        Boolean isStudent = studentRepository.existsById(id);

        if(!isStudent){
            return false;
        }

        studentRepository.deleteById(id);
        return true;

    }

    public Boolean deleteAllStudent() {
        if (studentRepository.findAll().isEmpty()) {
            return false;
        }
        studentRepository.deleteAll();
        return true;
    }

    public Boolean deleteStudentSoftly(Long id){
        Optional<Student> existingStudent =
                studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()){
            return false;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);

        return true;

    }
}
