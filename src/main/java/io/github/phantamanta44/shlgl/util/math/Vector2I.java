package io.github.phantamanta44.shlgl.util.math;

import io.github.phantamanta44.shlgl.util.ImmutableException;
import io.github.phantamanta44.shlgl.util.memory.IShared;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import io.github.phantamanta44.shlgl.util.memory.ResourcePool;

/**
 * A 2-dimensional vector of integer components.
 * @author Evan Geng
 */
public class Vector2I implements IShared {

    /**
     * The immutable constant vector (0, 0).
     */
    public static Vector2I ZERO = new Vector2I() {

        @Override
        public Vector2I x(int x) {
            throw new ImmutableException("x");
        }

        @Override
        public Vector2I y(int y) {
            throw new ImmutableException("y");
        }

    };

    /**
     * Creates a new vector with the given components.
     * @param x The x component.
     * @param y The y component.
     * @return A {@link Pooled} instance containing the new vector.
     */
    public static Pooled<Vector2I> of(int x, int y) {
        Pooled<Vector2I> res = zeroes();
        res.get().set(x, y);
        return res;
    }

    /**
     * Creates a new (mutable) vector of all zeroes.
     * @return A {@link Pooled} instance containing the new vector.
     */
    public static Pooled<Vector2I> zeroes() {
        return pool.get();
    }

    /**
     * The shared resource pool governing Vector2I instantiation.
     */
    private static final ResourcePool<Vector2I> pool = new ResourcePool<>(Vector2I::new);

    /**
     * The vector's x component.
     */
    private int x;

    /**
     * The vector's y component.
     */
    private int y;

    /**
     * Creates a new vector with zeroed components.
     */
    private Vector2I() {
        onFree();
    }

    @Override
    public void onFree() {
        x = y = 0;
    }

    /**
     * Sets this vector's components.
     * @param x The new x value.
     * @param y The new y value.
     * @return This vector.
     */
    public Vector2I set(int x, int y) {
        return x(x).y(y);
    }

    /**
     * Gets this vector's x component.
     * @return The x value.
     */
    public int x() {
        return x;
    }

    /**
     * Sets this vector's x component.
     * @param x The new x value.
     * @return This vector.
     */
    public Vector2I x(int x) {
        this.x = x;
        return this;
    }

    /**
     * Gets this vector's y component.
     * @return The y value.
     */
    public int y() {
        return y;
    }

    /**
     * Sets this vector's y component.
     * @param y The new y value.
     * @return This vector.
     */
    public Vector2I y(int y) {
        this.y = y;
        return this;
    }

    /**
     * Calculates this vector's magnitude.
     * @return The magnitude.
     */
    public double magnitude() {
        return MathUtils.magnitude(x, y);
    }

    /**
     * Adds another vector to this vector.
     * @param other The other addend vector.
     * @return This vector.
     */
    public Vector2I add(Vector2I other) {
        return add(other.x(), other.y());
    }

    /**
     * Adds another vector to this vector.
     * @param x The x addend.
     * @param y The y addend.
     * @return This vector.
     */
    public Vector2I add(int x, int y) {
        return addX(x).addY(y);
    }

    /**
     * Adds to this vector's x component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector2I addX(int addend) {
        return x(x + addend);
    }

    /**
     * Adds to this vector's y component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector2I addY(int addend) {
        return y(y + addend);
    }

    /**
     * Multiplies this vector by a scalar.
     * @param factor The multiplier.
     * @return This vector.
     */
    public Vector2I multiply(double factor) {
        return x((int)Math.floor(x * factor)).y((int)Math.floor(y * factor));
    }

}
