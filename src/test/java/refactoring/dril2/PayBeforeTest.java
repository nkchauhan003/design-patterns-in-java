
package refactoring.dril2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayBeforeTest {

    private final PayBefore pay = new PayBefore();

    @Test
    void shouldCalculateWeekdayPayment() {
        assertEquals(0, pay.weekday(0));
        assertEquals(2000, pay.weekday(1));
        assertEquals(8000, pay.weekday(4));
        assertEquals(16000, pay.weekday(8));
        assertEquals(19000, pay.weekday(9));
        assertEquals(22000, pay.weekday(10));
        assertEquals(25000, pay.weekday(11));
    }

    @Test
    void shouldCalculateSaturdayPayment() {
        assertEquals(0, pay.saturday(0));
        assertEquals(2500, pay.saturday(1));
        assertEquals(10000, pay.saturday(4));
        assertEquals(20000, pay.saturday(8));
        assertEquals(23750, pay.saturday(9));
        assertEquals(27500, pay.saturday(10));
        assertEquals(31250, pay.saturday(11));
    }

    @Test
    void shouldCalculateSundayPayment() {
        assertEquals(0, pay.sunday(0));
        assertEquals(4000, pay.sunday(1));
        assertEquals(16000, pay.sunday(4));
        assertEquals(32000, pay.sunday(8));
        assertEquals(38000, pay.sunday(9));
        assertEquals(44000, pay.sunday(10));
        assertEquals(50000, pay.sunday(11));
    }

    @Test
    void shouldApplyOvertimeRateAfterEightHours() {
        // Weekday: 2000 per hour, overtime: 3000
        assertEquals(16000, pay.weekday(8));
        assertEquals(19000, pay.weekday(9));

        // Saturday: 2500 per hour, overtime: 3750
        assertEquals(20000, pay.saturday(8));
        assertEquals(23750, pay.saturday(9));

        // Sunday: 4000 per hour, overtime: 6000
        assertEquals(32000, pay.sunday(8));
        assertEquals(38000, pay.sunday(9));
    }
}
