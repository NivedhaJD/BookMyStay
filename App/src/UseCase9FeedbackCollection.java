import java.util.*;

/**
 * =================================================================
 * CLASS - FeedbackService
 * =================================================================
 * Use Case 9: Feedback Collection
 * * Description:
 * This class handles guest feedback after their stay.
 * It allows guests to provide a rating and comments,
 * helping the hotel improve service quality.
 * * @version 9.0
 */
class FeedbackService {

    /** Stores feedback records indexed by Guest ID. */
    private Map<String, Feedback> feedbackStore;

    public FeedbackService() {
        this.feedbackStore = new HashMap<>();
    }

    /**
     * Submits feedback for a specific guest.
     * * @param guestId unique ID of the guest
     * @param rating  numerical rating (e.g., 1 to 5)
     * @param comment guest's descriptive feedback
     */
    public void submitFeedback(String guestId, int rating, String comment) {
        Feedback entry = new Feedback(guestId, rating, comment);
        feedbackStore.put(guestId, entry);

        System.out.println("Feedback submitted successfully for Guest: " + guestId);
        System.out.println("Rating: " + "*".repeat(rating) + " (" + rating + "/5)");
        System.out.println("Comment: " + comment);
        System.out.println("--------------------------------------");
    }
}

/**
 * Data model for an individual Feedback entry.
 */
class Feedback {
    private String guestId;
    private int rating;
    private String comment;

    public Feedback(String guestId, int rating, String comment) {
        this.guestId = guestId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters
    public String getGuestId() { return guestId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}

/**
 * Supporting Guest class.
 */
class Guest {
    private String guestId;
    private String name;

    public Guest(String guestId, String name) {
        this.guestId = guestId;
        this.name = name;
    }

    public String getGuestId() { return guestId; }
    public String getName() { return name; }
}

/**
 * =================================================================
 * MAIN CLASS - UseCase9FeedbackCollection
 * =================================================================
 * Description:
 * This class demonstrates the feedback collection process
 * once a guest has completed their stay and payment.
 */
public class UseCase9FeedbackCollection {

    public static void main(String[] args) {
        System.out.println("--- Guest Feedback Collection ---");
        System.out.println();

        // 1. Initialize Feedback Service
        FeedbackService feedbackService = new FeedbackService();

        // 2. Setup mock Guest data
        Guest guest1 = new Guest("G-401", "Abhi");
        Guest guest2 = new Guest("G-402", "Subha");

        // 3. Collect and submit feedback
        feedbackService.submitFeedback(
                guest1.getGuestId(),
                5,
                "Excellent stay! The room service was very prompt."
        );

        feedbackService.submitFeedback(
                guest2.getGuestId(),
                4,
                "Great experience, though the check-in queue was a bit long."
        );
    }
}