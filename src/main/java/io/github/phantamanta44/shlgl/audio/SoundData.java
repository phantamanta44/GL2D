package io.github.phantamanta44.shlgl.audio;

/**
 * Stores sound data and metadata.
 * @author Evan Geng
 */
public class SoundData {

    /**
     * The audio data.
     */
    public final byte[] data;

    /**
     * The OpenAL format of the stored audio.
     */
    public final int format;

    /**
     * The audio sample rate.
     */
    public final int sampleRate;

    /**
     * Creates a new SoundData instance for the given audio clip.
     * @param data The audio data.
     * @param format The OpenAL format.
     * @param sampleRate The sample rate.
     */
    SoundData(byte[] data, int format, float sampleRate) {
        this.data = data;
        this.format = format;
        this.sampleRate = (int)sampleRate;
    }

}
