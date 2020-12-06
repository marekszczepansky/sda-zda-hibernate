package pl.sda.hibernate.di;

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
    private static Context INSTANCE = new Context();
    private static Map<Class<?>, Object> componentStore = new ConcurrentHashMap<>();

    private Context() {
    }

    public static Context getInstance() {
        return INSTANCE;
    }

    public HibernateConfiguration getHibernateConfiguration() {
        return (HibernateConfiguration) componentStore
                .computeIfAbsent(HibernateConfiguration.class, aClass -> new HibernateConfiguration());
    }

    public CourseDao getCourseDao() {
        return (CourseDao) componentStore
                .computeIfAbsent(CourseDao.class, aClass -> new HibernateCourseDao(getHibernateConfiguration()));
    }

    public StudentDao getStudentDao() {
        return (StudentDao) componentStore
                .computeIfAbsent(StudentDao.class, aClass -> new HibernateStudentDao(getHibernateConfiguration()));
    }

    public TeacherDao getTeacherDao() {
        return (TeacherDao) componentStore
                .computeIfAbsent(TeacherDao.class, aClass -> new HibernateTeacherDao(getHibernateConfiguration()));
    }

    public BootstrapService getBootstrapService() {
        return (BootstrapService) componentStore
                .computeIfAbsent(BootstrapService.class, aClass -> new BootstrapService(getCourseDao(), getStudentDao(), getTeacherDao()));
    }

    public SearchService getSearchService() {
        return (SearchService) componentStore
                .computeIfAbsent(SearchService.class, aClass -> new SearchService(getCourseDao(), getStudentDao(), getTeacherDao(), getScreen()));
    }

    public Screen getScreen() {
        return (Screen) componentStore
                .computeIfAbsent(Screen.class, aClass -> new Screen());
    }

}
