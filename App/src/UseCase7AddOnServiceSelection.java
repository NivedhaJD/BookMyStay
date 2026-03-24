import java.util.*;

/**
 * =================================================================
 * CLASS - Service (AddOnService)
 * =================================================================
 * Use Case 7: Add-On Service Selection
 * Description:
 * This class represents an optional service that can be added
 * to a confirmed reservation.
 */
class Service {

    private String serviceName;
    private double cost;

    /**
     * Creates a new add-on service.
     * @param serviceName name of the service
     * @param cost cost of the service
     */
    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

/**
 * =================================================================
 * CLASS - AddOnServiceManager
 * =================================================================
 * Description:
 * This class manages optional services associated with confirmed
 * reservations. It supports attaching multiple services to one booking.
 */
class AddOnServiceManager {

    /** Maps reservation ID to selected services. */
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a reservation.
     * @param reservationId confirmed reservation ID
     * @param service add-on service
     */
    public void addService(String reservationId, Service service) {
        // Retrieve existing list or create a new one, then add the service
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added '" + service.getServiceName() +
                "' to Reservation: " + reservationId);
    }

    /**
     * Calculates total add-on cost for a reservation.
     * @param reservationId reservation ID
     * @return total service cost
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;

        double total = 0.0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }
}

/**
 * =================================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * =================================================================
 * Description:
 * This class demonstrates how optional services can be attached
 * to a confirmed booking.
 */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {
        System.out.println("--- Add-On Service Selection ---");
        System.out.println("--------------------------------");

        // 1. Initialize the Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // 2. Create available Add-On Services
        Service breakfast = new Service("Breakfast", 15.0);
        Service spa = new Service("Spa", 50.0);
        Service pickup = new Service("Airport Pickup", 30.0);

        // 3. Define confirmed reservation IDs
        String resId1 = "RES-101";
        String resId2 = "RES-202";

        // 4. Attach multiple services to reservations
        serviceManager.addService(resId1, breakfast);
        serviceManager.addService(resId1, spa);

        serviceManager.addService(resId2, pickup);

        // 5. Calculate and display totals
        System.out.println("\n--- Final Billing Summary (Add-Ons) ---");
        System.out.println("Total Add-On Cost for " + resId1 + ": $" +
                serviceManager.calculateTotalServiceCost(resId1));

        System.out.println("Total Add-On Cost for " + resId2 + ": $" +
                serviceManager.calculateTotalServiceCost(resId2));
    }
}