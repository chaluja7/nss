package cz.cvut.nss.security;

import java.security.MessageDigest;

/**
 * Created by jakubchalupa on 24.11.14.
 *
 * Helper class for hash comutation.
 */
public class HashUtils {

    private static String convertToHex(byte[] data) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;

            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    builder.append((char) ('0' + halfbyte));
                } else {
                    builder.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;

            } while (two_halfs++ < 1);
        }

        return builder.toString();
    }

    public static String computeHash(String s) {

        if(s == null) {
            return s;
        }

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(s.getBytes("utf8"), 0, s.length());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        byte[] sha256hash = md.digest();

        return convertToHex(sha256hash);
    }

}
