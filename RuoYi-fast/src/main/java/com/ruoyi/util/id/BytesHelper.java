package com.ruoyi.util.id;

public final class BytesHelper
{
    private BytesHelper() {
    }
    
    public static int toInt(final byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; ++i) {
            result = (result << 8) + 128 + bytes[i];
        }
        return result;
    }
}
