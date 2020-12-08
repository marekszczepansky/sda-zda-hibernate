package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemoryTeacherDao implements TeacherDao {
    private final Map<Integer, Teacher> teacherMap = new HashMap<>();
    private AtomicInteger maxId = new AtomicInteger(1);

    @Override
    public List<Teacher> getAllTeachersForCourseId(int id) {
        return teacherMap.values().stream()
                .filter(teacher -> teacher.getCourses().stream().anyMatch(course -> course.getId() == id))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Teacher entity) {
        entity.setId(maxId.getAndAdd(1));
        teacherMap.put(entity.getId(), entity);
        entity.getCourses().forEach( course -> course.getTeachers().add(entity));
    }

    @Override
    public void create(Set<Teacher> entities) {
        entities.forEach(this::create);
    }

    @Override
    public Teacher findById(int id) {
        return teacherMap.get(id);
    }

    @Override
    public List<Teacher> getAll() {
        return new ArrayList<>(teacherMap.values());
    }
}
