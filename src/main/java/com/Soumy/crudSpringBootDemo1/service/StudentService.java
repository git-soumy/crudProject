package com.Soumy.crudSpringBootDemo1.service;

import com.Soumy.crudSpringBootDemo1.dto.CreateStudentRequestDto;
import com.Soumy.crudSpringBootDemo1.dto.CreateStudentResponseDto;
import com.Soumy.crudSpringBootDemo1.dto.UpdateStudentRequestDto;
import com.Soumy.crudSpringBootDemo1.dto.UpdateStudentResponseDto;
import com.Soumy.crudSpringBootDemo1.entity.Student;
import com.Soumy.crudSpringBootDemo1.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto) {
        Student student = mapToEntity(studentReqDto);

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);

    }

    public CreateStudentResponseDto getStudent(Long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentResp.isPresent()){
            return mapToDto(studentResp.get());
        }return null;
    }

    public List<CreateStudentResponseDto> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {
        Optional<Student> existingStudent =
                studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()){
            return null;
        }

        Student studentTosave = existingStudent.get();

        studentTosave.setName(studentReq.getName());
        studentTosave.setRollNo(studentReq.getRollNo());
        studentTosave.setAge(studentReq.getAge());
        studentTosave.setSubject(studentReq.getSubject());
        studentTosave.setDeleted(false);
        studentTosave.setUpdatedAt(LocalDateTime.now());


        Student savedStudent = studentRepository.save(studentTosave);
        return mapToUpdateDto(savedStudent);

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

    private Student mapToEntity(CreateStudentRequestDto studentReqDto) {
        Student student = new Student();
        student.setName(studentReqDto.getName());
        student.setAge(studentReqDto.getAge());
        student.setEmail(studentReqDto.getEmail());
        student.setRollNo(studentReqDto.getRollNo());
        student.setSubject(studentReqDto.getSubject());
        student.setDeleted(false);

        return student;
    }
    private CreateStudentResponseDto mapToDto(Student student) {
        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setMessage("Student saved Successfully");
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());
        
        return responseDto;
        
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setMessage("Student updated Successfully");
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;

    }
    
}
