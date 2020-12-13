package pl.sda.hibernate.zadanieStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.hibernate.services.Screen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuctionHouseServiceTest {

    private static final String TEST_CITY = "Poznań";
    private static final Room TEST_ROOM1 = new Room(TEST_CITY, 20, 800, 800, "Warta");
    private static final Room TEST_ROOM2 = new Room("Gdańsk", 15, 850, 1500, "Morze", "Plac zabaw");
    private static final Room TEST_ROOM3 = new Room(TEST_CITY, 10, 800, 2500, "Ulica", "Droga");

    @Spy
    final HashSet<Room> rooms = new HashSet<>();

    @Mock
    private Screen screen;

    @InjectMocks
    private AuctionHouseService auctionHouseService;

    @BeforeEach
    void setUp() {
        auctionHouseService.rooms = rooms;
        rooms.add(TEST_ROOM1);
        rooms.add(TEST_ROOM2);
        rooms.add(TEST_ROOM3);
        reset(rooms);
    }

    @Test
    void shouldConfirmInitialise() {
        auctionHouseService.initialise();

        verify(screen, times(1)).println(anyString());
        verify(screen).println(contains("initialised"));
        verify(screen).println("AuctionHouseService initialised");
    }

    @Test
    void shouldInitialiseData() {

        auctionHouseService.initialise();

        verify(rooms, times(7)).add(any(Room.class));
    }

    @Test
    void shouldGetRoomsFromEmptyCity() {

        final List<Room> roomsFromCity = auctionHouseService.getRoomsFromCity(null);

        assertIterableEquals(rooms, roomsFromCity);
    }

    @Test
    void shouldGetRoomsForCity() {

        final List<Room> roomsFromCity = auctionHouseService.getRoomsFromCity(TEST_CITY);

        assertIterableEquals(
                Set.of(TEST_ROOM1, TEST_ROOM3),
                roomsFromCity);
    }

    @Test
    void shouldGetAll() {

        final List<Room> allRooms = auctionHouseService.getAll();

        assertIterableEquals(rooms, allRooms);
    }

    @Test
    void shouldGetRoomsOfSizeBetweenNullParams() {
        final List<Room> roomsOfSizeBetween = auctionHouseService.getRoomsOfSizeBetween(null, null);

        assertIterableEquals(rooms, roomsOfSizeBetween);
    }

    @Test
    void shouldGetRoomsOfSize() {
        final List<Room> roomsOfSizeBetween = auctionHouseService.getRoomsOfSizeBetween(12, 18);

        assertIterableEquals(
                Set.of(TEST_ROOM2),
                roomsOfSizeBetween);
    }
}
