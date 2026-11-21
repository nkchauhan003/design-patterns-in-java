package structural.facade;

class InventoryService {
    void reserve(String sku, int quantity) {
        System.out.println("Reserved " + quantity + " units of " + sku);
    }

    void release(String sku, int quantity) {
        System.out.println("Released " + quantity + " units of " + sku);
    }
}

class PaymentService {

    String charge(String card, long amount) {
        System.out.println("Processing payment of " + amount);

        // Simulates a successful payment.
        return "PAY-" + System.currentTimeMillis();
    }
}

class ShippingService {

    String schedule(String orderId, String address) {
        System.out.println("Shipping scheduled for order: " + orderId);
        return "TRACK-" + orderId;
    }
}

class EmailService {

    void sendConfirmation(String orderId, String trackingId) {
        System.out.println("Confirmation email sent for order: "
                + orderId + ", tracking: " + trackingId);
    }
}

// Facade: Coordinates multiple services behind a simple interface.
class OrderFacade {

    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    private final EmailService email;

    OrderFacade(
            InventoryService inventory,
            PaymentService payment,
            ShippingService shipping,
            EmailService email) {

        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
        this.email = email;
    }

    String placeOrder(
            String orderId,
            String sku,
            int quantity,
            String card,
            long amount,
            String address) {

        inventory.reserve(sku, quantity);

        try {
            payment.charge(card, amount);
        } catch (PaymentDeclinedException ex) {
            inventory.release(sku, quantity);
            return "FAILED: " + ex.getMessage();
        }

        String trackingId = shipping.schedule(orderId, address);
        email.sendConfirmation(orderId, trackingId);

        return "PLACED: " + trackingId;
    }
}

class PaymentDeclinedException extends RuntimeException {

    PaymentDeclinedException(String message) {
        super(message);
    }
}

public class App {

    public static void main(String[] args) {

        OrderFacade orderFacade = new OrderFacade(
                new InventoryService(),
                new PaymentService(),
                new ShippingService(),
                new EmailService()
        );

        String result = orderFacade.placeOrder(
                "ORD-101",
                "IPHONE-16",
                2,
                "CARD-123",
                150000,
                "Bangalore"
        );

        System.out.println("\nOrder Result: " + result);
    }
}
