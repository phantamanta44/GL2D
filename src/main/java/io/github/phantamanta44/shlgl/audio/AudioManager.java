package io.github.phantamanta44.shlgl.audio;

import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Handles audio loading and playback.
 * @author Evan Geng
 */
public class AudioManager {

    /**
     * Initializes audio device and buffers.
     */
    public static void initAudio() {
        long dev = ALC10.alcOpenDevice((ByteBuffer)null);
        ALCCapabilities devCaps = ALC.createCapabilities(dev);
        if (!devCaps.OpenALC10)
            throw new IllegalArgumentException("Unsupported audio device!");
        long ctx;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer attribs = stack.mallocInt(16);
            attribs.put(ALC10.ALC_REFRESH);
            attribs.put(60);
            attribs.put(ALC10.ALC_SYNC);
            attribs.put(ALC10.ALC_FALSE);
            attribs.put(0);
            attribs.flip();
            ctx = ALC10.alcCreateContext(dev, attribs);
        }
        if (!ALC10.alcMakeContextCurrent(ctx))
            throw new IllegalArgumentException("Failed to initialize ALC context!");
        AL.createCapabilities(devCaps);
        // TODO Initialize audio buffers
    }

    // TODO Finish implementation

}
