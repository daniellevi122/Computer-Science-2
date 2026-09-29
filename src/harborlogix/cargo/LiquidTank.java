package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

/**
 * SKELETON.  A DIRECT child of CargoUnit - a separate branch from the
 * container family. Do not make it extend StandardContainer.
 *
 * Extra state : capacityLitres (positive), fillPercent (0-100, mutable)
 * currentLitres() : capacityLitres * fillPercent / 100
 * Daily fee   : LIQUID_RATE * currentLitres()   (actual content, not capacity)
 * Category    : "Tank"
 *
 * transferOut() exists ONLY here. It is the method you will use in Part C
 * to demonstrate the one downcast this assignment permits.
 */
public class LiquidTank extends CargoUnit {

    // TODO: private final double capacityLitres;  private double fillPercent;
    private final double capacityLitres;
    private double fillPercent;

    public LiquidTank(String unitId, Client owner, double weightKg,
                      int daysStored, double capacityLitres, double fillPercent) {
        super(unitId, owner, weightKg, daysStored);
        // TODO: validate and assign
        if (capacityLitres <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (fillPercent < 0 || fillPercent > 100) {
            throw new IllegalArgumentException("Fill percent must be between 0 and 100");
        }
        this.capacityLitres = capacityLitres;
        this.fillPercent = fillPercent;
    }

    public double getCapacityLitres() {
        return this.capacityLitres;
    }

    public double getFillPercent() {
        return this.fillPercent;
    }

    public double currentLitres() {
        return this.capacityLitres * (this.fillPercent / 100.0);
    }

    /**
     * Pumps out up to `litres` and returns how much was actually moved
     * (never more than is present). Updates fillPercent accordingly.
     * Reject a non-positive request with IllegalArgumentException.
     */
    public double transferOut(double litres) {
        if (litres <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        double current = currentLitres();
        double actualTransfer = Math.min(litres, current); // מחזיר כמה שהועבר בפועל, לא יותר ממה שיש

        // עדכון אחוז המילוי החדש
        double newLitres = current - actualTransfer;
        this.fillPercent = (newLitres / this.capacityLitres) * 100.0;

        return actualTransfer;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.LIQUID_RATE * currentLitres();
    }

    @Override
    public String handlingCategory() {
        return "Tank";
    }

    @Override
    public String safetyBriefing() {
        return "Liquid Tank handling";
    }

    // TODO: override toString(), reusing super.toString()
    @Override
    public String toString() {
        return super.toString() + " capacity=" + this.capacityLitres + " fill=" + this.fillPercent;
    }
}