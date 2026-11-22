
package chainofresponsibility;

// Immutable request passed through the handler chain.
record Request(String token, String body, int count) {
}

// Base handler maintains a reference to the next handler.
abstract class Handler {
    private Handler next;

    // Links the next handler and supports fluent chain construction.
    Handler linkWith(Handler next) {
        this.next = next;
        return next;
    }

    // Passes the request to the next handler.
    // If no handler exists, the request is considered successfully processed.
    protected String handle(Request r) {
        return next == null ? "200 OK" : next.handle(r);
    }
}

// Validates the authentication token.
class AuthHandler extends Handler {

    @Override
    protected String handle(Request r) {
        if (!"valid".equals(r.token()))
            return "401 Unauthorized";

        // Authentication successful; continue to the next handler.
        return super.handle(r);
    }
}

// Checks whether the request count exceeds the configured limit.
class RateLimitHandler extends Handler {
    private final int limit;

    RateLimitHandler(int limit) {
        this.limit = limit;
    }

    @Override
    protected String handle(Request r) {
        if (limit < r.count())
            return "429 Too Many Requests";

        // Rate limit not exceeded; continue processing.
        return super.handle(r);
    }
}

// Validates the maximum allowed request body length.
class ValidationHandler extends Handler {
    private final int maxLength = 500;

    @Override
    protected String handle(Request r) {
        if (r.body() != null && maxLength < r.body().length())
            return "400 Bad Request";

        // Validation successful; continue to the next handler.
        return super.handle(r);
    }
}

// Performs the actual order processing.
class OrderHandler extends Handler {

    @Override
    protected String handle(Request r) {
        return "Order";
    }
}

public class App {
    public static void main(String[] args) {

        // Build the chain of responsibility.
        Handler chain = new AuthHandler();

        chain.linkWith(new RateLimitHandler(3))
                .linkWith(new ValidationHandler())
                .linkWith(new OrderHandler());

        // Submit the request to the first handler.
        String response = chain.handle(new Request(
                "90982b23-d49c-4a01-a443-195a04e643ca",
                "{\"customer_id\":\"C1\",\"items\":[{\"id\":\"P1\",\"qty\":2}],\"total\":39.98}",
                1
        ));

        System.out.println(response);
    }
}
