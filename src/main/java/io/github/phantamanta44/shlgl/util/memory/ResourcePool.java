package io.github.phantamanta44.shlgl.util.memory;

import io.github.phantamanta44.shlgl.util.collection.StackNode;

import java.util.function.Supplier;

/**
 * A shared resource pool implementation.
 * @author Evan Geng
 */
public class ResourcePool<T extends IShared> {

    /**
     * The factory method for creating resources.
     */
    private final Supplier<T> factory;

    /**
     * The pool of free resources.
     */
    private StackNode<Pooled<T>> free;

    /**
     * Creates a new resource pool with the given resource factory.
     * @param factory The factory providing new resources.
     */
    public ResourcePool(Supplier<T> factory) {
        this.factory = factory;
    }

    /**
     * Retrieves a pooled resource, or creates a new one if none are available.
     * @return A resource.
     */
    public Pooled<T> get() {
        if (free == null)
            return new Pooled<>(this, factory.get());
        Pooled<T> res = free.getValue();
        free = free.getParent();
        res.setFree(false);
        return res;
    }

    /**
     * Frees a pooled resource.
     * @param res The resource to free.
     */
    public void free(Pooled<T> res) {
        if (res != null && !res.isFree()) {
            free = free.extend(res);
            res.setFree(true);
            res.get().onFree();
        }
    }

}
