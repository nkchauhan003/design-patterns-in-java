package creational.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Lazy initialization Singleton using Double-Checked Locking.
 * <p>
 * Provides thread-safe lazy initialization and additional protection
 * against reflection-based instantiation and serialization-related
 * instance duplication.
 */
public class ProtectedDoubleCheckedLockingSingleton implements Serializable {

    private static final long serialVersionUID = 1L;

    // volatile ensures visibility across threads and prevents
    // unsafe instruction reordering during instance initialization.
    private static volatile ProtectedDoubleCheckedLockingSingleton INSTANCE;

    // Private constructor prevents external instantiation.
    // Additional check rejects constructor calls through reflection
    // after the Singleton instance has been initialized.
    private ProtectedDoubleCheckedLockingSingleton() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already exists");
        }
    }

    /**
     * Returns the single shared instance.
     * <p>
     * Uses Double-Checked Locking for thread-safe lazy initialization
     * while avoiding synchronization after instance creation.
     *
     * @return the Singleton instance
     */
    public static ProtectedDoubleCheckedLockingSingleton getInstance() {

        // First check: Avoid synchronization if the instance already exists.
        if (INSTANCE == null) {

            synchronized (ProtectedDoubleCheckedLockingSingleton.class) {

                // Second check: Another thread might have initialized it.
                if (INSTANCE == null) {
                    INSTANCE = new ProtectedDoubleCheckedLockingSingleton();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Ensures deserialization returns the existing Singleton instance
     * instead of creating a separate instance.
     *
     * @return the existing Singleton instance
     * @throws ObjectStreamException if deserialization fails
     */
    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}