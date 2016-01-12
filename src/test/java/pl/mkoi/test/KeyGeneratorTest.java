package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.util.KeyGenerator;
import pl.mkoi.util.PointGenerator;
import pl.mkoi.util.model.*;

public class KeyGeneratorTest {

    private final static Logger log = Logger.getLogger(KeyGeneratorTest.class);

    @Test
    public void generator() {
        long start = System.currentTimeMillis();
        int m = 4;
        Polynomial irreducible = Polynomial.createIrreducible(m);

        log.info("Irreducible Polynomial generated");

        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);

        log.info("Generator Polynomial generated");

        FiniteField finiteField = new FiniteField(generator, m, irreducible);

        log.info("FiniteField created");

        int randA = (int) (Math.random() * Math.floor(Math.pow(2, m)));
        int randB = (int) (Math.random() * Math.floor(Math.pow(2, m)));

        Polynomial a = Polynomial.createFromLong(randA);
        Polynomial b = Polynomial.createFromLong(randB);

        log.info("Points multiplication started for curve A = " + a + " B = " + b);

        EllipticCurve curve = new EllipticCurve(a, b, finiteField, null, irreducible);

        Point generatePoint = PointGenerator.generatePoint(curve, generator);
        curve.setGeneratorPoint(generatePoint);

        log.info("Points multiplication finished ");

        KeyPair keyPair = KeyGenerator.generateKeyPair(curve);

        log.info((System.currentTimeMillis() - start) / 1000);

        log.info("Finished");
    }
}
