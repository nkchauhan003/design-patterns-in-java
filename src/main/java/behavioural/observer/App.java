
package behavioural.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Observer: Defines the notification contract.
interface OrderListener {
    void onOrderPlaced(Order order);
}

// Event data published to all registered observers.
record Order(String orderId, String product, int quantity) {
}

// Subject / Publisher: Manages observers and notifies them.
class OrderService {

    private final List<OrderListener> listeners =
            new CopyOnWriteArrayList<>();

    void subscribe(OrderListener listener) {
        listeners.add(listener);
    }

    void unsubscribe(OrderListener listener) {
        listeners.remove(listener);
    }

    void placeOrder(Order order) {
        // Persist the order before notifying observers.
        System.out.println("Order placed: " + order.orderId());

        for (OrderListener listener : listeners) {
            listener.onOrderPlaced(order);
        }
    }
}

// Concrete Observer: Sends order confirmation email.
class EmailListener implements OrderListener {

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("Email sent for order: " + order.orderId());
    }
}

// Concrete Observer: Updates inventory.
class InventoryListener implements OrderListener {

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("Stock updated for: " + order.product());
    }
}

// Concrete Observer: Records order analytics.
class AnalyticsListener implements OrderListener {

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("Analytics recorded for: " + order.orderId());
    }
}

public class App {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();

        OrderListener emailListener = new EmailListener();
        OrderListener inventoryListener = new InventoryListener();
        OrderListener analyticsListener = new AnalyticsListener();

        // Register observers.
        orderService.subscribe(emailListener);
        orderService.subscribe(inventoryListener);
        orderService.subscribe(analyticsListener);

        Order order = new Order("ORD-101", "IPHONE-16", 2);

        orderService.placeOrder(order);

        // Unregister an observer.
        orderService.unsubscribe(analyticsListener);

        System.out.println("\nPlacing another order:");

        orderService.placeOrder(
                new Order("ORD-102", "MACBOOK", 1)
        );
    }
}
