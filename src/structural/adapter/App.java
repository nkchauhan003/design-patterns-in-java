package structural.adapter;

/**
 * Demonstrates the Adapter Design Pattern using composition.
 */
public class App {

    public static void main(String[] args) {

        // Create the existing legacy gateway.
        LegacyGateway legacyGateway = new OldPaymentGateway();

        // Adapt the legacy gateway to the modern payment interface.
        PaymentProcessor processor =
                new LegacyGatewayAdapter(legacyGateway);

        // Make payment using paisa.
        boolean success = processor.pay(10550);

        System.out.println("Payment successful: " + success);
    }
}

/**
 * Target interface expected by the modern payment application.
 * Amount is expressed in paisa.
 */
interface PaymentProcessor {

    boolean pay(long amountInPaisa);
}

/**
 * Existing legacy payment gateway interface.
 * Amount is expressed in rupees.
 */
interface LegacyGateway {

    // Returns 0 on successful payment.
    int charge(double amountInRupees);
}

/**
 * Concrete implementation of the legacy payment gateway.
 */
class OldPaymentGateway implements LegacyGateway {

    @Override
    public int charge(double amountInRupees) {
        System.out.println("Charged Rs. " + amountInRupees);
        return 0; // Success
    }
}

/**
 * Adapter that makes the legacy gateway compatible
 * with the modern PaymentProcessor interface.
 */
class LegacyGatewayAdapter implements PaymentProcessor {

    private final LegacyGateway legacyGateway;

    public LegacyGatewayAdapter(LegacyGateway legacyGateway) {
        this.legacyGateway = legacyGateway;
    }

    @Override
    public boolean pay(long amountInPaisa) {

        // Convert paisa to rupees.
        double amountInRupees = amountInPaisa / 100.0;

        // Convert the legacy return code into a boolean result.
        return legacyGateway.charge(amountInRupees) == 0;
    }
}