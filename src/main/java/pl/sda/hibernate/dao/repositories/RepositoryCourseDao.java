package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    public List<Course> findByNameLike(String nameTerm) {
        screen.println("findByNameLike from repository");
        return courseRepository.findByNameLike(nameTerm);
    }

    @Override
    @Transactional
    public void create(Course entity) {
        screen.println("create from repository");
        courseRepository.save(entity);
    }

    @Override
    @Transactional
    public void create(Set<Course> entities) {
        screen.println("create from Set from repository");
        courseRepository.saveAll(entities);
    }

    @Override
    public Course findById(int id) {
        screen.println("findById from repository");
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAll() {
        screen.println("getAll from repository");
        return courseRepository.findAll();
    }
}
