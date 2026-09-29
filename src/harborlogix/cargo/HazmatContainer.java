package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

/**
 * SKELETON.  Also a child of StandardContainer - a sibling of the reefer.
 *
 * Extra state : hazardClass (1-9 only), requiresEscort (boolean)
 * Daily fee   : the parent's fee MULTIPLIED by HAZMAT_MULTIPLIER
 * Category    : "Hazmat"
 * Briefing    : must state the hazard class number
 */
public class HazmatContainer extends StandardContainer {

    // TODO: private final fields
    private final int hazardClass;
    private final boolean requiresEscort;

    public HazmatContainer(String unitId, Client owner, double weightKg,
                           int daysStored, double volumeM3,
                           int hazardClass, boolean requiresEscort) {
        super(unitId, owner, weightKg, daysStored, volumeM3);
        // TODO: validate and assign
        if (hazardClass < 1 || hazardClass > 9) {
            throw new IllegalArgumentException("Hazard class must be between 1 and 9");
        }

        this.hazardClass = hazardClass;
        this.requiresEscort = requiresEscort;
    }

    public int getHazardClass() {
        return this.hazardClass;
    }

    public boolean isRequiresEscort() {
        return this.requiresEscort;
    }

    // TODO: override dailyStorageFee(), handlingCategory(), safetyBriefing(), toString()

    @Override
    public double dailyStorageFee() {
        return super.dailyStorageFee() * TariffPolicy.HAZMAT_MULTIPLIER;
    }

    @Override
    public String handlingCategory() {
        return "Hazmat";
    }

    @Override
    public String safetyBriefing() {
        return "Danger! Hazard Class: " + hazardClass + (requiresEscort ? " (Escort Required)" : "");
    }

    @Override
    public String toString() {
        return super.toString() + " hazard=" + this.hazardClass + " escort=" + this.requiresEscort;
    }
}