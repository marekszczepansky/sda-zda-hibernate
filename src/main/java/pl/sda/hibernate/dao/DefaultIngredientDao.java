package pl.sda.hibernate.dao;

import pl.sda.hibernate.configuration.HibernateConfiguration;
import pl.sda.hibernate.entity.Ingredient;

public class DefaultIngredientDao extends DefaultBaseDao<Ingredient> implements IngredientDao {
    public DefaultIngredientDao(HibernateConfiguration hibernateConfiguration) {
        super(hibernateConfiguration);
    }

    @Override
    public Ingredient findById(int id) {
        return super.findById(Ingredient.class, id);
    }
}
