package creational.singleton;

/**
 * Lazy initialization Singleton using the Static Holder pattern.
 *
 * The instance is created only when getInstance() is called for the first time.
 * JVM class initialization guarantees thread safety without explicit synchronization.
 */
public class HolderLazySingleton {

    // Private constructor prevents external instantiation.
    private HolderLazySingleton() {
    }

    /**
     * Static nested holder class.
     *
     * Initialized only when Holder.INSTANCE is first accessed.
     */
    private static class Holder {

        // Singleton instance created once during Holder class initialization.
        private static final HolderLazySingleton INSTANCE =
                new HolderLazySingleton();
    }

    /**
     * Returns the single shared instance of this class.
     *
     * @return the singleton instance
     */
    public static HolderLazySingleton getInstance() {
        return Holder.INSTANCE;
    }
}