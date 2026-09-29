package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

/**
 * SKELETON.  Level 3 of the hierarchy.
 *
 * Extra state : targetTempC (must be 8.0 or below), powerDrawKw (positive)
 * Daily fee   : the parent's fee PLUS (POWER_RATE * powerDrawKw)
 *               You must EXTEND the parent's fee, not recompute it.
 * Category    : "Reefer"
 */
public class RefrigeratedContainer extends StandardContainer {

    // TODO: private final fields
    private final double targetTempC;
    private final double powerDrawKw;

    public RefrigeratedContainer(String unitId, Client owner, double weightKg,
                                 int daysStored, double volumeM3,
                                 double targetTempC, double powerDrawKw) {
        super(unitId, owner, weightKg, daysStored, volumeM3);
        // TODO: validate and assign
        if (targetTempC > 8.0) {
            throw new IllegalArgumentException("Target temperature must be 8.0 or below");
        }
        if (powerDrawKw <= 0) {
            throw new IllegalArgumentException("Power draw must be positive");
        }

        this.targetTempC = targetTempC;
        this.powerDrawKw = powerDrawKw;
    }

    public double getTargetTempC() {
        return this.targetTempC;
    }

    public double getPowerDrawKw() {
        return this.powerDrawKw;
    }

    // TODO: override dailyStorageFee() using super.dailyStorageFee()
    // TODO: override handlingCategory(), safetyBriefing(), toString()

    @Override
    public double dailyStorageFee() {
        return super.dailyStorageFee() + (TariffPolicy.POWER_RATE * this.powerDrawKw);
    }

    @Override
    public String handlingCategory() {
        return "Reefer";
    }

    @Override
    public String safetyBriefing() {
        return "Maintain temperature at " + targetTempC + "C";
    }

    @Override
    public String toString() {
        return super.toString() + " temp=" + this.targetTempC + " power=" + this.powerDrawKw;
    }
}