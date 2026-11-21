
package structural.decorator;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

interface RateProvider {
    BigDecimal rate(String from, String to);
}

// Concrete component: fetches the exchange rate from an external service.
class HttpRateProvider implements RateProvider {

    @Override
    public BigDecimal rate(String from, String to) {
        // Placeholder for an actual HTTP API call.
        return new BigDecimal("90.30");
    }
}

// Decorator: retries the underlying provider when it reports a failure.
class RetryingRateProvider implements RateProvider {

    private final RateProvider next;
    private final int attempts;

    RetryingRateProvider(RateProvider next, int attempts) {
        this.next = Objects.requireNonNull(next);
        if (attempts < 1) {
            throw new IllegalArgumentException("Attempts must be at least 1");
        }
        this.attempts = attempts;
    }

    @Override
    public BigDecimal rate(String from, String to) {
        RateUnavailableException last = null;

        for (int i = 0; i < attempts; i++) {
            try {
                return next.rate(from, to);
            } catch (RateUnavailableException e) {
                last = e;
            }
        }

        throw last;
    }
}

// Decorator: caches rates for a configured time-to-live.
class CachingRateProvider implements RateProvider {

    private final RateProvider next;
    private final Duration ttl;
    private final Clock clock;
    private final Map<RateKey, Entry> cache = new ConcurrentHashMap<>();

    CachingRateProvider(RateProvider next, Duration ttl, Clock clock) {
        this.next = Objects.requireNonNull(next);
        this.ttl = Objects.requireNonNull(ttl);
        this.clock = Objects.requireNonNull(clock);

        if (ttl.isNegative() || ttl.isZero()) {
            throw new IllegalArgumentException("TTL must be positive");
        }
    }

    @Override
    public BigDecimal rate(String from, String to) {
        RateKey key = new RateKey(from, to);
        Instant now = clock.instant();

        Entry entry = cache.get(key);

        if (entry != null && now.isBefore(entry.at().plus(ttl))) {
            return entry.rate();
        }

        BigDecimal fresh = next.rate(from, to);
        cache.put(key, new Entry(fresh, clock.instant()));

        return fresh;
    }
}

// Structured cache key avoids collisions between currency pairs.
record RateKey(String from, String to) {
}

record Entry(BigDecimal rate, Instant at) {
}

class RateUnavailableException extends RuntimeException {
    RateUnavailableException(String message) {
        super(message);
    }
}

public class App {

    public static void main(String[] args) {
        RateProvider rateProvider = new CachingRateProvider(
                new RetryingRateProvider(new HttpRateProvider(), 3),
                Duration.ofMillis(500),
                Clock.systemUTC()
        );

        System.out.println(rateProvider.rate("INR", "USD"));
    }
}
