package eu.kraftics.krafticslib.utils;

import java.nio.ByteBuffer;

public final class KraftUtils {

    public static void putString(ByteBuffer buffer, String s) {
        buffer.put(s.getBytes());
        buffer.putChar('\0');
    }

    public static String getString(ByteBuffer buffer) {
        StringBuilder builder = new StringBuilder();

        byte b;
        while (buffer.position() < buffer.limit() && (b = buffer.get()) != '\0') {
            builder.append(b);
        }

        return builder.toString();
    }
}
