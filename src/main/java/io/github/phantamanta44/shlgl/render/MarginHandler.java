package io.github.phantamanta44.shlgl.render;

import io.github.phantamanta44.shlgl.util.math.Vector2I;

/**
 * Handles margin creation to conserve resolution upon window size change.
 * @author Evan Geng
 */
public class MarginHandler {

    /**
     * The window width.
     */
    private int winWidth;

    /**
     * The window height.
     */
    private int winHeight;

    /**
     * The resolution width.
     */
    private int resWidth;

    /**
     * The resolution height.
     */
    private int resHeight;

    /**
     * Cached horizontal margin value.
     */
    private int halfMarginHor;

    /**
     * Cached vertical margin value.
     */
    private int halfMarginVer;

    /**
     * Updates the cached width and height values.
     * @param winSize The new window size.
     * @param width The new resolution width.
     * @param height The new resolution height.
     */
    public void update(Vector2I winSize, int width, int height) {
        this.winWidth = winSize.x();
        this.winHeight = winSize.y();
        this.resWidth = width;
        this.resHeight = height;
        calculateMargin(); // TODO Make this call lazy
    }

    /**
     * Recalculates and caches margin values.
     */
    private void calculateMargin() {
        float kWidthHeight = (float)resHeight / (float)resWidth;
        int idealWinHeight = (int)Math.floor(winWidth * kWidthHeight); // TODO Probably do this by width instead for speed
        if (winHeight == idealWinHeight) {
            halfMarginHor = halfMarginVer = 0;
        } else if (winHeight < idealWinHeight) {
            halfMarginVer = 0;
            // TODO Pad horizontally
        } else {
            halfMarginHor = 0;
            halfMarginVer = (int)Math.floor(winHeight - idealWinHeight) / 2;
        }
    }

    // TODO Implement offsetting/scaling

}
