package pl.sda.hibernate.entity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Place extends BaseEntity {
    private String name;
    private String address;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name) &&
                Objects.equals(address, place.address) &&
                Objects.equals(phoneNumber, place.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address, phoneNumber);
    }
}
