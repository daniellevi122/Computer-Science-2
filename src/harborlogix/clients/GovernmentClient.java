package harborlogix.clients;

/**
 * SKELETON. A state client. The discount is fixed by statute at 25 percent,
 * so it must NOT be a constructor parameter - model it as a constant.
 * Government cargo always gets priority handling.
 */
public class GovernmentClient extends Client {

    // TODO: public static final double STATUTORY_DISCOUNT = 25.0;
    // ההנחה קבועה ולא מתקבלת כפרמטר לפי המפרט
    public static final double GOVERNMENT_DISCOUNT = 25.0;

    // TODO: private final String agencyCode;
    private final String agencyCode;

    public GovernmentClient(String clientId, String name, String agencyCode) {
        super(clientId, name);
        // TODO: validate agencyCode and assign
        if (agencyCode == null || agencyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Agency code cannot be null or empty");
        }
        this.agencyCode = agencyCode;
    }

    public String getAgencyCode() {
        return this.agencyCode;
    }

    // TODO: override discountPercent(), clientTier(), priorityHandling(), toString()

    @Override
    public double discountPercent() {
        return GOVERNMENT_DISCOUNT;
    }

    @Override
    public String clientTier() {
        return "Government";
    }

    @Override
    public boolean priorityHandling() {
        return true; // מקבל טיפול מועדף
    }

    @Override
    public String toString() {
        return super.toString() + " agency=" + this.agencyCode;
    }
}