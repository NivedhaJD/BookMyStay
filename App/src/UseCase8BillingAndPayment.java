import java.util.*;

/**
 * =================================================================
 * CLASS - BillingService
 * =================================================================
 * Use Case 8: Billing & Payment Processing
 * * Description:
 * This class calculates the final amount due for a guest.
 * It consolidates:
 * 1. Base room price (from Room Allocation)
 * 2. Total add-on service costs (from Add-On Service Selection)
 * * @version 8.0
 */
class BillingService {

    /**
     * Generates and displays the final bill for a guest.
     * * @param reservationId   ID of the confirmed booking
     * @param roomPrice       base price of the allocated room
     * @param serviceManager  manager containing add-on selections
     */
    public void generateInvoice(String reservationId, double roomPrice, AddOnServiceManager serviceManager) {

        // Retrieve total from the Add-On Service Selection use case
        double addOnTotal = serviceManager.calculateTotalServiceCost(reservationId);
        double totalAmount = roomPrice + addOnTotal;

        System.out.println("========== FINAL INVOICE ==========");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Base Room Price: $" + roomPrice);
        System.out.println("Add-on Services: $" + addOnTotal);
        System.out.println("-----------------------------------");
        System.out.println("TOTAL AMOUNT DUE: $" + totalAmount);
        System.out.println("===================================");

        processPayment(totalAmount);
    }

    /**
     * Simulates payment processing.
     */
    private void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + "...");
        System.out.println("Payment Successful. Thank you!");
        System.out.println();
    }
}

/**
 * Supporting Add-On Manager from Use Case 7
 */
class AddOnServiceManager {
    private Map<String, List<Double>> serviceCosts = new HashMap<>();

    public void addServiceCharge(String resId, double cost) {
        serviceCosts.computeIfAbsent(resId, k -> new ArrayList<>()).add(cost);
    }

    public double calculateTotalServiceCost(String resId) {
        List<Double> costs = serviceCosts.get(resId);
        if (costs == null) return 0.0;
        return costs.stream().mapToDouble(Double::doubleValue).sum();
    }
}

/**
 * =================================================================
 * MAIN CLASS - UseCase8BillingAndPayment
 * =================================================================
 * Description:
 * This class demonstrates the final consolidation of room costs
 * and optional service charges into a single payment.
 */
public class UseCase8BillingAndPayment {

    public static void main(String[] args) {
        // 1. Setup Mock Data from previous use cases
        String reservationId = "RES-999";
        double allocatedRoomPrice = 180.0; // From Room Search/Allocation

        // 2. Setup Add-On Services (e.g., Breakfast and Spa)
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        serviceManager.addServiceCharge(reservationId, 25.0); // Breakfast
        serviceManager.addServiceCharge(reservationId, 50.0); // Spa

        // 3. Initialize Billing Service and Generate Invoice
        BillingService billingService = new BillingService();
        billingService.generateInvoice(reservationId, allocatedRoomPrice, serviceManager);
    }
}