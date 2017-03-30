package io.github.phantamanta44.shlgl.graphics.texture;

/**
 * POJO that provides texture metadata.
 * @author Evan Geng
 */
public class TextureInfo {

    /**
     * The texture's ID.
     */
    public final int id;

    /**
     * The texture's width.
     */
    public final int w;

    /**
     * The texture's height.
     */
    public final int h;

    /**
     * Constructs a TextureInfo object for the given texture.
     * @param id The texture's ID.
     * @param w The texture's width.
     * @param h The texture's height.
     */
    TextureInfo(int id, int w, int h) {
        this.id = id;
        this.w = w;
        this.h = h;
    }

}
