package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Place;

public interface PlaceDao {
    void create(Place place);

    Place findById(int id);
}
