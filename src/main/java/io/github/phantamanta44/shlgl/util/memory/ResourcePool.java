package io.github.phantamanta44.shlgl.util.memory;

import io.github.phantamanta44.shlgl.util.collection.StackNode;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
     * Collection of all Pooled instances created by this pool.
     */
    private final Collection<Pooled<T>> created;

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
        this.created = new LinkedList<>();
    }

    /**
     * Retrieves a pooled resource, or creates a new one if none are available.
     * @return A resource.
     */
    public Pooled<T> get() {
        if (free == null)
            return generate();
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
            if (free != null)
                free = free.extend(res);
            else
                free = new StackNode<>(res);
            res.setFree(true);
            res.get().onFree();
        }
    }

    /**
     * Constructs a new instance of the resource.
     * @return The newly-created Pooled object.
     */
    private Pooled<T> generate() {
        Pooled<T> newInstance = new Pooled<>(this, factory.get());
        created.add(newInstance);
        return newInstance;
    }

    /**
     * Generates a stream of all Pooled instances created by this pool.
     * @returns The stream.
     */
    public Stream<Pooled<T>> stream() {
        return created.stream();
    }

    /**
     * Iterates through all Pooled instances created by this pool.
     * @param function The visitor function to call on each instance.
     */
    public void forEach(Consumer<Pooled<T>> function) {
        created.forEach(function);
    }

}
