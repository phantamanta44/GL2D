package io.github.phantamanta44.shlgl.util.memory;

/**
 * An object whose instantiation is backed by a shared resource pool.
 * @author Evan Geng
 */
public interface IShared {

    /**
     * Called when the resource is freed.
     */
    default void onFree() {
        // NO-OP
    }

}
