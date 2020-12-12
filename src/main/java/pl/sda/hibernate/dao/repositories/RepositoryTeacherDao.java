package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.entity.Teacher;
import pl.sda.hibernate.repositories.TeacherRepository;
import pl.sda.hibernate.services.Screen;

import java.util.List;

@Transactional(readOnly = true)
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
public class RepositoryTeacherDao extends RepositoryBaseDao<Teacher> implements TeacherDao {

    private final TeacherRepository teacherRepository;
    private final Screen screen;

    public RepositoryTeacherDao(TeacherRepository teacherRepository, Screen screen) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
        this.screen = screen;
    }

    @Override
    public List<Teacher> getAllTeachersForCourseId(int id) {
        screen.println("getAllTeachersForCourseId from repository");
        return teacherRepository.findByCoursesId(id);
    }
}
