package io.github.phantamanta44.shlgl.util.math;

import java.util.Arrays;

/**
 * Utility class for math-related things.
 * @author Evan Geng
 */
public class MathUtils {

    /**
     * Calculates the magnitude of an n-dimensional vector.
     * @param dimensions The various components of the vector.
     * @return The calculated magnitude.
     */
    public static double magnitude(double... dimensions) {
        return Math.sqrt(Arrays.stream(dimensions).map(d -> d * d).sum());
    }

}
