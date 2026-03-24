import java.util.HashMap;
import java.util.Map;

/**
 * =================================================================
 * CLASS - RoomSearchService
 * =================================================================
 * Use Case 4: Room Search & Availability Check
 * Description:
 * This class provides search functionality for guests to view available rooms.
 * It reads room availability from inventory and room details from Room objects.
 */
class RoomSearchService {

    /**
     * Displays available rooms along with their details and pricing.
     * This method performs read-only access to inventory and room data.
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("--- Available Rooms Search Results ---");

        // Check and display Single Room availability
        if (availability.get("Single") != null && availability.get("Single") > 0) {
            displayRoomInfo(singleRoom, availability.get("Single"));
        }

        // Check and display Double Room availability
        if (availability.get("Double") != null && availability.get("Double") > 0) {
            displayRoomInfo(doubleRoom, availability.get("Double"));
        }

        // Check and display Suite Room availability
        if (availability.get("Suite") != null && availability.get("Suite") > 0) {
            displayRoomInfo(suiteRoom, availability.get("Suite"));
        }
    }

    private void displayRoomInfo(Room room, int count) {
        System.out.println("Room Type: " + room.getType());
        System.out.println("Price: $" + room.getPrice());
        System.out.println("Available Count: " + count);
        System.out.println("--------------------------------------");
    }
}

/**
 * Supporting Room Data Model
 */
class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
}

/**
 * Supporting Inventory Model
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return inventory;
    }
}

/**
 * =================================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =================================================================
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {
        // 1. Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addInventory("Single", 5);
        inventory.addInventory("Double", 2);
        inventory.addInventory("Suite", 0); // Should not display in search

        // 2. Define Room Details
        Room single = new Room("Single", 100.0);
        Room doubleRm = new Room("Double", 180.0);
        Room suite = new Room("Suite", 350.0);

        // 3. Initialize Service and Perform Search
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, single, doubleRm, suite);
    }
}