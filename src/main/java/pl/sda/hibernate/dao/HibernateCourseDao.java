package pl.sda.hibernate.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Course;

import java.util.List;

@Component
public class HibernateCourseDao extends HibernateBaseDao<Course> implements CourseDao {

    public HibernateCourseDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Course findById(final int id) {
        return findById(Course.class, id);
    }

    @Override
    public List<Course> getAll() {
        return super.getAll(Course.class);
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
