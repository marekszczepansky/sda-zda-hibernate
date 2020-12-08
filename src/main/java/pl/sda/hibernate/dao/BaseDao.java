package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.NamedEntity;

import java.util.List;
import java.util.Set;

public interface BaseDao<K extends NamedEntity> {
    void create(K entity);

    void create(Set<K> entities);

    K findById(int id);

    List<K> getAll();
}
