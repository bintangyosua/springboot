package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
        Student skyla = new Student(
                    "Skyla",
                    "skyla@gg.com",
                    LocalDate.of(2002, Month.MARCH, 9)
        );

        Student bintang = new Student(
                    "Bintang",
                    "bintang@gg.com",
                    LocalDate.of(2002, Month.MARCH, 9)
        );

        if (!repository.findStudentByEmail(skyla.getEmail()).isPresent()) repository.save(skyla);
        if (!repository.findStudentByEmail(bintang.getEmail()).isPresent()) repository.save(bintang);
        };
    }
}
