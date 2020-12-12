package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.aop.LogAllEntry;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.repositories.StudentRepository;
import pl.sda.hibernate.services.Screen;

import java.util.List;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
@Transactional(readOnly = true)
public class RepositoryStudentDao implements StudentDao {

    private final StudentRepository studentRepository;
    private final Screen screen;

    public RepositoryStudentDao(StudentRepository studentRepository, Screen screen) {
        this.studentRepository = studentRepository;
        this.screen = screen;
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        screen.println("findAllByCourseId from repository");
        return studentRepository.findByCourseId(id);
    }

    @Override
    @Transactional
    public void create(Student entity) {
        screen.println("create from repository");
        studentRepository.save(entity);
    }

    @Override
    @Transactional
    public void create(Set<Student> entities) {
        screen.println("create from Set from repository");
        studentRepository.saveAll(entities);
    }

    @Override
    public Student findById(int id) {
        screen.println("findById from repository");
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAll() {
        screen.println("getAll from repository");
        return studentRepository.findAll();
    }
}
