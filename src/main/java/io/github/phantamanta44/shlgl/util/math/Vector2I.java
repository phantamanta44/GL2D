package io.github.phantamanta44.shlgl.util.math;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A 2-dimensional vector of integer components.
 * @author Evan Geng
 */
public class Vector2I {

    /**
     * The immutable constant vector (0, 0).
     */
    public static Vector2I ZERO = new Vector2I(0, 0) {

    };

    /**
     * Creates a new vector with the given components.
     * @param x The x component.
     * @param y The y component.
     * @return The new vector.
     */
    public static Vector2I of(int x, int y) {
        throw new NotImplementedException(); // TODO Implement with resource pool
    }

    /**
     * Creates a new (mutable) vector of all zeroes.
     * @return The new vector (0, 0).
     */
    public static Vector2I zeroes() {
        return of(0, 0);
    }

    /**
     * The vector's x component.
     */
    private int x;

    /**
     * The vector's y component.
     */
    private int y;

    /**
     * Creates a new vector with the given components.
     * @param x The x component.
     * @param y The y component.
     */
    private Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // TODO Getters and setters

    // TODO Vector addition, multiplication

    // TODO Magnitude property

    // TODO Normalize function

}
