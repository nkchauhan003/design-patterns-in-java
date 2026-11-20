package creational.singleton;

/**
 * Eagerly initialized Singleton implementation using a static initialization block.
 * Allows exception handling during instance creation when the class is loaded by the JVM.
 */
public class EagerSingletonStaticBlock {

    // Holds the single instance; cannot be 'final' because it is assigned inside a try-catch block
    private static EagerSingletonStaticBlock INSTANCE;

    // Static block runs once when the class is loaded, providing thread-safe initialization
    static {
        try {
            INSTANCE = new EagerSingletonStaticBlock();
        } catch (Exception e) {
            // Note: swallowing/ignoring after fillInStackTrace() can leave INSTANCE as null;
            // rethrowing a RuntimeException is often safer if initialization must not fail silently
            e.fillInStackTrace();
        }
    }

    // Private constructor prevents external instantiation using 'new'
    private EagerSingletonStaticBlock() {
    }

    /**
     * Returns the single shared instance of {@code EagerSingletonStaticBlock}.
     *
     * @return the singleton instance (may be {@code null} if static initialization failed)
     */
    public static EagerSingletonStaticBlock getInstance() {
        return INSTANCE;
    }
}