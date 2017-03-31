package io.github.phantamanta44.shlgl.engine.graph;

import io.github.phantamanta44.shlgl.graphics.render.RenderBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in a scene graph. Propagates transformations to children.
 * @author Evan Geng.
 */
public abstract class GraphNode {

    /**
     * This node's parent.
     */
    protected GraphNode parent;

    /**
     * The game entity's x coordinate.
     */
    protected float x;

    /**
     * The game entity's y coordinate.
     */
    protected float y;

    /**
     * This graph node's children.
     */
    protected List<GraphNode> children;

    /**
     * The node's alive state.
     */
    private boolean alive = true;
    
    /**
     * Creates a graph node at the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected GraphNode(GraphNode parent, float x, float y) {
        this.parent = parent;
        setPosition(x, y);
        this.children = new ArrayList<>();
    }

    /**
     * Sets the game entity's position.
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the game entity's x coordinate.
     * @return The x coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the game entity's y coordinate.
     * @return The y coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the parent node.
     * @return The parent.
     */
    public GraphNode getParent() {
        return parent;
    }

    /**
     * Get the set of this node's children.
     * @return The children.
     */
    public List<GraphNode> getChildren() {
        return children;
    }

    /**
     * Appends a child node to this node.
     * @param child The new child node.
     */
    public void addChild(GraphNode child) {
        children.add(child);
    }

    /**
     * Checks whether this node is still alive. If the node dies, all its children also die and are removed from the graph.
     * @return Whether this node is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kills this node and all its children, removing them from the graph.
     */
    public void kill() {
        alive = false;
    }

    /**
     * Updates the graph node's state.
     */
    public abstract void tick();

    /**
     * Renders the game entity represented by this graph node.
     * @param buf The render buffer to render with.
     */
    public abstract void render(RenderBuffer buf);
    
}
