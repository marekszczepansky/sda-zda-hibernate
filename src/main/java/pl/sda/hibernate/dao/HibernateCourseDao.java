package pl.sda.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.sda.hibernate.entity.Course;

import java.util.List;

public class HibernateCourseDao extends HibernateBaseDao<Course> implements CourseDao {
    public HibernateCourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Course findById(final int id) {
        return getInTransaction(session -> session.find(Course.class, id));
    }

    @Override
    public List<Course> findByNameLike(String nameTerm) {

        return getInTransaction(session -> {
            final Query<Course> courseQuery = session.createQuery(
                    "from Course c where name like :nameparam",
                    Course.class);
            courseQuery.setParameter("nameparam", nameTerm);

            return courseQuery.getResultList();
        });
    }

}
