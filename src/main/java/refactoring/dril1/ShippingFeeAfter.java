package refactoring.dril1;

public class ShippingFeeAfter {
    long fee(String country, int grams) {
        return switch (country) {
            case "IN" -> Country.IN.fee(grams);
            case "US" -> Country.US.fee(grams);
            case "CA" -> Country.CA.fee(grams);
            default -> Country.DEFAULT.fee(grams);
        };
    }
}

enum Country {
    IN(49, 99, 30),
    US(400, 700, 150),
    CA(400, 700, 150),
    DEFAULT(900, 1500, 300);

    Country(long baseFee, long mediumWeightFee, long additionalFee) {
        this.baseFee = baseFee;
        this.mediumWeightFee = mediumWeightFee;
        this.additionalFee = additionalFee;
    }

    private final long baseFee;
    private final long mediumWeightFee;
    private final long additionalFee;

    public long fee(int grams) {
        if (grams <= 500) return baseFee;
        if (grams <= 2000) return mediumWeightFee;
        return mediumWeightFee + ((grams - 2000 + 999) / 1000) * additionalFee;
    }
}
