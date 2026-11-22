
package behavioural.state;

// Defines the operations supported by an order.
// Each state decides which operations are valid.
interface OrderState {

    String name();

    // Default behavior: reject operations not supported by a state.
    default OrderState pay() {
        throw new IllegalStateException("Cannot pay in state: " + name());
    }

    default OrderState ship() {
        throw new IllegalStateException("Cannot ship in state: " + name());
    }

    default OrderState cancel() {
        throw new IllegalStateException("Cannot cancel in state: " + name());
    }
}

// Initial state: an order can be paid or cancelled.
class NewState implements OrderState {

    @Override
    public String name() {
        return "NEW";
    }

    @Override
    public OrderState pay() {
        // Paying transitions the order to PAID.
        return new PaidState();
    }

    @Override
    public OrderState cancel() {
        // Cancelling transitions the order to CANCELLED.
        return new CancelledState();
    }
}

// An order can be shipped or cancelled after payment.
class PaidState implements OrderState {

    @Override
    public String name() {
        return "PAID";
    }

    @Override
    public OrderState ship() {
        // Shipping transitions the order to SHIPPED.
        return new ShippedState();
    }

    @Override
    public OrderState cancel() {
        // Cancelling transitions the order to CANCELLED.
        return new CancelledState();
    }
}

// Terminal state: no further operations are allowed.
class ShippedState implements OrderState {

    @Override
    public String name() {
        return "SHIPPED";
    }
}

// Terminal state: no further operations are allowed.
class CancelledState implements OrderState {

    @Override
    public String name() {
        return "CANCELLED";
    }
}

// Context: maintains the current state and delegates operations to it.
class Order {

    private OrderState state = new NewState();

    void pay() {
        state = state.pay();
    }

    void ship() {
        state = state.ship();
    }

    void cancel() {
        state = state.cancel();
    }

    String getState() {
        return state.name();
    }
}

public class App {

    public static void main(String[] args) {

        Order order = new Order();

        System.out.println(order.getState()); // NEW

        order.pay();
        System.out.println(order.getState()); // PAID

        order.ship();
        System.out.println(order.getState()); // SHIPPED

        // Invalid transition: a shipped order cannot be cancelled.
        // order.cancel(); // Throws IllegalStateException
    }
}
