package io.github.phantamanta44.shlgl.util.io;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Utility class for dealing with resources on the classpath.
 * @author Evan Geng
 */
public class ResourceUtils {

    /**
     * Retrieves a resource using the current context classloader.
     * @param resource The resource's identifier.
     * @return An input stream for the resource.
     */
    public static InputStream getStream(String resource) {
        return new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
    }

}
