//package com.tmt.challenge.model;
//
//import com.tmt.challenge.repository.StudentRepo;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static java.time.Month.JANUARY;
//
//@Configuration
//public class StudentConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepo repository) {
//        return args -> {
//            Student fadhil = new Student(
//                    "Ridhan",
//                    "Fadhilah",
//                    "fadhil@yopmail.com",
//                    LocalDate.of(1997, JANUARY, 12)
//            );
//
//            Student mariam = new Student(
//                    "Mariam",
//                    "Jamal",
//                    "mariam.jamal@yopmail.com",
//                    LocalDate.of(2000, JANUARY, 5)
//            );
//
//            repository.saveAll(
//                    List.of(fadhil, mariam)
//            );
//        };
//    }
//}
