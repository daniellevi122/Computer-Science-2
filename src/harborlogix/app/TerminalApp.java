package harborlogix.app;

import harborlogix.cargo.*;
import harborlogix.clients.*;
import harborlogix.ops.*;

/**
 * SKELETON - your demonstration program.
 *
 * It must print, in this order, with clear section headings:
 *
 *   1. A yard manifest holding AT LEAST one of every cargo type.
 *   2. Invoices for all three client tiers, showing the discount applied.
 *   3. The drainage round - the one place a downcast is permitted.
 *      Use pattern matching: `if (unit instanceof LiquidTank tank)`.
 *   4. Part D: receive an OversizedCargo into the SAME yard object and
 *      reprint the manifest, proving Yard needed no changes.
 *
 * Build the yard with capacity TariffPolicy.YARD_CAPACITY.
 */
public class TerminalApp {

    public static void main(String[] args) {
        System.out.println("HarborLogix terminal - student " + TariffPolicy.STUDENT_ID);
        // TODO: sections 1-4

        // יצירת שלושת סוגי הלקוחות
        Client regularClient = new Client("C1", "Walk-in Moshe");
        ContractClient contractClient = new ContractClient("C2", "Contract David", 15.0);
        GovernmentClient govClient = new GovernmentClient("C3", "IDF Logistics", "DEF-100");

        // 2. יצירת המסוף (בשימוש בקבוע מ-TariffPolicy כפי שנדרש בהערות השלד)
        Yard yard = new Yard("Ashdod Terminal", TariffPolicy.YARD_CAPACITY);

        // יצירת לפחות יחידה אחת מכל סוג מטען (חלק ג')
        yard.receive(new StandardContainer("U1", regularClient, 2000, 5, 30.0));
        yard.receive(new RefrigeratedContainer("U2", contractClient, 2500, 3, 40.0, 4.0, 10.0));
        yard.receive(new HazmatContainer("U3", govClient, 3000, 2, 25.0, 3, true));
        yard.receive(new LiquidTank("U4", regularClient, 4000, 4, 5000, 80));

        // 1. הדפסת רשימת המטען (Manifest)
        System.out.println("\n=== 1. INITIAL MANIFEST ===");
        yard.printManifest();

        // 2. חשבוניות עבור שלושת סוגי הלקוחות
        System.out.println("\n=== 2. INVOICES ===");
        System.out.println("Regular Client: " + yard.invoiceFor(regularClient));
        System.out.println("Contract Client: " + yard.invoiceFor(contractClient));
        System.out.println("Government Client: " + yard.invoiceFor(govClient));

        // 3. סבב ניקוז עם המרה למטה (חלק ג') - Downcast permitted here
        System.out.println("\n=== 3. DRAINAGE ROUND (Target: 3000L) ===");
        for (CargoUnit unit : yard.getUnits()) {
            if (unit instanceof LiquidTank tank) {
                double removed = tank.transferOut(3000);
                System.out.println("Drained " + removed + "L from tank " + tank.getUnitId());
            }
        }

        System.out.println("\n=== MANIFEST AFTER DRAINAGE ===");
        yard.printManifest();

        // 4. קליטת מטען חריג והדפסה מחדש (חלק ד')
        System.out.println("\n=== 4. PART D: OVERSIZED CARGO ADDED ===");
        yard.receive(new OversizedCargo("U5", contractClient, 12000, 1, 14.5, true));
        yard.printManifest();
    }
}