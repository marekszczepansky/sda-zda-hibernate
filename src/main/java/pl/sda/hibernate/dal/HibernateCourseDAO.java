package pl.sda.hibernate.dal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateCourseDAO implements CourseDAO, AutoCloseable {

    private final HibernateSessionHelper hibernateSessionHelper;

    public HibernateCourseDAO(SessionFactory sessionFactory) {
        hibernateSessionHelper =  new HibernateSessionHelper(sessionFactory);
    }

    @Override
    public void create(Course course) {
        hibernateSessionHelper.doInTransaction(session -> session.persist(course));
    }

    @Override
    public void create(List<Course> courses) {
        hibernateSessionHelper.doInTransaction(session -> courses.forEach(session::persist));
    }

    @Override
    public Optional<Course> findById(int id) {
        return Optional.ofNullable(
                hibernateSessionHelper.returnInTransaction(session -> session.find(Course.class, id))
        );
    }

    @Override
    public List<Course> findByNameLike(String term) {
        return hibernateSessionHelper.returnInTransaction(session -> {
            final Query<Course> query = session.createQuery("from Course where name like :nameparam", Course.class);
            query.setParameter("nameparam", term);
            return query.list();
        });
    }

    @Override
    public List<Course> findByName(String name) {
        return hibernateSessionHelper.returnInTransaction(session -> {
            final Query<Course> query = session.createQuery("from Course where name = :nameparam", Course.class);
            query.setParameter("nameparam", name);
            return query.list();
        });
    }

    @Override
    public void updateNameById(int id, String name) {
        hibernateSessionHelper.doInTransaction(session -> {
            final Course course = session.find(Course.class, id);
            course.setName(name);
        });
    }

    private void doInTransaction(Consumer<Session> action) {
        hibernateSessionHelper.doInTransaction(action);
    }

    private <K> K returnInTransaction(Function<Session, K> action) {
        return hibernateSessionHelper.returnInTransaction(action);
    }

    @Override
    public void close() throws Exception {
        hibernateSessionHelper.close();
    }
}
