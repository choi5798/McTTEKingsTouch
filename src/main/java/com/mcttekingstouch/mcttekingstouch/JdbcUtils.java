package com.mcttekingstouch.mcttekingstouch;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtils {
    public static UUID toUUID(byte[] id) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(id);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
