
package behavioural.strategy;

interface DiscountStrategy {
    long apply(long amount);
}

// Concrete Strategy: No discount.
class NoDiscount implements DiscountStrategy {

    @Override
    public long apply(long amount) {
        return amount;
    }
}

// Concrete Strategy: Percentage-based discount.
class PercentageDiscount implements DiscountStrategy {

    private final int percentage;

    PercentageDiscount(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(
                    "Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public long apply(long amount) {
        return amount - (amount * percentage / 100);
    }
}

// Concrete Strategy: Fixed amount discount.
class FlatDiscount implements DiscountStrategy {

    private final long off;

    FlatDiscount(long off) {
        if (off < 0) {
            throw new IllegalArgumentException(
                    "Discount cannot be negative");
        }
        this.off = off;
    }

    @Override
    public long apply(long amount) {
        return Math.max(0, amount - off);
    }
}

// Context: Delegates discount calculation to the selected strategy.
class Checkout {

    private final DiscountStrategy discountStrategy;

    Checkout(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    long total(long amount) {
        return discountStrategy.apply(amount);
    }
}

public class App {

    public static void main(String[] args) {

        long amount = 10000;

        Checkout regularCheckout =
                new Checkout(new NoDiscount());

        Checkout percentageCheckout =
                new Checkout(new PercentageDiscount(10));

        Checkout flatCheckout =
                new Checkout(new FlatDiscount(1500));

        System.out.println("Original Amount: " + amount);
        System.out.println("No Discount: " + regularCheckout.total(amount));
        System.out.println("10% Discount: " + percentageCheckout.total(amount));
        System.out.println("Flat Discount: " + flatCheckout.total(amount));
    }
}
