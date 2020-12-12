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

@Transactional(readOnly = true)
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
public class RepositoryCourseDao extends RepositoryBaseDao<Course> implements CourseDao {

    private final Screen screen;
    private final CourseRepository courseRepository;

    public RepositoryCourseDao(CourseRepository courseRepository, Screen screen) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.screen = screen;
    }

    @Override
    @LogEntry
    public List<Course> findByNameLike(String nameTerm) {
        return courseRepository.findByNameLike(nameTerm);
    }

}
