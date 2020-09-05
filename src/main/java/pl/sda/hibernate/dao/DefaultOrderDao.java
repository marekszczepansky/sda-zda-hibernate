package pl.sda.hibernate.dao;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Order;

public class DefaultOrderDao extends DefaultBaseDao<Order> implements OrderDao {

    public DefaultOrderDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Order findById(int id) {
        return super.findById(Order.class, id);
    }
}
