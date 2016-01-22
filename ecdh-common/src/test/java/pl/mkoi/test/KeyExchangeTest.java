package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.ecdh.crypto.model.*;
import pl.mkoi.ecdh.crypto.util.KeyGenerator;
import pl.mkoi.ecdh.crypto.util.PointGenerator;

public class KeyExchangeTest {

    private final static Logger log = Logger.getLogger(PointGenerator.class);

    @Test
    public void keyExchangeTest() {
        int m = 6;

        for (int i = 0; i < 10000; i++) {
            Polynomial irreducible = Polynomial.createIrreducible(m);
            GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
            FiniteField finiteField = new FiniteField(generator, m, irreducible);

            int randA = (int) (Math.random() * Math.floor(Math.pow(2, m)));
            int randB = (int) (Math.random() * Math.floor(Math.pow(2, m)));

            Polynomial a = Polynomial.createFromLong(randA);
            Polynomial b = Polynomial.createFromLong(randB);

            EllipticCurve curve = new EllipticCurve(a, b, finiteField, irreducible);

            Point generatePoint = PointGenerator.generatePoint(curve, generator);
            curve.setGeneratorPoint(generatePoint);

            //Diffie Hellman magic happens here

            KeyPair keyPairAlice = KeyGenerator.generateKeyPair(curve);
            KeyPair keyPairBob = KeyGenerator.generateKeyPair(curve);

            Point aliceReceivedPoint = keyPairBob.getPublicKey();
            Point bobReceivedPoint = keyPairAlice.getPublicKey();

            aliceReceivedPoint = aliceReceivedPoint.multiplyByScalarTimesGenerator(keyPairAlice.getPrivateKey(), curve);
            bobReceivedPoint = bobReceivedPoint.multiplyByScalarTimesGenerator(keyPairBob.getPrivateKey(), curve);

            log.debug(i + " AliceReceivedPoint " + aliceReceivedPoint + " BobReceivedPoint: " + bobReceivedPoint);

            Assert.assertTrue(aliceReceivedPoint.equals(bobReceivedPoint));
        }

        log.info("TEST PASSED SUCCESSFULLY");

    }

    @Test
    public void keyExchangeForLargeMTest(){
        int m = 15;
        long startTime = System.currentTimeMillis();
        Polynomial irreducible = Polynomial.createIrreducible(m);
        log.debug("finding irreducible polynomial: " + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
        log.debug("finding generator polynomial: " + (System.currentTimeMillis() - startTime));
        FiniteField finiteField = new FiniteField(generator, m, irreducible);

        int randA = (int) (Math.random() * Math.floor(Math.pow(2, m)));
        int randB = (int) (Math.random() * Math.floor(Math.pow(2, m)));

        Polynomial a = Polynomial.createFromLong(randA);
        Polynomial b = Polynomial.createFromLong(randB);

        EllipticCurve curve = new EllipticCurve(a, b, finiteField, irreducible);

        startTime = System.currentTimeMillis();
        Point generatorPoint = PointGenerator.generatePoint(curve, generator);
        log.debug("finding generator point: " + (System.currentTimeMillis() - startTime));
        curve.setGeneratorPoint(generatorPoint);
    }

}
