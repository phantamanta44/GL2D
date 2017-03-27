package io.github.phantamanta44.shlgl.util.math;

import io.github.phantamanta44.shlgl.util.ImmutableException;
import io.github.phantamanta44.shlgl.util.memory.IShared;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import io.github.phantamanta44.shlgl.util.memory.ResourcePool;

/**
 * A 4-dimensional vector of float components.
 * @author Evan Geng
 */
public class Vector4F implements IShared {

    /**
     * The immutable constant vector (0, 0, 0, 0).
     */
    public static Vector4F ZERO = new Vector4F() {

        @Override
        public Vector4F x(float x) {
            throw new ImmutableException("x");
        }

        @Override
        public Vector4F y(float y) {
            throw new ImmutableException("y");
        }

        @Override
        public Vector4F z(float z) {
            throw new ImmutableException("z");
        }

        @Override
        public Vector4F w(float w) {
            throw new ImmutableException("w");
        }

    };

    /**
     * Creates a new vector with the given components.
     * @param x The x component.
     * @param y The y component.
     * @param z The z component.
     * @param w The w component.
     * @return A {@link Pooled} instance containing the new vector.
     */
    public static Pooled<Vector4F> of(float x, float y, float z, float w) {
        Pooled<Vector4F> res = zeroes();
        res.get().set(x, y, z, w);
        return res;
    }

    /**
     * Creates a new (mutable) vector of all zeroes.
     * @return A {@link Pooled} instance containing the new vector.
     */
    public static Pooled<Vector4F> zeroes() {
        return pool.get();
    }

    /**
     * The shared resource pool governing Vector4F instantiation.
     */
    private static final ResourcePool<Vector4F> pool = new ResourcePool<>(Vector4F::new);

    /**
     * The vector's x component.
     */
    private float x;

    /**
     * The vector's y component.
     */
    private float y;

    /**
     * The vector's z component.
     */
    private float z;

    /**
     * The vector's w component.
     */
    private float w;

    /**
     * Creates a new vector with zeroed components.
     */
    private Vector4F() {
        onFree();
    }

    @Override
    public void onFree() {
        x = y = z = w = 0;
    }

    /**
     * Sets this vector's components.
     * @param x The new x value.
     * @param y The new y value.
     * @param z The new z value.
     * @param w The new w value.
     * @return This vector.
     */
    public Vector4F set(float x, float y, float z, float w) {
        return x(x).y(y).z(z).w(w);
    }

    /**
     * Gets this vector's x component.
     * @return The x value.
     */
    public float x() {
        return x;
    }

    /**
     * Sets this vector's x component.
     * @param x The new x value.
     * @return This vector.
     */
    public Vector4F x(float x) {
        this.x = x;
        return this;
    }

    /**
     * Gets this vector's y component.
     * @return The y value.
     */
    public float y() {
        return y;
    }

    /**
     * Sets this vector's y component.
     * @param y The new y value.
     * @return This vector.
     */
    public Vector4F y(float y) {
        this.y = y;
        return this;
    }

    /**
     * Gets this vector's z component.
     * @return The z value.
     */
    public float z() {
        return z;
    }

    /**
     * Sets this vector's z component.
     * @param z The new z value.
     * @return This vector.
     */
    public Vector4F z(float z) {
        this.z = z;
        return this;
    }

    /**
     * Gets this vector's w component.
     * @return The w value.
     */
    public float w() {
        return w;
    }

    /**
     * Sets this vector's w component.
     * @param w The new w value.
     * @return This vector.
     */
    public Vector4F w(float w) {
        this.w = w;
        return this;
    }

    /**
     * Calculates this vector's magnitude.
     * @return The magnitude.
     */
    public double magnitude() {
        return MathUtils.magnitude(x, y, z, w);
    }

    /**
     * Adds another vector to this vector.
     * @param other The other addend vector.
     * @return This vector.
     */
    public Vector4F add(Vector4F other) {
        return add(other.x(), other.y(), other.z(), other.w());
    }

    /**
     * Adds another vector to this vector.
     * @param x The x addend.
     * @param y The y addend.
     * @param z The z addend.
     * @param w The w addend.
     * @return This vector.
     */
    public Vector4F add(float x, float y, float z, float w) {
        return addX(x).addY(y).addZ(z).addW(w);
    }

    /**
     * Adds to this vector's x component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector4F addX(float addend) {
        return x(x + addend);
    }

    /**
     * Adds to this vector's y component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector4F addY(float addend) {
        return y(y + addend);
    }

    /**
     * Adds to this vector's z component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector4F addZ(float addend) {
        return z(z + addend);
    }

    /**
     * Adds to this vector's w component.
     * @param addend The other addend.
     * @return This vector.
     */
    public Vector4F addW(float addend) {
        return w(w + addend);
    }

    /**
     * Multiplies this vector by a scalar.
     * @param factor The multiplier.
     * @return This vector.
     */
    public Vector4F multiply(double factor) {
        return x((float)Math.floor(x * factor))
                .y((float)Math.floor(y * factor))
                .z((float)Math.floor(z * factor))
                .w((float)Math.floor(w * factor));
    }

}
