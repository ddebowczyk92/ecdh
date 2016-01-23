package pl.mkoi.ecdh.crypto.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Util class for generating sha1 signature key pair
 */
public class SignatureKeyPairGenerator {

    private static final String ALGORITHM = "DSA";
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String PROVIDER = "SUN";
    private static final int KEY_LENGTH = 1024;

    /**
     * generate signature key pair
     *
     * @return generated keypair
     */
    public static KeyPair generateSignatureKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
            SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM, "SUN");
            keyGen.initialize(KEY_LENGTH, random);
            KeyPair pair = keyGen.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPublicKeyEncoded(PublicKey key) {
        byte[] publicKeyBytes = key.getEncoded();
        return new BASE64Encoder().encode(publicKeyBytes);
    }

    public static PublicKey decodePublicKey(String keyString) {
        byte[] sigBytes2;
        try {
            sigBytes2 = new BASE64Decoder().decodeBuffer(keyString);
            // Convert the public key bytes into a PublicKey object
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes2);
            KeyFactory keyFact = KeyFactory.getInstance(ALGORITHM, PROVIDER);
            return keyFact.generatePublic(x509KeySpec);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
