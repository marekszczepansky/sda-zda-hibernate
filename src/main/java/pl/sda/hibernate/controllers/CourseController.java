package pl.sda.hibernate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.entity.Course;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final CourseDao courseDao;

    public CourseController(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseDao.getAll();
    }

    // courses/id
    @GetMapping("{id}")
    public Course getCourse(@PathVariable int id) {
        return courseDao.findById(id);
    }

    @GetMapping("byName/{name}")
    public List<Course> findByName(@PathVariable String name) {
        return courseDao.findByNameLike(name + '%');
    }
}
