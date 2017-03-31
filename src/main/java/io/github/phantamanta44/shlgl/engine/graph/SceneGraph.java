package io.github.phantamanta44.shlgl.engine.graph;

import io.github.phantamanta44.shlgl.graphics.render.RenderBuffer;

/**
 * A hierarchical representation of the elements in a scene.
 * @author Evan Geng
 */
public class SceneGraph extends GraphNode {

    /**
     * Creates the root graph node with the given coordinates.
     * @param rootX The root x coordinate.
     * @param rootY The root y coordinate.
     */
    public SceneGraph(int rootX, int rootY) {
        super(null, rootX, rootY);
    }

    /**
     * Ticks all the nodes in this scene graph.
     */
    @Override
    public void tick() {
        children.forEach(SceneGraph::tick);
    }

    /**
     * Recursively ticks a node and all its children.
     * @param node The node to tick.
     */
    private static void tick(GraphNode node) {
        node.tick();
        if (node.isAlive())
            node.children.forEach(SceneGraph::tick);
        else
            kill(node);
    }

    /**
     * Recursively kills a node and all its children.
     * @param node The node to kill.
     */
    private static void kill(GraphNode node) {
        node.kill();
        node.parent.children.remove(node);
        node.children.forEach(SceneGraph::kill);
    }

    /**
     * Renders all the nodes in this scene graph.
     * @param buf The render buffer to render with.
     */
    @Override
    public void render(RenderBuffer buf) {
        children.forEach(child -> render(buf, child));
    }

    /**
     * Recursively renders a node and all its children.
     * @param buf The render buffer to render with.
     * @param node
     */
    private static void render(RenderBuffer buf, GraphNode node) {
        buf.pushMatrix();
        buf.translate(node.x, node.y);
        node.render(buf);
        node.children.forEach(child -> render(buf, child));
        buf.popMatrix();
    }

}
