package pl.sda.hibernate.zadanieStream;

import org.springframework.stereotype.Service;
import pl.sda.hibernate.services.Screen;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuctionHouseService {
    private final Screen screen;
    Set<Room> rooms = new HashSet<>();

    public AuctionHouseService(Screen screen) {
        this.screen = screen;
    }


    @PostConstruct
    public void initialise() {
        rooms.add(new Room("Poznań", 16, 800, 800, "Warta"));
        rooms.add(new Room("Gdańsk", 15, 850, 1500, "Morze", "Plac zabaw"));
        rooms.add(new Room("Poznań", 13, 800, 2500, "Ulica", "Droga"));
        rooms.add(new Room("Gdańsk", 26, 950, 900,  "Wisła", "Kanał"));
        rooms.add(new Room("Poznań", 23, 700, 4500));
        rooms.add(new Room("Warszawa", 14, 888, 100, "Stadion", "Stacja metra"));
        rooms.add(new Room("Poznań", 27, 1500, 300, "Śmietnik", "Podwórze"));
        screen.println("AuctionHouseService initialised");
    }

    public List<Room> getRoomsFromCity(final String city) {
        if (city == null) {
            return getAll();
        }
        return rooms.stream()
                .filter((Room room) -> city.equals(room.getCity()))
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsOfSizeBetween(final Integer min, final Integer max) {
        return rooms.stream()
                .filter(room -> room.getSize() >= (min == null ? 0 : min))
                .filter(room -> room.getSize() <= (max == null ? Integer.MAX_VALUE : max))
                .collect(Collectors.toList());
    }

    public List<Integer> getAllRoomPrices() {
        return rooms.stream()
                .map(Room::getPrice)
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsClosestCentre(Integer count) {
        return rooms.stream()
                .sorted(Comparator.comparing(Room::getDistance))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsClosestCentreOfCity(Integer count, String city) {
        return rooms.stream()
                .filter((Room room) -> city.equals(room.getCity()))
                .sorted(Comparator.comparing(Room::getDistance))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Room> getAll() {
        return new ArrayList<>(rooms);
    }

    public Map<String, List<Room>> getAllByCity() {
        return rooms.stream()
                .collect(Collectors.groupingBy(Room::getCity));
    }

    public List<Set<String>> getWindowSides() {
        return rooms.stream()
                .map(Room::getWindowSides)
                .collect(Collectors.toList());
    }

    public List<String> getWindowSidesList() {
        return rooms.stream()
                .flatMap(room -> room.getWindowSides().stream())
                .collect(Collectors.toList());
    }

}
