package pl.sda.hibernate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.di.Context;
import pl.sda.hibernate.entity.Course;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
    @GetMapping
    public List<Course> getCourses() {
        return Collections.EMPTY_LIST;
    }

    // courses/id
    @GetMapping("{id}")
    public Course getCourse(@PathVariable int id) {
        return Context.getInstance().getComponent(CourseDao.class).findById(id);
    }

    @GetMapping("byName/{name}")
    public List<Course> findByName(@PathVariable String name) {
        return Context.getInstance().getComponent(CourseDao.class).findByNameLike(name + '%');
    }
}
