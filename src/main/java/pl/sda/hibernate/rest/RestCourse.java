package pl.sda.hibernate.rest;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.di.Context;
import pl.sda.hibernate.entity.Course;

import java.util.List;

@RestController
public class RestCourse {
    @GetMapping(path = "courses")
    public List<Course> getCourses() {
        final CourseDao component = Context.getInstance(null).getComponent(CourseDao.class);
        return component.findByNameLike("course%");
    }
    @GetMapping(path = "courses/{id}")
    public Course getCourse(@PathVariable int id) {
        final CourseDao component = Context.getInstance(null).getComponent(CourseDao.class);
        return component.findById(id);
    }
}
