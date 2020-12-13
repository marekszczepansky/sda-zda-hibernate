package pl.sda.hibernate.zadanieStream;

import org.springframework.stereotype.Service;
import pl.sda.hibernate.services.Screen;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AuctionHouseService {
    private final Screen screen;
    private Set<Room> rooms = new HashSet<>();

    public AuctionHouseService(Screen screen) {
        this.screen = screen;
    }


    @PostConstruct
    public void initialise(){
        rooms.add(new Room("Poznań", 16, 800, 800));
        rooms.add(new Room("Gdańsk", 15, 850, 1500));
        rooms.add(new Room("Poznań", 13, 800, 2500));
        rooms.add(new Room("Gdańsk", 26, 950, 900));
        rooms.add(new Room("Poznań", 23, 700, 4500));
        rooms.add(new Room("Warszawa", 14, 888, 100));
        rooms.add(new Room("Poznań", 27, 1500, 300));
        screen.println("AuctionHouseService initialised");
    }

    public List<Room> getRoomsFromCity(String city){
        if (city == null) {
            return getAll();
        }
        return rooms.stream()
                .filter((Room room) -> city.equals(room.getCity()))
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsOfSizeBetween(Integer min, Integer max) {
        // TODO: implement
        return Collections.EMPTY_LIST;
    }

    public List<Integer> getAllRoomPrices() {
        // TODO: implement
        return Collections.EMPTY_LIST;
    }

    public List<Room> getRoomsClosestCentre(Integer count) {
        // TODO: implement
        return Collections.EMPTY_LIST;
    }

    public List<Room> getRoomsClosestCentreOfCity(Integer count, String city) {
        // TODO: implement
        return Collections.EMPTY_LIST;
    }

    public List<Room> getAll() {
        return new ArrayList<>(rooms);
    }

}
