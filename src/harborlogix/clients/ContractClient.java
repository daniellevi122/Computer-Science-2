package harborlogix.clients;

/**
 * SKELETON. A client with a negotiated discount between 0 and 40 percent.
 * Reject anything outside that range with IllegalArgumentException.
 */
public class ContractClient extends Client {

    // TODO: private final field for the negotiated discount
    private final double discount;

    public ContractClient(String clientId, String name, double discount) {
        super(clientId, name); // קריאה לבנאי של האב שבודק null
        // TODO: validate 0..40 and assign
        if (discount < 0 || discount > 40) {
            throw new IllegalArgumentException("Discount must be between 0 and 40");
        }
        this.discount = discount;
    }

    // TODO: override discountPercent(), clientTier(), toString()
    //       toString() must reuse super.toString()

    @Override
    public double discountPercent() {
        return this.discount;
    }

    @Override
    public String clientTier() {
        return "Contract";
    }

    @Override
    public String toString() {
        return super.toString() + " discount=" + this.discount;
    }
}