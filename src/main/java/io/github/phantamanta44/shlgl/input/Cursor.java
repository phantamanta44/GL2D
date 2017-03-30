package io.github.phantamanta44.shlgl.input;

/**
 * Represents the mouse cursor and stores position and button data.
 * @author Evan Geng
 */
public class Cursor {

    /**
     * The left button's state.
     */
    private boolean btnLeft;

    /**
     * The right button's state.
     */
    private boolean btnRight;

    /**
     * The scroll wheel button's state.
     */
    private boolean btnMiddle;

    /**
     * The cursor's x coordinate.
     */
    private int posX;

    /**
     * The cursor's y coordinate.
     */
    private int posY;

    /**
     * Instantiates the cursor.
     */
    public Cursor() {
        this.btnLeft = this.btnRight = this.btnMiddle = false;
        this.posX = this.posY = 0;
    }

    /**
     * Updates the mouse button state.
     * @param button The button in question.
     * @param action The action performed on the button.
     */
    public void updateButtonState(int button, int action) {
        // TODO Implement
    }

    /**
     * Updates the cursor position.
     * @param posX The x coordinate.
     * @param posY The y coordinate.
     */
    public void updatePos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    // TODO Scroll wheel

    // TODO Public accessors

}
