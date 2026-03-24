import java.util.ArrayList;
import java.util.List;

public class UseCase3InventorySetup {
    public static void main(String[] args) {
        // 1. Initialize the Centralized Inventory
        RoomInventory inventory = new RoomInventory();

        // 2. Setup the Room Objects (Static Data)
        List<Room> hotelRooms = new ArrayList<>();
        hotelRooms.add(new Room("Single Room", 1, 250, 1500.0));
        hotelRooms.add(new Room("Double Room", 2, 400, 2500.0));
        hotelRooms.add(new Room("Suite Room", 3, 750, 5000.0));

        // 3. Display the Inventory Status
        System.out.println("Hotel Room Inventory Status\n");

        for (Room room : hotelRooms) {
            String type = room.getType();
            // Pulling availability from the Centralized Source of Truth
            int available = inventory.getRoomAvailability().getOrDefault(type, 0);

            System.out.println(type + ":");
            System.out.println("Beds: " + room.getBeds());
            System.out.println("Size: " + room.getSize() + " sqft");
            System.out.println("Price per night: " + room.getPricePerNight());
            System.out.println("Available Rooms: " + available);
            System.out.println();
        }
    }
}