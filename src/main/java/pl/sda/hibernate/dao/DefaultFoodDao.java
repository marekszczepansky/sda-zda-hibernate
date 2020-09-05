package pl.sda.hibernate.dao;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Food;

public class DefaultFoodDao extends DefaultBaseDao<Food> implements FoodDao {

    public DefaultFoodDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Food findById(int id) {
        return super.findById(Food.class, id);
    }
}
