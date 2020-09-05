package pl.sda.hibernate.services;

import pl.sda.hibernate.dao.DefaultIngredientDao;
import pl.sda.hibernate.entity.Ingredient;

public class BootstrapService {
    private final DefaultIngredientDao ingredientDao;

    public BootstrapService(DefaultIngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public void createIngredients() {
        Ingredient ingredient = new Ingredient();

        ingredient.setName("Sałata");
        ingredient.setKcal(50);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Pomidor");
        ingredient.setKcal(80);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Wołowina");
        ingredient.setKcal(200);
        ingredientDao.create(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Bekon");
        ingredient.setKcal(100);
        ingredientDao.create(ingredient);
    }
}
