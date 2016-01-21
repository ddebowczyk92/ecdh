package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.ecdh.crypto.util.SignatureKeyPairGenerator;
import pl.mkoi.ecdh.crypto.util.SignatureUtil;

import java.security.KeyPair;
import java.security.PublicKey;

/**
 * Created by DominikD on 2016-01-21.
 */
public class SignatureTest {

    private static final Logger log = Logger.getLogger(SignatureTest.class);

    @Test
    public void testPublicKeyConversion() {
        KeyPair keyPair = SignatureKeyPairGenerator.generateSignatureKeyPair();
        PublicKey publicKey1 = keyPair.getPublic();
        String encodedPublicKey = SignatureKeyPairGenerator.getPublicKeyEncoded(publicKey1);
        PublicKey publicKey2 = SignatureKeyPairGenerator.decodePublicKey(encodedPublicKey);
        Assert.assertEquals(publicKey1, publicKey2);
    }

    @Test
    public void testMessageSigning() {
        KeyPair keyPair = SignatureKeyPairGenerator.generateSignatureKeyPair();
        PublicKey publicKey1 = keyPair.getPublic();
        String encodedPublicKey = SignatureKeyPairGenerator.getPublicKeyEncoded(publicKey1);
        PublicKey publicKey2 = SignatureKeyPairGenerator.decodePublicKey(encodedPublicKey);
        String messageToSign = "xxxxsadfccacXZCZXGVNBXVBćććŚDEQR454LASDAł";
        String signature = SignatureUtil.sign(keyPair.getPrivate(), messageToSign);
        Assert.assertTrue(SignatureUtil.verifySignature(messageToSign, signature, publicKey2));
        Assert.assertFalse(SignatureUtil.verifySignature("xzxxzx", signature, publicKey2));
    }

}
