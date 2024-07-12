package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email taken.");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) throw new IllegalStateException("Student with ID " + studentId + " does not exist.");
        studentRepository.deleteById(studentId);
    }

    public Student updateStudent(Long studentId, Student newStudent) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) throw new IllegalAccessError("Student not found.");

        return this.studentRepository.findById(studentId).map(student -> {
            student.setName(newStudent.getName());
            student.setEmail(newStudent.getEmail());
            return studentRepository.save(student);
        }).orElseGet(() -> {
            return studentRepository.save(newStudent);
        });
    }
}
