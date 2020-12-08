package pl.sda.hibernate.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "dao.hibernate", havingValue = "false")
public class MemoryStudentDao implements StudentDao {
    private final Map<Integer, Student> studentMap = new HashMap<>();
    private AtomicInteger maxId = new AtomicInteger(1);

    @Override
    public List<Student> findAllByCourseId(int id) {
        return studentMap.values().stream()
                .filter(student -> student.getCourse() != null && student.getCourse().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Student entity) {
        entity.setId(maxId.getAndAdd(1));
        studentMap.put(entity.getId(), entity);
        if (entity.getCourse() != null) {
            entity.getCourse().getStudents().add(entity);
        }
    }

    @Override
    public void create(Set<Student> entities) {
        entities.forEach(this::create);
    }

    @Override
    public Student findById(int id) {
        return studentMap.get(id);
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(studentMap.values());
    }
}
