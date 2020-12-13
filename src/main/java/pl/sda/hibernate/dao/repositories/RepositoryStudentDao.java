package pl.sda.hibernate.dao.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.repositories.StudentRepository;
import pl.sda.hibernate.services.Screen;

import java.util.List;

@Transactional(readOnly = true)
@Component
@ConditionalOnProperty(value = "dao.implementation", havingValue = "repository")
public class RepositoryStudentDao extends RepositoryBaseDao<Student> implements StudentDao {

    private final StudentRepository studentRepository;
    private final Screen screen;

    public RepositoryStudentDao(StudentRepository studentRepository, Screen screen) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.screen = screen;
    }

    @Override
    public List<Student> findAllByCourseId(int id) {
        screen.println("findAllByCourseId from repository");
        return studentRepository.findByCourseId(id);
    }
}
