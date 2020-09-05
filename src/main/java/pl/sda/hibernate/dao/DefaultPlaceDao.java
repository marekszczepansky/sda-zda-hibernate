package pl.sda.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Place;

public class DefaultPlaceDao implements PlaceDao {
    private final HibernateConfiguration hibernateConfiguration;

    public DefaultPlaceDao(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    @Override
    public void create(Place place) {
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();
            session.persist(place);
            transaction.commit();
        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }
    }

    @Override
    public Place findById(int id) {
        Place result;
        Transaction transaction = null;
        try (Session session = hibernateConfiguration
                .getSessionFactory()
                .openSession()) {
            transaction = session.beginTransaction();

            result = session.find(Place.class, id);

            transaction.commit();
        } catch (Throwable throwable) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw throwable;
        }
        return result;
    }
}
