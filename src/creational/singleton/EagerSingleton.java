package creational.singleton;

/**
 * Eagerly initialized Singleton implementation.
 * The single instance is created when the class is loaded by the JVM,
 * providing built-in thread safety without explicit synchronization.
 */
public class EagerSingleton {

    // Created once at class-loading time; 'final' prevents accidental reassignment
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private constructor prevents external instantiation using 'new'
    private EagerSingleton() {
    }

    /**
     * Returns the single shared instance of {@code EagerSingleton}.
     *
     * @return the singleton instance
     */
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}