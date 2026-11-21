package creational.singleton;

/**
 * Lazy initialization Singleton using Double-Checked Locking.
 *
 * The instance is created only when getInstance() is called for the first time.
 * Synchronization is performed only during instance creation, avoiding
 * synchronization overhead on subsequent calls.
 */
public class DoubleCheckedLockingSingleton {

    // volatile ensures visibility across threads and prevents unsafe
    // instruction reordering during instance initialization.
    private static volatile DoubleCheckedLockingSingleton INSTANCE;

    // Private constructor prevents external instantiation.
    private DoubleCheckedLockingSingleton() {
    }

    /**
     * Returns the single shared instance of this class.
     *
     * @return the singleton instance
     */
    public static DoubleCheckedLockingSingleton getInstance() {

        // First check: Avoid acquiring the lock if the instance already exists.
        if (INSTANCE == null) {

            // Acquire the class-level lock before instance creation.
            synchronized (DoubleCheckedLockingSingleton.class) {

                // Second check: Another thread might have initialized it
                // while the current thread was waiting for the lock.
                if (INSTANCE == null) {
                    INSTANCE = new DoubleCheckedLockingSingleton();
                }
            }
        }

        return INSTANCE;
    }
}