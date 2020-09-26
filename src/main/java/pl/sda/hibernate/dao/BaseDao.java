package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.NamedEntity;

public interface BaseDao<T extends NamedEntity> {
    void create(T entity);
    T findById(int id);
}
