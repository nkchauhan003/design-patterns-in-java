
package refactoring.dril1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ShippingFeeBeforeTest {

    private final ShippingFeeBefore shippingFee = new ShippingFeeBefore();

    @Test
    void shouldCalculateIndiaShippingFee() {
        assertEquals(49, shippingFee.fee("IN", 500));
        assertEquals(99, shippingFee.fee("IN", 501));
        assertEquals(99, shippingFee.fee("IN", 2000));
        assertEquals(129, shippingFee.fee("IN", 2001));
        assertEquals(129, shippingFee.fee("IN", 3000));
        assertEquals(159, shippingFee.fee("IN", 3001));
        assertEquals(159, shippingFee.fee("IN", 4000));
        assertEquals(189, shippingFee.fee("IN", 4001));
    }

    @Test
    void shouldCalculateUSShippingFee() {
        assertEquals(400, shippingFee.fee("US", 500));
        assertEquals(700, shippingFee.fee("US", 501));
        assertEquals(700, shippingFee.fee("US", 2000));
        assertEquals(850, shippingFee.fee("US", 2001));
        assertEquals(850, shippingFee.fee("US", 3000));
        assertEquals(1000, shippingFee.fee("US", 3001));
    }

    @Test
    void shouldCalculateCanadaShippingFee() {
        assertEquals(400, shippingFee.fee("CA", 500));
        assertEquals(700, shippingFee.fee("CA", 501));
        assertEquals(700, shippingFee.fee("CA", 2000));
        assertEquals(850, shippingFee.fee("CA", 2001));
        assertEquals(1000, shippingFee.fee("CA", 3001));
    }

    @Test
    void shouldCalculateOtherCountriesShippingFee() {
        assertEquals(900, shippingFee.fee("UK", 500));
        assertEquals(1500, shippingFee.fee("UK", 501));
        assertEquals(1500, shippingFee.fee("UK", 2000));
        assertEquals(1800, shippingFee.fee("UK", 2001));
        assertEquals(1800, shippingFee.fee("UK", 3000));
        assertEquals(2100, shippingFee.fee("UK", 3001));
    }

    @Test
    void shouldApplyAdditionalFeeForEveryExtra1000Grams() {
        assertEquals(129, shippingFee.fee("IN", 2001));
        assertEquals(129, shippingFee.fee("IN", 3000));
        assertEquals(159, shippingFee.fee("IN", 3001));

        assertEquals(850, shippingFee.fee("US", 2001));
        assertEquals(850, shippingFee.fee("US", 3000));
        assertEquals(1000, shippingFee.fee("US", 3001));

        assertEquals(1800, shippingFee.fee("UK", 2001));
        assertEquals(1800, shippingFee.fee("UK", 3000));
        assertEquals(2100, shippingFee.fee("UK", 3001));
    }
}
