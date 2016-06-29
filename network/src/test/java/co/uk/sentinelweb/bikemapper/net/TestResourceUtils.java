package co.uk.sentinelweb.bikemapper.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;

/**
 * Other utilities for testing.
 * Created by robert on 08/01/16.
 */
public class TestResourceUtils {

    public static Gson getGson() {
        final Gson gson = new GsonBuilder()
                .create();
        return gson;
    }

    /**
     * Gets the class/resource combination as a string.
     *
     * @param clazz
     * @param resourceName
     * @return string
     */
    public static String getString(final Class clazz, final String resourceName) {

        try {
            final InputStream io = clazz.getResourceAsStream(resourceName);
            Assert.assertNotNull("File " + resourceName
                    + " not found in the classpath of the class " + clazz, io);
            return toString(io);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets an input stream from a class/resource combination.
     *
     * @param clazz
     * @param resourceName
     * @return inputstream
     */
    public static InputStream getInputStream(final Class clazz, final String resourceName) {

        try {
            final InputStream io = clazz.getResourceAsStream(resourceName);
            Assert.assertNotNull("File " + resourceName
                    + " not found in the classpath of the class " + clazz, io);
            return io;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the inputstream contents as a string.
     *
     * @param stream
     * @return
     */
    public static String toString(final InputStream stream) {
        final StringBuilder stringBuilder = new StringBuilder();
        int b = 0;
        try {
            b = stream.read();
            while (b != -1) {
                stringBuilder.append((char) b);
                b = stream.read();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

}
