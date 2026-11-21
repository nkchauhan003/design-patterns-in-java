package creational.factorymethod;

/**
 * Product interface defining the contract for notification channels.
 */
interface Channel {
    void send();
}

/**
 * Concrete product representing an SMS notification channel.
 */
final class Sms implements Channel {

    @Override
    public void send() {
        System.out.println("SMS Sent !!!");
    }
}

/**
 * Concrete product representing an Email notification channel.
 */
final class Email implements Channel {

    @Override
    public void send() {
        System.out.println("Email Sent !!!");
    }
}

/**
 * Creator class defining the Factory Method and notification workflow.
 * <p>
 * Subclasses decide which Channel implementation to instantiate.
 */
abstract class NotificationService {

    /**
     * Factory Method responsible for creating a notification channel.
     *
     * @return the notification channel
     */
    protected abstract Channel createChannel();

    /**
     * Defines the notification workflow using the created channel.
     */
    public void sendMessage() {
        Channel channel = createChannel();
        channel.send();
    }
}

/**
 * Concrete creator responsible for creating SMS notification channels.
 */
final class SmsNotificationService extends NotificationService {

    @Override
    protected Channel createChannel() {
        return new Sms();
    }
}

/**
 * Concrete creator responsible for creating Email notification channels.
 */
final class EmailNotificationService extends NotificationService {

    @Override
    protected Channel createChannel() {
        return new Email();
    }
}

/**
 * Demonstrates the Factory Method Design Pattern.
 */
public class App {

    public static void main(String[] args) {

        NotificationService smsService = new SmsNotificationService();
        smsService.sendMessage();

        NotificationService emailService = new EmailNotificationService();
        emailService.sendMessage();
    }
}