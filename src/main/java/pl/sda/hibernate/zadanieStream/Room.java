package pl.sda.hibernate.zadanieStream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Room {
    private String city;
    private Integer size;
    private Integer price;
    private Integer distance;
    private Set<String> windowSides = new HashSet<>();

    public Room(String city, Integer size, Integer price, Integer distance) {
        this.city = city;
        this.size = size;
        this.price = price;
        this.distance = distance;
    }

    public Room(String city, Integer size, Integer price, Integer distance, String... windowSides) {
        this(city, size, price, distance);
        this.windowSides.addAll(Arrays.asList(windowSides.clone()));
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<String> getWindowSides() {
        return windowSides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(city, room.city) &&
                Objects.equals(size, room.size) &&
                Objects.equals(price, room.price) &&
                Objects.equals(distance, room.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, size, price, distance);
    }

    @Override
    public String toString() {
        return "Room{" +
                "city='" + city + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", distance=" + distance +
                '}';
    }
}
