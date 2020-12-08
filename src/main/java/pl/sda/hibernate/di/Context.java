package pl.sda.hibernate.di;

import org.springframework.context.ConfigurableApplicationContext;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.HibernateCourseDao;
import pl.sda.hibernate.dao.HibernateStudentDao;
import pl.sda.hibernate.dao.HibernateTeacherDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.Screen;
import pl.sda.hibernate.services.SearchService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {
    private static final Map<Class<?>, Object> componentStore = new ConcurrentHashMap<>();
    private static final Context INSTANCE = new Context();

    private Context() {
    }

    private void registerComponents(ConfigurableApplicationContext applicationContext) {
        componentStore.put(Screen.class, new Screen());
        componentStore.put(HibernateConfiguration.class, new HibernateConfiguration(applicationContext));
        componentStore.put(CourseDao.class, new HibernateCourseDao(getComponent(HibernateConfiguration.class)));
        componentStore.put(StudentDao.class, new HibernateStudentDao(getComponent(HibernateConfiguration.class)));
        componentStore.put(TeacherDao.class, new HibernateTeacherDao(getComponent(HibernateConfiguration.class)));
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

    public static Context getInstance(ConfigurableApplicationContext applicationContext) {
        if (componentStore.size() == 0) INSTANCE.registerComponents(applicationContext);
        return INSTANCE;
    }

    public <K> K getComponent(Class<K> key) {
        //noinspection unchecked
        return (K) componentStore.get(key);
    }

}
