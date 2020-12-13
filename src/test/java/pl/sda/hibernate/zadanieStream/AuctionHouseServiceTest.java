package pl.sda.hibernate.zadanieStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.hibernate.services.Screen;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionHouseServiceTest {

    @Mock
    private Screen screen;

    @InjectMocks
    private AuctionHouseService auctionHouseService;

    @BeforeEach
    void setUp() {
        final HashSet<Room> rooms = new HashSet<>();
        auctionHouseService.rooms = rooms;
        rooms.add(new Room("Poznań", 16, 800, 800, "Warta"));
        rooms.add(new Room("Gdańsk", 15, 850, 1500, "Morze", "Plac zabaw"));
        rooms.add(new Room("Poznań", 13, 800, 2500, "Ulica", "Droga"));
    }
}
