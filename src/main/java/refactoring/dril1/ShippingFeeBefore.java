package refactoring.dril1;

class ShippingFeeBefore {
    long fee(String country, int grams) {
        long fee;
        if (country.equals("IN")) {
            if (grams <= 500) fee = 49;
            else if (grams <= 2000) fee = 99;
            else fee = 99 + ((grams - 2000 + 999) / 1000) * 30;
        } else if (country.equals("US") || country.equals("CA")) {
            if (grams <= 500) fee = 400;
            else if (grams <= 2000) fee = 700;
            else fee = 700 + ((grams - 2000 + 999) / 1000) * 150;
        } else {
            if (grams <= 500) fee = 900;
            else if (grams <= 2000) fee = 1500;
            else fee = 1500 + ((grams - 2000 + 999) / 1000) * 300;
        }
        return fee;
    }
}