package io.github.phantamanta44.shlgl.audio;

import io.github.phantamanta44.shlgl.util.io.InputStreamUtils;
import io.github.phantamanta44.shlgl.util.io.ResourceUtils;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import io.github.phantamanta44.shlgl.util.memory.ResourcePool;
import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryStack;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Handles audio loading and playback.
 * @author Evan Geng
 */
public class AudioManager {

    /**
     * The pool of audio buffers.
     */
    private static ResourcePool<AudioBuffer> bufPool = new ResourcePool<>(AudioBuffer::new);

    /**
     * A collection of all created buffers.
     */
    private static Set<AudioBuffer> createdBufs = new HashSet<>();

    /**
     * The cache of all loaded sounds.
     */
    private static Map<String, SoundData> soundCache = new HashMap<>();

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

        AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
        AL10.alListener3f(AL10.AL_ORIENTATION, 0f, 0f, -1f);

        bufPool.get().free();
    }

    /**
     * Cleans up OpenAL.
     */
    public static void destruct() {
        AL10.alDeleteBuffers(createdBufs.stream().mapToInt(buf -> buf.buf).toArray());
        AL10.alDeleteSources(createdBufs.stream().mapToInt(buf -> buf.src).toArray());
        ALC.destroy();
    }

    /**
     * Retrieves the sound data associated with a path, loading it if it's not cached.
     * @param path The path to the audio file.
     * @return The audio data.
     */
    public static SoundData getSound(String path) {
        return soundCache.computeIfAbsent(path, AudioManager::load);
    }

    /**
     * Loads raw audio data from a path.
     * @param path The audio file's path.
     * @return The audio data.
     */
    private static SoundData load(String path) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(ResourceUtils.getStream(path))) {
            AudioFormat fmt = ais.getFormat();
            int chanFmt = -1;
            switch (fmt.getChannels()) {
                case 1:
                    switch (fmt.getSampleSizeInBits()) {
                        case 8:
                            chanFmt = AL10.AL_FORMAT_MONO8;
                            break;
                        case 16:
                            chanFmt = AL10.AL_FORMAT_MONO16;
                            break;
                    }
                    break;
                case 2:
                    switch (fmt.getSampleSizeInBits()) {
                        case 8:
                            chanFmt = AL10.AL_FORMAT_STEREO8;
                            break;
                        case 16:
                            chanFmt = AL10.AL_FORMAT_STEREO16;
                            break;
                    }
                    break;
            }
            if (chanFmt == -1)
                throw new IllegalStateException("Invalid audio format!");
            return new SoundData(
                    InputStreamUtils.readAsBytes(ais),
                    chanFmt,
                    fmt.getSampleRate()
            );
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("Unsupported audio format!", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays a sound on a free buffer.
     * @param sound The sound to play.
     * @return The buffer the sound is playing from.
     */
    public static AudioBuffer play(SoundData sound) {
        Pooled<AudioBuffer> buf = bufPool.get();
        createdBufs.add(buf.get());
        buf.get().buffer(sound);
        buf.get().play();
        return buf.get();
    }

    /**
     * Plays a sound on a free buffer. It's preferred to use {@link #play(SoundData)} if possible.
     * @param path The path to the sound.
     * @return The buffer the sound is playing from.
     */
    public static AudioBuffer play(String path) {
        return play(getSound(path));
    }

    /**
     * Updates the sound buffers, freeing any that are unused. For internal use only!
     */
    public static void tick() {
        // TODO Free unused buffers
    }

}
