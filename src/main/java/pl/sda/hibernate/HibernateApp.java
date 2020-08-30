package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.sda.hibernate.dal.CourseDAO;
import pl.sda.hibernate.dal.HibernateCourseDAO;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class HibernateApp {

    static SessionFactory sessionFactory;
    static CourseDAO courseDAO;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        courseDAO = new HibernateCourseDAO(sessionFactory);

        createCourses();
        findCourseById(1);
        findCourseByIdAndUpdate(2);
        findCourseByNameLike("course%");
        findCourseByNameLikeConsumer("co%");
        createStudentsForCourseId(1);
        createStudentsForCourseName("course 3");
        getAllStudentsForCourse(1);
        createTeachersForCourse(1);
        getAllTeachersForCourse(1);
    }

    private static void createCourses() {
        System.out.println(getOpenInfo());

        List<Course> courses = new ArrayList<>();

        Course course = new Course();
        course.setName("course 1");
        course.setStartDate(LocalDate.of(2020, 3, 8));
        courses.add(course);

        course = new Course();
        course.setName("course 2");
        course.setStartDate(LocalDate.of(2020, 4, 1));
        courses.add(course);

        course = new Course();
        course.setName("course 3");
        course.setStartDate(LocalDate.of(2020, 4, 29));
        courses.add(course);

        courseDAO.create(courses);

        System.out.println(getCloseInfo());
    }

    private static void findCourseById(int id) {
        System.out.println(getOpenInfo());
        courseDAO.findById(id).ifPresent(course -> {
            System.out.printf("\nCourse with id %d found:\n%s\n", id, course);
        });

        System.out.println(getCloseInfo());
    }

    private static void findCourseByIdAndUpdate(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("\nCourse with id %d found:\n%s\n", id, course);
            // -----------==============--------------
            course.setName("course name updated");
            tx.commit();
            session.evict(course);
            tx = session.beginTransaction();
            course.setName("course name updated after evict");
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseByNameLike(String term) {
        System.out.println(getOpenInfo());

        final List<Course> list = courseDAO.findByNameLike(term);
        System.out.printf("Query for courses with name like %s\n", term);
        list.forEach(course -> System.out.printf("course found: %s\n", course));

        System.out.println(getCloseInfo());
    }

    private static void findCourseByNameLikeConsumer(String term) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Query<Course> query = session.createQuery(
                    "from Course where name like :nameparam", Course.class);
            query.setParameter("nameparam", term);
            final List<Course> list = query.list();
            System.out.printf("Query for courses with name like %s\n", term);
            list.forEach(course -> System.out.printf("course found: %s\n", course));
        });

        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseId(int id) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);

            Student student = new Student();
            student.setName("Natalia");
            student.setEmail("natalia@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Natalia created for course %s\n", course.getName());

            student = new Student();
            student.setName("Marek");
            student.setEmail("marek@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Marek created for course %s\n", course.getName());

            student = new Student();
            student.setName("Kacper");
            student.setEmail("kacper@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Kacper created for course %s\n", course.getName());
        });

        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseName(String courseName) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Query<Course> query = session.createQuery("from Course where name = :nameparam", Course.class);
            query.setParameter("nameparam", courseName);
            final List<Course> courseList = query.list();
            System.out.printf("Found %d courses having name %s\n", courseList.size(), courseName);
            final Course course = courseList.get(0);

            Student student = new Student();
            student.setName("Jakub");
            student.setEmail("jakub@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Jakub created for course %s\n", course.getName());

            student = new Student();
            student.setName("Przemek");
            student.setEmail("przemek@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Przemek created for course %s\n", course.getName());

            student = new Student();
            student.setName("Stanisław");
            student.setEmail("stanislaw@sda.pl");
            student.setCourse(course);
            session.persist(student);
            System.out.printf("Student Stanisław created for course %s\n", course.getName());
        });

        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourse(int id) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            System.out.printf("Course for id %d name is %s\n", id, course.getName());
            final Set<Student> courseStudents = course.getStudents();
            System.out.printf("Number of students in course %s is %d\n", course.getName(), courseStudents.size());
            courseStudents.forEach(student -> System.out.printf(
                    "Student name: %s, student email: %s\n",
                    student.getName(),
                    student.getEmail()
            ));
        });

        System.out.println(getCloseInfo());
    }

    private static void createTeachersForCourse(int id) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            System.out.printf("Found course for id %d, name %s\n", id, course.getName());

            Teacher teacher = new Teacher();
            teacher.setName("Wojtek");
            teacher.setSubject("Java podstawy");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher Wojtek created with course %s\n", course.getName());

            teacher = new Teacher();
            teacher.setName("Franek");
            teacher.setSubject("Bazy danych");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher Franek created with course %s\n", course.getName());

            session.flush();

            teacher = new Teacher();
            teacher.setName("Artur");
            teacher.setSubject("SQL");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher Artur created with course %s\n", course.getName());

            teacher = new Teacher();
            teacher.setName("Eryk");
            teacher.setSubject("Spring");
            teacher.getCourses().add(course);
            session.persist(teacher);
            System.out.printf("Teacher Eryk created with course %s\n", course.getName());
        });

        System.out.println(getCloseInfo());
    }

    private static void getAllTeachersForCourse(int id) {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            System.out.printf("Found course for id %d, name %s\n", id, course.getName());
            final Set<Teacher> teachers = course.getTeachers();
            System.out.printf("Number of teachers is %d\n", teachers.size());
            teachers.forEach(teacher -> System.out.printf("Teacher: %s, subject: %s\n", teacher.getName(), teacher.getSubject()));
        });

        System.out.println(getCloseInfo());
    }

    private static void templateConsumer() {
        System.out.println(getOpenInfo());

        doInTransaction(session -> {
            // body here
        });

        System.out.println(getCloseInfo());
    }

    private static void doInTransaction(Consumer<Session> action) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            action.accept(session);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    private static void template() {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // body here

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static String getOpenInfo() {
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private static String getCloseInfo() {
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
