package creational.builder;

/**
 * Immutable Alert object created using the Builder Design Pattern.
 *
 * Email and message are mandatory fields.
 * Title and description are optional fields.
 */
final class Alert {

    private final String email;
    private final String message;
    private final String title;
    private final String description;

    // Private constructor ensures object creation through the Builder.
    private Alert(Builder builder) {
        this.email = builder.email;
        this.message = builder.message;
        this.title = builder.title;
        this.description = builder.description;
    }

    /**
     * Static nested Builder class responsible for constructing Alert objects.
     */
    public static class Builder {

        // Mandatory fields.
        private final String email;
        private final String message;

        // Optional fields.
        private String title;
        private String description;

        public Builder(String email, String message) {
            this.email = email;
            this.message = message;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Validates mandatory fields and constructs the Alert object.
         */
        public Alert build() {

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Invalid Email");
            }

            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }

            return new Alert(this);
        }
    }
}

/**
 * Demonstrates the Builder Design Pattern.
 */
public class Main {

    public static void main(String[] args) {

        Alert alert = new Alert.Builder("anc@gmail.com", "Message")
                .title("")
                .build();
    }
}