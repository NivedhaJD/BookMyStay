import java.util.LinkedList;
import java.util.Queue;

/**
 * =====================================================================
 * CLASS - ConcurrentBookingProcessor
 * =====================================================================
 * Use Case 11: Concurrent Booking Simulation
 * * Description:
 * This class represents a booking processor that can be executed by
 * multiple threads.
 * * It demonstrates how shared resources such as booking queues and
 * inventory must be accessed in a thread-safe manner.
 * * @version 11.0
 */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    /**
     * Creates a new booking processor.
     */
    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    /**
     * Executes booking processing logic.
     * This method is called when the thread starts.
     */
    @Override
    public void run() {
        while (true) {
            Reservation reservation = null;

            /*
             * Synchronize on the booking queue to ensure
             * that only one thread can retrieve a request
             * at a time.
             */
            synchronized (bookingQueue) {
                reservation = bookingQueue.getNextRequest();
            }

            // If no more reservations, exit simulation (optional logic)
            if (reservation == null) {
                break;
            }

            /*
             * Allocation also mutates shared inventory.
             * Synchronization ensures atomic allocation.
             */
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }

            // Small sleep to simulate processing time
            try { Thread.sleep(100); } catch (InterruptedException e) { break; }
        }
    }
}

/**
 * =====================================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * =====================================================================
 */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {
        // Initialize shared resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Pre-fill queue with dummy data for simulation
        bookingQueue.addRequest(new Reservation("User A"));
        bookingQueue.addRequest(new Reservation("User B"));

        // Create booking processor tasks
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("Processing complete.");
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }
    }
}

// Supporting placeholder classes to make the code compile
class Reservation {
    String name;
    Reservation(String name) { this.name = name; }
}

class BookingRequestQueue {
    private Queue<Reservation> requests = new LinkedList<>();
    public void addRequest(Reservation r) { requests.add(r); }
    public Reservation getNextRequest() { return requests.poll(); }
}

class RoomInventory { /* Inventory management logic */ }

class RoomAllocationService {
    public void allocateRoom(Reservation r, RoomInventory i) {
        System.out.println("Allocating room for: " + r.name + " via " + Thread.currentThread().getName());
    }
}