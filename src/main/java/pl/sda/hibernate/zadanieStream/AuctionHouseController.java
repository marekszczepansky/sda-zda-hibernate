package pl.sda.hibernate.zadanieStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auctions")
public class AuctionHouseController {
    private final AuctionHouseService auctionHouseService;

    public AuctionHouseController(AuctionHouseService auctionHouseService) {
        this.auctionHouseService = auctionHouseService;
    }

    @GetMapping
    public List<Room> getAll(){
        return auctionHouseService.getAll();
    }

    @GetMapping("city/{name}")
    public List<Room> getRoomsFromCity(@PathVariable String name){
        return auctionHouseService.getRoomsFromCity(name);
    }

    @GetMapping("size/{min}/{max}")
    public List<Room> getRoomsOfSizeBetween(@PathVariable Integer min, @PathVariable Integer max){
        return auctionHouseService.getRoomsOfSizeBetween(min, max);
    }

    @GetMapping("prices")
    public List<Integer> getAllRoomPrices(){
        return auctionHouseService.getAllRoomPrices();
    }

    @GetMapping("closest/{count}")
    public List<Room> getRoomsClosestCentre(@PathVariable Integer count){
        return auctionHouseService.getRoomsClosestCentre(count);
    }

    @GetMapping("closest/{city}/{count}")
    public List<Room> getRoomsClosestCentreOfCity(@PathVariable Integer count,
                                                  @PathVariable String city){
        return auctionHouseService.getRoomsClosestCentreOfCity(count, city);
    }
}
