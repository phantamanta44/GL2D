package io.github.phantamanta44.shlgl.util.math;

import io.github.phantamanta44.shlgl.util.memory.IShared;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import io.github.phantamanta44.shlgl.util.memory.ResourcePool;

/**
 * A 4x4 matrix of float components.
 * @author Evan Geng
 */
public class Matrix4F implements IShared {

    /**
     * Creates a new matrix of all zeroes.
     * @return A {@link Pooled} instance containing the new matrix.
     */
    public static Pooled<Matrix4F> zeroes() {
        return pool.get();
    }

    /**
     * Creates a new identity matrix.
     * @return A {@link Pooled} instance containing the new matrix.
     */
    public static Pooled<Matrix4F> ident() {
        return of(
                1F, 0F, 0F, 0F,
                0F, 1F, 0F, 0F,
                0F, 0F, 1F, 0F,
                0F, 0F, 0F, 1F
        );
    }

    /**
     * Creates a new matrix with the given values. Unprovided values are assumed to be zero.
     * @param vals The values to initialize the matrix with, row-major.
     * @return A {@link Pooled} instance containing the new matrix.
     */
    public static Pooled<Matrix4F> of(float... vals) {
        Pooled<Matrix4F> pooled = zeroes();
        pooled.get().readArray(vals);
        return pooled;
    }

    /**
     * The shared resource pool governing Matrix4F instantiation.
     */
    private static final ResourcePool<Matrix4F> pool = new ResourcePool<>(Matrix4F::new);

    /**
     * The backing array containing the values in the matrix.
     */
    private float[] values;

    /**
     * Initializes a matrix.
     */
    private Matrix4F() {
        values = new float[16];
    }

    @Override
    public void onFree() {
        for (int i = 0; i < values.length; i++)
            values[i] = 0;
    }

    /**
     * Retrieves a value in the matrix.
     * @param row The row index.
     * @param col The column index.
     * @return The value stored in that cell.
     */
    public float get(int row, int col) {
        return values[row * 4 + col];
    }

    /**
     * Inserts a value into the matrix.
     * @param row The row index.
     * @param col The column index.
     * @param val The new value.
     */
    public void put(int row, int col, float val) {
        values[row * 4 + col] = val;
    }

    /**
     * Adds another matrix to this one.
     * @param addend The addend matrix.
     */
    public void add(Matrix4F addend) {
        float[] addends = addend.asArray();
        for (int i = 0; i < 16; i++)
            values[i] += addends[i];
    }

    /**
     * Multiplies all cells in this matrix by a factor.
     * @param factor The factor.
     */
    public void multiply(float factor) {
        for (int i = 0; i < 16; i++)
            values[i] *= factor;
    }

    /**
     * Returns this matrix as a row-major sequential array representation.
     * @return The array of values.
     */
    public float[] asArray() {
        return values;
    }

    /**
     * Sets the values of this matrix from an array.
     * @param vals The array of values.
     */
    private void readArray(float[] vals) {
        System.arraycopy(vals, 0, values, 0, vals.length);
        for (int i = vals.length; i < 16; i++)
            values[i] = 0;
    }

}
