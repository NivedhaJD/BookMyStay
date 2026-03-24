import java.util.*;

/**
 * =================================================================
 * CLASS - DashboardService
 * =================================================================
 * Use Case 10: Management Dashboard & Analytics
 * * Description:
 * This class provides a centralized view of hotel operations.
 * It calculates key metrics like:
 * 1. Total Occupancy Rate
 * 2. Revenue breakdown (Rooms vs Add-ons)
 * 3. Performance summary
 * * @version 10.0
 */
class DashboardService {

    /**
     * Generates a management report based on current system data.
     * * @param inventory   current room inventory
     * @param totalRevenue sum of all processed payments
     * @param totalBookings total number of rooms occupied
     */
    public void generateAnalyticsReport(RoomInventory inventory, double totalRevenue, int totalBookings) {

        System.out.println("========== MANAGEMENT DASHBOARD ==========");
        System.out.println("Report Generated on: " + new Date());
        System.out.println("------------------------------------------");

        // Calculate Occupancy (Assuming a total capacity of 50 for this demo)
        int totalCapacity = 50;
        double occupancyRate = ((double) totalBookings / totalCapacity) * 100;

        System.out.println("Total Revenue    : $" + String.format("%.2f", totalRevenue));
        System.out.println("Active Bookings  : " + totalBookings);
        System.out.println("Occupancy Rate   : " + String.format("%.1f", occupancyRate) + "%");

        System.out.println("\n--- Current Inventory Status ---");
        inventory.getRoomAvailability().forEach((type, count) -> {
            System.out.println(type + " Rooms Available: " + count);
        });

        System.out.println("==========================================");
    }
}

/**
 * Supporting RoomInventory from previous Use Cases
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
 * MAIN CLASS - UseCase10ManagementDashboard
 * =================================================================
 * Description:
 * This class demonstrates the analytics capabilities of the system,
 * providing insights for hotel managers to make data-driven decisions.
 */
public class UseCase10ManagementDashboard {

    public static void main(String[] args) {
        // 1. Setup Mock Inventory Data
        RoomInventory inventory = new RoomInventory();
        inventory.addInventory("Single", 12);
        inventory.addInventory("Double", 8);
        inventory.addInventory("Suite", 2);

        // 2. Setup Mock Financial Data
        // (In a real system, these would be aggregated from the BillingService)
        double revenueFromRooms = 4500.00;
        double revenueFromAddOns = 1250.50;
        double totalRevenue = revenueFromRooms + revenueFromAddOns;
        int activeBookings = 28;

        // 3. Initialize Dashboard Service
        DashboardService dashboard = new DashboardService();

        // 4. Generate the Analytics Report
        dashboard.generateAnalyticsReport(inventory, totalRevenue, activeBookings);
    }
}