package pl.sda.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;
import pl.sda.hibernate.entity.Student;
import pl.sda.hibernate.entity.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class HibernateApp {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

        System.out.println("\n\n--------------------->\n" +
                "Hibernate Session Factory Created");

        createCourses();
        findCourseById(1);
        findCourseByIdAndUpdate(2);
        findCourseByNameLike("course%");
        createStudentsForCourseId(1);
        getAllStudentsForCourse(1);
        createTeachersForCourse(1);
        getAllTeachersForCourse(1);
    }

    private static void createCourses() {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course c = new Course();

            c.setName("course 1");
            c.setStartDate(LocalDate.of(2020, 3, 8));
            session.persist(c);

            c = new Course();
            c.setName("course 2");
            c.setStartDate(LocalDate.of(2020, 4, 1));
            session.persist(c);

            c = new Course();
            c.setName("course 3");
            c.setStartDate(LocalDate.of(2020, 4, 29));
            session.persist(c);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseById(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("\n course with id %d found: %s\n%s\n", id, course.getName(), course);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void findCourseByIdAndUpdate(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            final Course course = session.find(Course.class, id);
            System.out.printf("\n course with id %d found: %s\n%s\n", id, course.getName(), course);
            course.setName("course name updated");
            tx.commit();
            session.evict(course);
            tx = session.beginTransaction();
            course.setName("course name updated after evic");

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
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Query<Course> query = session.createQuery("from Course where name like :nameparam", Course.class);
            query.setParameter("nameparam", term);
            final List<Course> courseList = query.list();
            courseList.forEach(courseItem -> System.out.println("course found: " + courseItem));
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void createStudentsForCourseId(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course course5 = session.find(Course.class, id);

            Student student1 = new Student();
            student1.setName("Marek");
            student1.setEmail("marek@sda.pl");
            student1.setCourse(course5);
            session.persist(student1);
            System.out.println("Student Marek is persisted");

            Student student2 = new Student();
            student2.setName("Tomek");
            session.persist(student2);
            System.out.println("Student Tomek is persisted");
            student2.setEmail("tomek@sda.pl");
            student2.setCourse(course5);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void getAllStudentsForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course course = session.find(Course.class, id);
            Set<Student> courseStudents = course.getStudents();
            System.out.println("Number of students: " + courseStudents.size());
            courseStudents.forEach(studentItem -> System.out.println("name: " + studentItem.getName()));

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void createTeachersForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course course = session.find(Course.class, id);

            Teacher teacher = new Teacher();
            teacher.setName("Wojtek");
            teacher.setSubject("Java");
            teacher.getCourses().add(course);
            session.persist(teacher);

            teacher = new Teacher();
            teacher.setName("Franek");
            teacher.setSubject("Angular");
            teacher.getCourses().add(course);
            session.persist(teacher);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
    }

    private static void getAllTeachersForCourse(int id) {
        System.out.println(getOpenInfo());
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Course course = session.find(Course.class, id);
            System.out.println("Teacher list: ");
            course.getTeachers().forEach(teacher -> System.out.println("teacher: " + teacher));

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && !tx.getRollbackOnly()) {
                tx.rollback();
            }
            throw ex;
        }
        System.out.println(getCloseInfo());
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

    private static String getOpenInfo(){
        return String.format("\n<-----------\n-= Method %s called =-\n", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
    private static String getCloseInfo(){
        return String.format("\n-= Method %s finished =-", Thread.currentThread().getStackTrace()[2].getMethodName());
    }


}
