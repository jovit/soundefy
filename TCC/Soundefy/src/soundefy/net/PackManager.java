package soundefy.net;

public class PackManager {

    public static int unpack(byte[] bytes, int c) {
        return bytes[c] << 24 | (bytes[c + 1] & 0xFF) << 16 | (bytes[c + 2] & 0xFF) << 8 | (bytes[c + 3] & 0xFF);
    }
    public static void pack(int value, byte[] b, int c) {
        b[c] = (byte) (value >>> 24);
        b[c + 1] = (byte) (value >>> 16);
        b[c + 2] = (byte) (value >>> 8);
        b[c + 3] = (byte) (value);
    }
}
