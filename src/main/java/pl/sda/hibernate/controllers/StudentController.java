package pl.sda.hibernate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.hibernate.dao.StudentDao;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentDao studentDao;

    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    // tu proszę o implementację rest endpointów
}
