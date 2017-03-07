package io.github.phantamanta44.shlgl.util;

/**
 * Thrown when a mutator is called on an immutable object.
 * @author Evan Geng
 */
public class ImmutableException extends RuntimeException {

    /**
     * Creates an ImmutableException.
     * @param propName The name of the immutable property.
     */
    public ImmutableException(String propName) {
        super("Cannot mutate immutable: " + propName);
    }

    /**
     * Creates an ImmutableException.
     */
    public ImmutableException() {
        super("Cannot mutate immutable!");
    }

}
