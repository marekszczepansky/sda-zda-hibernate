package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.aop.LogEntry;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.repositories.CourseRepository;
import pl.sda.hibernate.services.Screen;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
public class RepositoryCourseDao implements CourseDao {

    private final CourseRepository courseRepository;
    private final Screen screen;

    public RepositoryCourseDao(CourseRepository courseRepository, Screen screen) {
        this.courseRepository = courseRepository;
        this.screen = screen;
    }

    @Override
    @LogEntry
    public List<Course> findByNameLike(String nameTerm) {
        return courseRepository.findByNameLike(nameTerm);
    }

    @Override
    @Transactional
    @LogEntry
    public void create(Course entity) {
        courseRepository.save(entity);
    }

    @Override
    @Transactional
    @LogEntry
    public void create(Set<Course> entities) {
        courseRepository.saveAll(entities);
    }

    @Override
    @LogEntry
    public Course findById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    @LogEntry
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
}
