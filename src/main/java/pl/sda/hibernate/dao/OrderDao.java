package pl.sda.hibernate.dao;

import pl.sda.hibernate.entity.Order;

public interface OrderDao {
    void create(Order order);

    Order findById(int id);
}
