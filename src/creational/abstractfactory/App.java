package creational.abstractfactory;

/**
 * Abstract product defining the contract for cloud storage services.
 */
interface Storage {

    void save(Object data);

    Object get();
}

/**
 * Abstract product defining the contract for cloud queue services.
 */
interface Queue {

    void offer(Object data);

    Object poll();
}

/**
 * Concrete product representing AWS S3 storage.
 */
class AwsStorage implements Storage {

    @Override
    public void save(Object data) {
        System.out.println("Saved to S3 !!!");
    }

    @Override
    public Object get() {
        System.out.println("Got from S3 !!!");
        return null;
    }
}

/**
 * Concrete product representing Google Cloud Storage.
 */
class GoogleStorage implements Storage {

    @Override
    public void save(Object data) {
        System.out.println("Saved to GCS !!!");
    }

    @Override
    public Object get() {
        System.out.println("Got from GCS !!!");
        return null;
    }
}

/**
 * Concrete product representing AWS SQS.
 */
class AwsQueue implements Queue {

    @Override
    public void offer(Object data) {
        System.out.println("Saved to SQS !!!");
    }

    @Override
    public Object poll() {
        System.out.println("Got from SQS !!!");
        return null;
    }
}

/**
 * Concrete product representing Google Cloud Tasks.
 */
class GoogleQueue implements Queue {

    @Override
    public void offer(Object data) {
        System.out.println("Saved to CloudTasks !!!");
    }

    @Override
    public Object poll() {
        System.out.println("Got from CloudTasks !!!");
        return null;
    }
}

/**
 * Abstract Factory defining methods to create families of related
 * cloud services such as Storage and Queue.
 */
interface CloudServiceFactory {

    /**
     * Creates a cloud queue service.
     *
     * @return the queue implementation
     */
    Queue createQueue();

    /**
     * Creates a cloud storage service.
     *
     * @return the storage implementation
     */
    Storage createStorage();
}

/**
 * Concrete factory responsible for creating AWS cloud services.
 */
class AwsCloudServiceFactory implements CloudServiceFactory {

    @Override
    public Queue createQueue() {
        return new AwsQueue();
    }

    @Override
    public Storage createStorage() {
        return new AwsStorage();
    }
}

/**
 * Concrete factory responsible for creating Google Cloud services.
 */
class GoogleCloudServiceFactory implements CloudServiceFactory {

    @Override
    public Queue createQueue() {
        return new GoogleQueue();
    }

    @Override
    public Storage createStorage() {
        return new GoogleStorage();
    }
}

/**
 * Demonstrates the Abstract Factory Design Pattern.
 */
public class App {

    public static void main(String[] args) {

        // Create AWS service family.
        CloudServiceFactory awsFactory = new AwsCloudServiceFactory();

        Queue awsQueue = awsFactory.createQueue();
        Storage awsStorage = awsFactory.createStorage();

        awsQueue.offer("Item");
        awsQueue.poll();

        awsStorage.save("File");
        awsStorage.get();

        // Create Google Cloud service family.
        CloudServiceFactory googleFactory = new GoogleCloudServiceFactory();

        Queue googleQueue = googleFactory.createQueue();
        Storage googleStorage = googleFactory.createStorage();

        googleQueue.offer("Item");
        googleQueue.poll();

        googleStorage.save("File");
        googleStorage.get();
    }
}