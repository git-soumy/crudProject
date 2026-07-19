package com.Soumy.crudSpringBootDemo1.controller;

import com.Soumy.crudSpringBootDemo1.dto.CreateStudentRequestDto;
import com.Soumy.crudSpringBootDemo1.dto.CreateStudentResponseDto;
import com.Soumy.crudSpringBootDemo1.dto.UpdateStudentRequestDto;
import com.Soumy.crudSpringBootDemo1.dto.UpdateStudentResponseDto;
import com.Soumy.crudSpringBootDemo1.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Validation -> spring-boot-starter-validation

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(
           @Valid @RequestBody CreateStudentRequestDto studentRequestDto) {

        CreateStudentResponseDto createStudent =
                studentService.createStudent(studentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createStudent);
    }
    // read one student
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id) {
        CreateStudentResponseDto studentResp = studentService.getStudent(id);


        return ResponseEntity.ok(studentResp);
    }

    // read one student
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent() {
        List<CreateStudentResponseDto> studentList = studentService.getAllStudent();

        return ResponseEntity.ok(studentList);
    }

    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id,
                                                                  @RequestBody UpdateStudentRequestDto studentReq) {
        UpdateStudentResponseDto studentResp =
                studentService.updateStudent(id, studentReq);

        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);


        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllStudent() {
        Boolean isDeleted = studentService.deleteAllStudent();
        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("All Record Deleted");
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
       studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }




}
