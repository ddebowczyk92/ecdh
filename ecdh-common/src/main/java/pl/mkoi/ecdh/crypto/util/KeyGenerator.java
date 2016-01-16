package pl.mkoi.ecdh.crypto.util;

import org.apache.log4j.Logger;
import pl.mkoi.ecdh.crypto.model.EllipticCurve;
import pl.mkoi.ecdh.crypto.model.KeyPair;
import pl.mkoi.ecdh.crypto.model.Point;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

    private final static Logger log = Logger.getLogger(KeyGenerator.class);

    public static KeyPair generateKeyPair(EllipticCurve curve) {

        KeyPair pair = new KeyPair();
        pair.setPrivateKey(new BigInteger((int) (Math.log(curve.getM()) / Math.log(2)), new SecureRandom()));

//        log.info("Private key generated " + pair.getPrivateKey());

        Point point = curve.getGeneratorPoint();
        point = point.multiplyByScalar(pair.getPrivateKey(), curve);
        pair.setPublicKey(point);

        return pair;
    }
}
