
package chainofresponsibility;

// Immutable request object passed through the handler chain.
record Request(String token, String body, int count) {
}

// Base handler: maintains the reference to the next handler.
abstract class Handler {

    private Handler next;

    // Links the next handler and returns it for fluent chain construction.
    Handler linkWith(Handler next) {
        this.next = next;
        return next;
    }

    // Entry point for processing a request.
    public String process(Request request) {
        return handle(request);
    }

    // Passes the request to the next handler.
    // If no handler exists, the request is considered successfully processed.
    protected String handle(Request request) {
        return next == null ? "200 OK" : next.handle(request);
    }
}

// Validates authentication before allowing the request to proceed.
class AuthHandler extends Handler {

    @Override
    protected String handle(Request request) {

        if (!"valid".equals(request.token()))
            return "401 Unauthorized";

        return super.handle(request);
    }
}

// Checks whether the request exceeds the allowed request limit.
class RateLimitHandler extends Handler {

    private final int limit;

    RateLimitHandler(int limit) {
        this.limit = limit;
    }

    @Override
    protected String handle(Request request) {

        if (request.count() > limit)
            return "429 Too Many Requests";

        return super.handle(request);
    }
}

// Validates the request body before processing the order.
class ValidationHandler extends Handler {

    private static final int MAX_LENGTH = 500;

    @Override
    protected String handle(Request request) {

        if (request.body() == null || request.body().isBlank())
            return "400 Bad Request: Empty body";

        if (request.body().length() > MAX_LENGTH)
            return "400 Bad Request: Body too large";

        return super.handle(request);
    }
}

// Performs the actual business operation.
class OrderHandler extends Handler {

    @Override
    protected String handle(Request request) {
        return "Order Processed Successfully";
    }
}

public class App {

    public static void main(String[] args) {

        // Build the processing chain.
        Handler chain = new AuthHandler();

        chain.linkWith(new RateLimitHandler(3))
                .linkWith(new ValidationHandler())
                .linkWith(new OrderHandler());

        // Valid request: passes through all handlers.
        Request validRequest = new Request("valid", "Create Order", 2);

        System.out.println(chain.process(validRequest));

        // Invalid authentication: stops at AuthHandler.
        Request invalidAuth = new Request("invalid", "Create Order", 2);

        System.out.println(chain.process(invalidAuth));

        // Rate limit exceeded: stops at RateLimitHandler.
        Request rateLimited = new Request("valid", "Create Order", 5);

        System.out.println(chain.process(rateLimited));

        // Invalid body: stops at ValidationHandler.
        Request invalidBody = new Request("valid", "", 2);

        System.out.println(chain.process(invalidBody));
    }
}
