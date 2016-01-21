package pl.mkoi.ecdh.crypto.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import pl.mkoi.ecdh.crypto.model.Point;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class AesCipher {

    public final static int KEY_LENGTH_BYTES = 128 / 8;

    private final static String CIPHER_SUITE = "AES/ECB/NoPadding";

    private static final Logger log = Logger.getLogger(AesCipher.class);

    public static String encrypt(Point point, String value) {
        try {

            byte[] key = AesCipher.calculateKey(point);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance(CIPHER_SUITE);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(paddingFix(value).getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(Point point, String encrypted) {
        try {
            byte[] key = AesCipher.calculateKey(point);

            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_SUITE);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

//            String decordedValue = new BASE64Decoder().decodeBuffer(encrypted).toString().trim();
            byte[] bytesEncoded = Base64.decodeBase64(encrypted);
            byte[] original = cipher.doFinal(bytesEncoded);

            return new String(original).trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static byte[] calculateKey(Point point) {

        byte[] key = new byte[KEY_LENGTH_BYTES];
        byte[] pointsAdded = ArrayUtils.addAll(point.getX().getDegrees().toByteArray(), point.getY().getDegrees().toByteArray());

        for (int i = 0; i < key.length; i++) {
            key[i] = pointsAdded[i % pointsAdded.length];
        }
        System.out.println(key.length);
        return key;
    }

    private static String paddingFix(String value) {
        int length = value.getBytes().length % 16;
        if (length != 0) {
            int n = 16 - length;
            char[] spaces = new char[n];
            Arrays.fill(spaces, ' ');

            return value + new String(spaces);
        } else return value;
    }


}
