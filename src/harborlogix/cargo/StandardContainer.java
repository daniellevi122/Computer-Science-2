package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

/**
 * SKELETON.  StandardContainer IS-A CargoUnit.
 *
 * Extra state : volumeM3 (must be positive)
 * Daily fee   : BASE_STORAGE_RATE * volumeM3
 * Category    : "Standard"
 * Briefing    : any sensible one-line message
 */
public class StandardContainer extends CargoUnit {

    // TODO: private final double volumeM3;
    private final double volumeM3;

    public StandardContainer(String unitId, Client owner, double weightKg,
                             int daysStored, double volumeM3) {
        super(unitId, owner, weightKg, daysStored);
        // TODO: validate volumeM3 > 0, then assign
        if (volumeM3 <= 0) {
            throw new IllegalArgumentException("Volume must be positive");
        }
        this.volumeM3 = volumeM3;
    }

    public double getVolumeM3() {
        return this.volumeM3;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.BASE_STORAGE_RATE * this.volumeM3;
    }

    @Override
    public String handlingCategory() {
        return "Standard";
    }

    @Override
    public String safetyBriefing() {
        return "Standard container handling";
    }

    // TODO: override toString(), reusing super.toString()
    @Override
    public String toString() {
        return super.toString() + " volume=" + this.volumeM3;
    }
}