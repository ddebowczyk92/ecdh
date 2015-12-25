package pl.mkoi.util;

import pl.mkoi.util.model.EllipticCurve;
import pl.mkoi.util.model.KeyPair;
import pl.mkoi.util.model.Point;
import pl.mkoi.util.model.Polynomial;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

    public static KeyPair generateKeyPair(EllipticCurve curve, Polynomial polynomial, int bitNumber) {

        KeyPair pair = new KeyPair();
        pair.setPrivateKey(new BigInteger(bitNumber, new SecureRandom()));

        Point point = curve.getGeneratorPoint();
        point.multiplyByScalar(pair.getPrivateKey(), curve);

        pair.setPublicKey(point);

        return pair;
    }
}
