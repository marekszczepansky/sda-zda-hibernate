package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.entity.Teacher;
import pl.sda.hibernate.repositories.TeacherRepository;
import pl.sda.hibernate.services.Screen;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
public class RepositoryTeacherDao implements TeacherDao {

    private final TeacherRepository teacherRepository;
    private final Screen screen;

    public RepositoryTeacherDao(TeacherRepository teacherRepository, Screen screen) {
        this.teacherRepository = teacherRepository;
        this.screen = screen;
    }

    @Override
    public List<Teacher> getAllTeachersForCourseId(int id) {
        screen.println("getAllTeachersForCourseId from repository");
        return teacherRepository.findByCoursesId(id);
    }

    @Override
    @Transactional
    public void create(Teacher entity) {
        screen.println("create from repository");
        teacherRepository.save(entity);
    }

    @Override
    @Transactional
    public void create(Set<Teacher> entities) {
        screen.println("create from Set from repository");
        teacherRepository.saveAll(entities);
    }

    @Override
    public Teacher findById(int id) {
        screen.println("findById from repository");
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public List<Teacher> getAll() {
        screen.println("getAll from repository");
        return teacherRepository.findAll();
    }
}
