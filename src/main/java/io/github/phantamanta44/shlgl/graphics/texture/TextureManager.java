package io.github.phantamanta44.shlgl.graphics.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads and stores texture data.
 * @author Evan Geng
 */
public class TextureManager {

    /**
     * Map of texture paths to texture IDs.
     */
    private static final Map<String, Integer> idByPath = new ConcurrentHashMap<>();

    /**
     * Map of texture IDs to texture info objects.
     */
    private static final Map<Integer, TextureInfo> infoById = new ConcurrentHashMap<>();

    /**
     * The currently bound texture.
     */
    private static TextureInfo bound;

    /**
     * Retrieves the texture ID for a given path, loading the texture if it isn't already cached.
     * @param path The path to the texture.
     * @return The texture ID.
     */
    public static int getTextureId(String path) {
        if (idByPath.containsKey(path))
            return idByPath.get(path);
        return load(path);
    }

    /**
     * Loads texture data from the given path and caches it.
     * @param path The path to the texture file.
     * @return The ID of the newly loaded texture.
     */
    private static int load(String path) {
        try (InputStream stream = TextureManager.class.getClassLoader().getResourceAsStream(path)) {
            PNGDecoder decoder = new PNGDecoder(stream);
            int w = decoder.getWidth(), h = decoder.getHeight();
            ByteBuffer data = BufferUtils.createByteBuffer(4 * w * h);
            decoder.decodeFlipped(data, 4 * w, PNGDecoder.Format.RGBA);
            data.flip();
            int texId = bufferTexture(w, h, data, GL11.GL_RGBA);
            idByPath.put(path, texId);
            infoById.put(texId, new TextureInfo(texId, w, h));
            return texId;
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Initializes OpenGL texture data for a texture.
     * @param w The texture's width.
     * @param h The texture's height.
     * @param data The texture data.
     * @param format The image format.
     * @return The texture's ID.
     */
    private static int bufferTexture(int w, int h, ByteBuffer data, int format) {
        int texId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format, w, h, 0, format, GL11.GL_UNSIGNED_BYTE, data);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        return texId;
    }

    /**
     * Retrieves the TextureInfo instance for a texture.
     * @param texId The ID to look up.
     * @return The info, if it exists.
     */
    public static TextureInfo getTextureInfo(int texId) {
        return infoById.get(texId);
    }

    /**
     * Retrieves the TextureInfo instance for a texture.
     * @param path The path to the texture.
     * @return The info, if it exists.
     */
    public static TextureInfo getTextureInfo(String path) {
        return getTextureInfo(getTextureId(path));
    }

    /**
     * Retrieves the currently bound texture info.
     * @return The texture.
     */
    public static TextureInfo getBound() {
        return bound;
    }

    /**
     * Binds a texture by info. The preferred way to bind textures.
     * @param info The texture to bind.
     */
    public static void bind(TextureInfo info) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, info.id);
        bound = info;
    }

    /**
     * Binds a texture by ID. Use {@link #bind(TextureInfo)} instead if possible.
     * @param id The texture ID.
     */
    public static void bind(int id) {
        bind(getTextureInfo(id));
    }

    /**
     * Binds a texture by path. Use {@link #bind(TextureInfo)} instead if possible.
     * @param path The path to the texture.
     */
    public static void bind(String path) {
        bind(getTextureId(path));
    }

}