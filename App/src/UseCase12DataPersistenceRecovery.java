import java.io.*;
import java.util.*;

/**
 * =====================================================================
 * CLASS - FilePersistenceService
 * =====================================================================
 * Use Case 12: Data Persistence & System Recovery
 * * Description:
 * This class is responsible for persisting critical system state
 * to a plain text file.
 * * @version 12.0
 */
class FilePersistenceService {

    /**
     * Saves room inventory state to a file.
     * Format: roomType=availableCount
     */
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Map<String, Integer> data = inventory.getRooms();
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            System.out.println("Inventory saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory state from a file.
     */
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Persistence file not found. Starting with empty inventory.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String type = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    inventory.updateRoom(type, count);
                }
            }
            System.out.println("Inventory restored from " + filePath);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }
}

/**
 * =====================================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * =====================================================================
 * Description:
 * This class demonstrates how system state can be restored
 * after an application restart.
 */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {
        String persistFile = "inventory_state.txt";
        FilePersistenceService persistenceService = new FilePersistenceService();
        RoomInventory inventory = new RoomInventory();

        // 1. Load data from file (Recovery)
        persistenceService.loadInventory(inventory, persistFile);

        // 2. Simulate some changes if it was empty
        if (inventory.getRooms().isEmpty()) {
            System.out.println("Initializing fresh data...");
            inventory.updateRoom("Deluxe", 10);
            inventory.updateRoom("Suite", 5);
        }

        // 3. Display current state
        System.out.println("Current Inventory: " + inventory.getRooms());

        // 4. Save state for next restart (Persistence)
        persistenceService.saveInventory(inventory, persistFile);
    }
}

/**
 * Reusing/Defining the RoomInventory for this context
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public void updateRoom(String type, int count) {
        rooms.put(type, count);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }
}