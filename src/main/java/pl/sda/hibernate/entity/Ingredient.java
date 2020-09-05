package pl.sda.hibernate.entity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Ingredient extends BaseEntity {
    private String name;
    private int kcal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ingredient that = (Ingredient) o;
        return kcal == that.kcal &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, kcal);
    }
}
