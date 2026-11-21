package creational.singleton;

/**
 * Eagerly initialized Singleton implementation.
 * <p>
 * The single instance is created when the class is initialized by the JVM.
 * JVM class initialization guarantees thread safety without explicit synchronization.
 */
public class EagerSingleton {

    // Creates a single instance during class initialization.
    // static ensures one instance per class, and final prevents reassignment.
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private constructor prevents external instantiation.
    private EagerSingleton() {
    }

    /**
     * Returns the single shared instance of this class.
     *
     * @return the singleton instance
     */
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}