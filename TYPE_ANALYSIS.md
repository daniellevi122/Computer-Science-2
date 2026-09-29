# ניתוח טיפוסים (Type Analysis)

| # | קובץ ושורה | הקוד המנותח | טיפוס סטטי (Static) | טיפוס דינמי (Dynamic) | מי מכריע (קומפיילר/אובייקט) |
|---|---|---|---|---|---|
| 1 | `Yard.java` (totalDailyRevenue) | `total += u.dailyStorageFee();` | `CargoUnit` | `StandardContainer`, `RefrigeratedContainer` וכו' | **האובייקט (שיגור דינמי)**: הקומפיילר מאשר כי הפעולה קיימת באב, אך בזמן ריצה מופעל המימוש הספציפי של הטיפוס הדינמי. |
| 2 | `Yard.java` (printManifest) | `System.out.println(u.toString());` | `CargoUnit` | פולימורפי (משתנה לפי המופע) | **האובייקט (שיגור דינמי)**: מופעלת הדריסה של `toString` השייכת לתת-המחלקה הספציפית בזיכרון. |
| 3 | `RefrigeratedContainer.java` | `super.dailyStorageFee()` | `RefrigeratedContainer` | `RefrigeratedContainer` | **הקומפיילר (Static Dispatch)**: השימוש במילה `super` מאלץ עקיפה של הפולימורפיזם וקריאה מפורשת למימוש של מחלקת האב. |
| 4 | `TerminalApp.java` (סבב ניקוז) | `if (unit instanceof LiquidTank tank)` | `CargoUnit` (`unit`) | `LiquidTank` (אם התנאי מתקיים) | **האובייקט**: הבדיקה מתבצעת בזמן ריצה ומוודאת את סוגו האמיתי של האובייקט בזיכרון לפני ביצוע ההמרה למטה (Downcast). |
| 5 | `TerminalApp.java` (סבב ניקוז) | `tank.transferOut(3000);` | `LiquidTank` (`tank`) | `LiquidTank` | **הקומפיילר**: לאחר ה-Pattern Matching, הקומפיילר מתייחס למשתנה `tank` כ-LiquidTank ומאשר את הפעלת הפעולה הייחודית לו. |
| 6 | `Yard.java` (invoiceFor) | `u.totalStorageCharge()` | `CargoUnit` | פולימורפי | **קומפיילר ואובייקט**: האובייקט מפעיל את הפעולה, אך מכיוון שהיא `final` באב, המימוש קבוע מראש ואינו נדרס על ידי תת-המחלקות. |
| 7 | `TerminalApp.java` (קליטה) | `yard.receive(new StandardContainer(...))` | `CargoUnit` (בפרמטר של Yard) | `StandardContainer` | **הקומפיילר (Upcasting)**: הקומפיילר מבצע המרה אוטומטית ובטוחה למעלה, שכן כל מכולה היא בהגדרתה יחידת מטען. |
| 8 | תיאורטי (פסילת קומפיילר) | `unit.transferOut(3000);` כאשר `unit` הוא מטיפוס CargoUnit | `CargoUnit` | `LiquidTank` | **הקומפיילר יפסול**: הקומפיילר אוסר את הקריאה מכיוון שהטיפוס הסטטי `CargoUnit` אינו מכיר את הפעולה `transferOut`, אפילו אם בזמן ריצה האובייקט הוא בפועל מיכל. לשם כך נדרשת המרה. |
### שאלות משלימות
1. **עקרון פתוח/סגור ושיגור דינמי:** `Yard` מכירה רק את הטיפוס הסטטי `CargoUnit`. בזמן ריצה, השיגור הדינמי מפעיל את המתודות הייחודיות של כל תת-מחלקה. זה איפשר לנו להוסיף את `OversizedCargo` בלי לשנות את הקוד של `Yard`.
2. **המרת Downcasting:** הפעולה `transferOut` קיימת רק ב-`LiquidTank`. כדי להפעיל אותה חובה לבצע המרה כלפי מטה. ההמרה בטוחה כי השתמשנו ב-`instanceof` (Pattern Matching) שמוודא קודם את סוג האובייקט.