package refactoring.dril2;

class PayBefore {
    long weekday(int hours) {
        if (hours <= 8) return hours * 2000L;
        return 8 * 2000L + (hours - 8) * 3000L;
    }
    long saturday(int hours) {
        if (hours <= 8) return hours * 2500L;
        return 8 * 2500L + (hours - 8) * 3750L;
    }
    long sunday(int hours) {
        if (hours <= 8) return hours * 4000L;
        return 8 * 4000L + (hours - 8) * 6000L;
    }
}