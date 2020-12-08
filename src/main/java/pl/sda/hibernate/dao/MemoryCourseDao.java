package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemoryCourseDao implements CourseDao {
    private final Map<Integer, Course> courseMap = new HashMap<>();
    private AtomicInteger maxId = new AtomicInteger(1);

    @Override
    public List<Course> findByNameLike(String nameTerm) {
        return courseMap.values().stream()
                .filter(course -> course.getName().startsWith(nameTerm.replace("%", "")))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Course entity) {
        entity.setId(maxId.getAndAdd(1));
        courseMap.put(entity.getId(), entity);
    }

    @Override
    public void create(Set<Course> entities) {
        entities.forEach(this::create);
    }

    @Override
    public Course findById(int id) {
        return courseMap.get(id);
    }
}
