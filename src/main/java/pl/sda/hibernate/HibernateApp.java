package pl.sda.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.dao.CourseDao;
import pl.sda.hibernate.dao.HibernateTeacherDao;
import pl.sda.hibernate.dao.HibernateCourseDao;
import pl.sda.hibernate.dao.HibernateStudentDao;
import pl.sda.hibernate.dao.StudentDao;
import pl.sda.hibernate.dao.TeacherDao;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;
import pl.sda.hibernate.services.BootstrapService;
import pl.sda.hibernate.services.Screen;
import pl.sda.hibernate.services.SearchService;

public class HibernateApp {

    static SessionFactory sessionFactory;

    private static CourseDao courseDao;
    private static StudentDao studentDao;
    private static TeacherDao teacherDao;

    private static BootstrapService bootstrapService;
    private static SearchService searchService;

    public static void main(String[] args) {

        sessionFactory = HibernateConfiguration.getInstance().getSessionFactory();

        courseDao = new HibernateCourseDao(sessionFactory);
        studentDao = new HibernateStudentDao(sessionFactory);
        teacherDao = new HibernateTeacherDao(sessionFactory);

        bootstrapService = new BootstrapService(courseDao, studentDao, teacherDao);
        searchService = new SearchService(courseDao, studentDao, teacherDao, new Screen());

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        bootstrapService.createCourses();
        bootstrapService.createStudents();
        bootstrapService.createTeachers();

        searchService.findCourseById(1);
        searchService.findCourseByNameLike("course%");
        searchService.getAllStudentsForCourseIdByQuery(1);
        searchService.getAllTeachersForCourse(1);
    }


}
