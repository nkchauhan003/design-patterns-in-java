package creational.singleton;

/**
 * Singleton implementation using Java Enum.
 *
 * The JVM guarantees that each enum constant is instantiated only once.
 * Enum Singleton also provides built-in thread safety and protection
 * against serialization-related duplication and reflection-based instantiation.
 */
public enum EnumSingleton {

    // The single Singleton instance.
    INSTANCE;

    /**
     * Example business method.
     * 
     * Add Singleton-specific operations here.
     */
    public void doSomething() {
        // Business logic goes here.
    }
}