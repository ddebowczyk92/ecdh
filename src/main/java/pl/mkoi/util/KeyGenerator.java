package pl.mkoi.util;

import org.apache.log4j.Logger;
import pl.mkoi.util.model.EllipticCurve;
import pl.mkoi.util.model.KeyPair;
import pl.mkoi.util.model.Point;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

    private final static Logger log = Logger.getLogger(KeyGenerator.class);

    public static KeyPair generateKeyPair(EllipticCurve curve) {

        KeyPair pair = new KeyPair();
        pair.setPrivateKey(new BigInteger(curve.getM(), new SecureRandom()));

        log.info("Private key generated " + pair.getPrivateKey());

        Point point = curve.getGeneratorPoint();
        point.multiplyByScalar(pair.getPrivateKey(), curve);
        pair.setPublicKey(point);

        return pair;
    }
}
