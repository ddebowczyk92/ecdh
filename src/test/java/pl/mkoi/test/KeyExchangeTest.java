package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.KeyGenerator;
import pl.mkoi.util.PointGenerator;
import pl.mkoi.util.model.*;

public class KeyExchangeTest {

    private final static Logger log = Logger.getLogger(PointGenerator.class);

    @Test
    public void keyExchangeTest() {
        int m = 4;

        for (int i = 0; i < 10000; i++) {
            Polynomial irreducible = Polynomial.createIrreducible(m);
            GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
            FiniteField finiteField = new FiniteField(generator, m, irreducible);

            int randA = (int) (Math.random() * Math.floor(Math.pow(2, m)));
            int randB = (int) (Math.random() * Math.floor(Math.pow(2, m)));

            Polynomial a = Polynomial.createFromLong(randA);
            Polynomial b = Polynomial.createFromLong(randB);

            EllipticCurve curve = new EllipticCurve(a, b, finiteField, null, irreducible);

            Point generatePoint = PointGenerator.generatePoint(curve, generator);
            curve.setGeneratorPoint(generatePoint);

            //Diffie Hellman magic happens here

            KeyPair keyPairAlice = KeyGenerator.generateKeyPair(curve);
            KeyPair keyPairBob = KeyGenerator.generateKeyPair(curve);

            Point aliceReceivedPoint = new Point(keyPairBob.getPublicKey());
            Point bobReceivedPoint = new Point(keyPairAlice.getPublicKey());

            aliceReceivedPoint = aliceReceivedPoint.multiplyByScalarTimesGenerator(keyPairAlice.getPrivateKey(), curve);
            bobReceivedPoint = bobReceivedPoint.multiplyByScalarTimesGenerator(keyPairBob.getPrivateKey(), curve);

            Assert.assertTrue(aliceReceivedPoint.equals(bobReceivedPoint));
        }

        log.info("TEST PASSED SUCCESSFULLY");

    }

}
