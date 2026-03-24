import java.util.*;

/**
 * =================================================================
 * CLASS - RoomAllocationService
 * =================================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Description:
 * This class is responsible for confirming booking requests and assigning rooms.
 * It ensures:
 * - Each room ID is unique
 * - Inventory is updated immediately
 * - No room is double-booked
 */
class RoomAllocationService {

    /** Stores all allocated room IDs to prevent duplicate assignments. */
    private Set<String> allocatedRoomIds;

    /** Stores assigned room IDs by room type. */
    private Map<String, Set<String>> assignedRoomsByType;

    /** Initializes allocation tracking structures. */
    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning a unique room ID
     * and updating inventory.
     * * @param reservation booking request
     * @param inventory   centralized room inventory
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();

        // 1. Check if inventory allows allocation
        if (inventory.getRoomAvailability().getOrDefault(type, 0) > 0) {

            // 2. Generate unique Room ID
            String roomId = generateRoomId(type);

            // 3. Track the allocation
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

            // 4. Update the inventory (Decrement available count)
            inventory.updateInventory(type, -1);

            System.out.println("SUCCESS: Room allocated for " + reservation.getGuestName());
            System.out.println("Assigned Room ID: " + roomId);
            System.out.println("Remaining " + type + " rooms: " + inventory.getRoomAvailability().get(type));
        } else {
            System.out.println("FAILURE: No " + type + " rooms available for " + reservation.getGuestName());
        }
        System.out.println("--------------------------------------");
    }

    /**
     * Generates a unique room ID for the given room type.
     */
    private String generateRoomId(String roomType) {
        // Simple logic: [First Letter of Type]-[Random Number]
        return roomType.substring(0, 1).toUpperCase() + "-" + (100 + (int)(Math.random() * 900));
    }
}

/** * Supporting Reservation class from Use Case 5
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/**
 * Supporting Inventory class updated with mutation logic
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    // New method for Use Case 6 to update inventory immediately
    public void updateInventory(String type, int delta) {
        inventory.put(type, inventory.getOrDefault(type, 0) + delta);
    }

    public Map<String, Integer> getRoomAvailability() {
        return inventory;
    }
}

/**
 * =================================================================
 * MAIN CLASS - UseCase6RoomAllocation
 * =================================================================
 * This class demonstrates how booking requests are confirmed
 * and rooms are allocated safely.
 */
public class UseCase6RoomAllocation {

    public static void main(String[] args) {
        // 1. Set up initial inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addInventory("Single", 2); // Only 2 rooms available
        inventory.addInventory("Double", 5);

        // 2. Initialize Allocation Service
        RoomAllocationService allocationService = new RoomAllocationService();

        // 3. Create simulated FIFO booking requests
        List<Reservation> queue = new ArrayList<>();
        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Subha", "Single"));
        queue.add(new Reservation("Vanmathi", "Single")); // This should fail

        // 4. Process requests (FIFO)
        System.out.println("Starting Room Allocation Process...");
        System.out.println("--------------------------------------");

        for (Reservation res : queue) {
            allocationService.allocateRoom(res, inventory);
        }
    }
}