package pl.sda.hibernate.dao;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Place;

public class DefaultPlaceDao extends DefaultBaseDao<Place> implements PlaceDao {

    public DefaultPlaceDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Place findById(int id) {
        return super.findById(Place.class, id);
    }
}
