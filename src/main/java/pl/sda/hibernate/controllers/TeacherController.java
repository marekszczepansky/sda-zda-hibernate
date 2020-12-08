package pl.sda.hibernate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.hibernate.dao.TeacherDao;

@RestController
@RequestMapping("teachers")
public class TeacherController {

    private final TeacherDao teacherDao;

    public TeacherController(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    // tu proszę o implementację rest endpointów

}
