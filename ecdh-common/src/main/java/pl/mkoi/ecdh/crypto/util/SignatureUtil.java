package pl.mkoi.ecdh.crypto.util;

import com.google.common.io.BaseEncoding;
import org.apache.log4j.Logger;

import java.security.*;

/**
 * Util class for signing and checking signature
 */
public class SignatureUtil {

    private static final Logger log = Logger.getLogger(SignatureUtil.class);

    private static final String ALGORITHM = "SHA1withDSA";
    private static final String PROVIDER = "SUN";

    public static String sign(PrivateKey key, String message) {
        try {
            Signature dsa = Signature.getInstance(ALGORITHM, PROVIDER);
            dsa.initSign(key);
            dsa.update(message.getBytes());
            byte[] signature = dsa.sign();
            String encoded = BaseEncoding.base64().encode(signature);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifySignature(String data, String encodedSignature, PublicKey publicKey) {
        try {
            Signature dsa = Signature.getInstance(ALGORITHM, PROVIDER);
            byte[] signature = BaseEncoding.base64().decode(encodedSignature);
            dsa.initVerify(publicKey);
            dsa.update(data.getBytes());
            return dsa.verify(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }
}
