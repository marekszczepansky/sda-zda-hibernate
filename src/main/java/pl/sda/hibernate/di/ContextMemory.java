package pl.sda.hibernate.di;

import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.MemoryCourseDao;
import pl.sda.hibernate.dao.MemoryStudentDao;
import pl.sda.hibernate.dao.MemoryTeacherDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.Screen;
import pl.sda.hibernate.services.SearchService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextMemory {
    private static final Map<Class<?>, Object> componentStore = new ConcurrentHashMap<>();
    private static final ContextMemory INSTANCE = new ContextMemory();

    private ContextMemory() {
        registerComponents();
    }

    private void registerComponents() {
        componentStore.put(Screen.class, new Screen());
        componentStore.put(CourseDao.class, new MemoryCourseDao());
        componentStore.put(StudentDao.class, new MemoryStudentDao());
        componentStore.put(TeacherDao.class, new MemoryTeacherDao());
        componentStore.put(BootstrapService.class, new BootstrapService(
                getComponent(CourseDao.class),
                getComponent(StudentDao.class),
                getComponent(TeacherDao.class)
        ));
        componentStore.put(SearchService.class, new SearchService(
                getComponent(CourseDao.class),
                getComponent(StudentDao.class),
                getComponent(TeacherDao.class),
                getComponent(Screen.class)
        ));
    }

    public static ContextMemory getInstance() {
        return INSTANCE;
    }

    public <K> K getComponent(Class<K> key) {
        //noinspection unchecked
        return (K) componentStore.get(key);
    }

}
