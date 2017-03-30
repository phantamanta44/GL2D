package io.github.phantamanta44.shlgl.audio;

import io.github.phantamanta44.shlgl.util.memory.IShared;
import org.lwjgl.openal.AL10;

/**
 * A paired audio buffer and source.
 * @author Evan Geng
 */
public class AudioBuffer implements IShared {

    /**
     * The audio buffer's handle.
     */
    final int buf;

    /**
     * The audio source's handle.
     */
    final int src;

    /**
     * Creates and initializes a buffer-source pair.
     */
    AudioBuffer() {
        buf = AL10.alGenBuffers();
        src = AL10.alGenSources();
        AL10.alSourcei(src, AL10.AL_BUFFER, buf);
        AL10.alSource3f(src, AL10.AL_POSITION, 0F, 0F, 0F);
        AL10.alSource3f(src, AL10.AL_VELOCITY, 0F, 0F, 0F);
    }

    /**
     * Begins playback from this buffer.
     */
    public void play() {
        AL10.alSourcePlay(src);
    }

    /**
     * Stops playback from this buffer and frees it.
     */
    public void stop() {
        AL10.alSourceStop(src);
    }

    /**
     * Buffers audio data to be played.
     * @param sound The sound to buffer.
     */
    public void buffer(SoundData sound) {
        // TODO Implement
    }

}
