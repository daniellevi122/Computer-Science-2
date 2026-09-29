package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class OversizedCargo extends CargoUnit {
    private final double length;
    private final boolean needsHeavyCrane;

    public OversizedCargo(String unitId, Client owner, double weightKg,
                          int daysStored, double length, boolean needsHeavyCrane) {
        super(unitId, owner, weightKg, daysStored);
        if (length <= 1.0) {
            throw new IllegalArgumentException("Length must be strictly greater than 1.0 meter");
        }
        this.length = length;
        this.needsHeavyCrane = needsHeavyCrane;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.OVERSIZE_DAILY_FLAT + (this.length * 5.0);
    }

    @Override
    public String handlingCategory() {
        return "Oversized";
    }

    @Override
    public String safetyBriefing() {
        return "Oversized Load" + (needsHeavyCrane ? " - Heavy Crane Required" : "");
    }

    @Override
    public String toString() {
        return super.toString() + " length=" + this.length + " crane=" + this.needsHeavyCrane;
    }
}