package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.BaseEntity;

public interface BaseDao<F extends BaseEntity> {
    void create(F food);

    F findById(int id);

}
