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
    private float winWidth;

    /**
     * The window height.
     */
    private float winHeight;

    /**
     * The resolution width.
     */
    private float resWidth;

    /**
     * The resolution height.
     */
    private float resHeight;

    /**
     * Cached horizontal margin value.
     */
    private float halfMarginHor;

    /**
     * Cached vertical margin value.
     */
    private float halfMarginVer;

    /**
     * Cached horizontal scaling factor.
     */
    private float xFactor;

    /**
     * Cached vertical scaling factor.
     */
    private float yFactor;

    /**
     * Updates the cached width and height values.
     * @param winSize The new window size.
     * @param width The new resolution width.
     * @param height The new resolution height.
     */
    public void update(Vector2I winSize, int width, int height) {
        if (this.winWidth != winSize.x()
                || this.winHeight != winSize.y()
                || this.resWidth != width
                || this.resHeight != height) {
            this.winWidth = winSize.x();
            this.winHeight = winSize.y();
            this.resWidth = width;
            this.resHeight = height;
            calculateMargin();
        }
    }

    /**
     * Recalculates and caches margin values.
     */
    private void calculateMargin() {
        float kHeightWidth = resWidth / resHeight;
        float idealWinWidth = winHeight * kHeightWidth;
        if (Math.abs(winWidth - idealWinWidth) < 0.5F) {
            halfMarginHor = halfMarginVer = 0;
        } else if (winWidth > idealWinWidth) {
            halfMarginVer = 0;
            halfMarginHor = (winWidth - idealWinWidth) / 2;
        } else {
            halfMarginHor = 0;
            float idealHeight = winWidth * resHeight / resWidth;
            halfMarginVer = (winHeight - idealHeight) / 2;
        }
        xFactor = (winWidth - halfMarginHor * 2) / resWidth;
        yFactor = (winHeight - halfMarginVer * 2) / resHeight;
    }

    /**
     * Computes a device x-coordinate for the given render x-coordinate.
     * @param resX The x-coordinate.
     * @return The corresponding device coordinate.
     */
    public float computeX(float resX) {
        return (resX * xFactor + halfMarginHor) / winWidth;
    }

    /**
     * Computes a device y-coordinate for the given render y-coordinate.
     * @param resY The y-coordinate.
     * @return The corresponding device coordinate.
     */
    public float computeY(float resY) {
        return (resY * yFactor + halfMarginVer) / winHeight;
    }

}
