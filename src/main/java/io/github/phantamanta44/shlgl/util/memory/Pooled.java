package io.github.phantamanta44.shlgl.util.memory;

/**
 * A container for a pooled resource.
 * @author Evan Geng
 */
public class Pooled<T extends IShared> implements AutoCloseable {

    /**
     * The parent resource pool.
     */
    private final ResourcePool<T> parent;

    /**
     * The pooled object.
     */
    private final T value;

    /**
     * Whether this resource is free or not.
     */
    private boolean free = false;

    /**
     * Creates a new instance containing the given object.
     * @param parent The parent resource pool.
     * @param value The pooled object.
     */
    Pooled(ResourcePool<T> parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    /**
     * Sets the freeness state of this resouce.
     * @param free Whether the resource should be free or not.
     */
    void setFree(boolean free) {
        this.free = free;
    }

    /**
     * Retrieves the pooled object.
     * @return The object.
     */
    public T get() {
        return value;
    }

    /**
     * Frees the object contained by this instance.
     */
    public void free() {
        parent.free(this);
    }

    /**
     * Checks if this resource is free.
     * @return Whether this resource is free or not.
     */
    public boolean isFree() {
        return free;
    }

    @Override
    public void close() {
        free();
    }

}
