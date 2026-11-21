package creational.singleton;

/**
 * Eagerly initialized Singleton implementation using a static initialization block.
 * <p>
 * The instance is created when the class is initialized by the JVM.
 * The static initialization mechanism guarantees thread safety.
 * A static block also allows exception handling during instance creation.
 */
public class StaticBlockEagerSingleton {

    // Holds the single instance.
    // Cannot be final because it is assigned inside the static initialization block.
    private static StaticBlockEagerSingleton INSTANCE;

    // Executes once during class initialization.
    // JVM guarantees thread-safe execution of static initialization.
    static {
        try {
            INSTANCE = new StaticBlockEagerSingleton();
        } catch (Exception e) {
            // Fail class initialization rather than allowing a null singleton instance.
            throw new ExceptionInInitializerError(e);
        }
    }

    // Private constructor prevents external instantiation.
    private StaticBlockEagerSingleton() {
    }

    /**
     * Returns the single shared instance of this class.
     *
     * @return the singleton instance
     */
    public static StaticBlockEagerSingleton getInstance() {
        return INSTANCE;
    }
}