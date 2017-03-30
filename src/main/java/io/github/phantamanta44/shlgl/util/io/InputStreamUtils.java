package io.github.phantamanta44.shlgl.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for interacting with input streams.
 * @author Evan Geng
 */
public class InputStreamUtils {

    /**
     * Fully reads an input stream as textual data.
     * @param stream The input stream.
     * @return The text provided by the stream.
     */
    public static String readAsString(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        int codePoint;
        while ((codePoint = stream.read()) != -1)
            sb.append((char)codePoint);
        return sb.toString();
    }

    /**
     * Fully reads an input stream as a byte array.
     * @param stream The input stream.
     * @return The data provided by the stream.
     */
    public static byte[] readAsBytes(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int byteRead;
        while ((byteRead = stream.read()) != -1)
            buffer.write(byteRead);
        return buffer.toByteArray();
    }

}
