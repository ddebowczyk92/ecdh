package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.ecdh.crypto.util.PointGenerator;
import pl.mkoi.ecdh.crypto.model.*;

/**
 * Created by DominikD on 2016-01-03.
 */
public class PointGeneratorTest {

    private final static Logger log = Logger.getLogger(PointGeneratorTest.class);

    @Test
    public void pointGeneratorTest() {

        for (int i = 0; i < 1000; i++) {

            long mParam = 6L;
            Polynomial irreducible = Polynomial.createIrreducible(mParam);
//        log.debug("irreducible " + irreducible.toBinaryString());

            GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
//        log.info("Generator storage size: " + generator.getGeneratorPowers().size());
            FiniteField finiteField = new FiniteField(generator, (int) mParam, irreducible);
            EllipticCurve curve = new EllipticCurve(generator.getGeneratorPower(2L), generator.getGeneratorPower(6L), finiteField, irreducible);
            Point point = PointGenerator.generatePoint(curve, generator);

//        log.info("point x: " + point.getX().toBinaryString() + " y: " + point.getY().toBinaryString());

            int pointOrder = PointGenerator.getPointOrder(curve, point);
            log.info("point order: " + i + " -  " + pointOrder);
        }
    }

    @Test
    public void pointGeneratorTest2() {
        Polynomial irreducible = Polynomial.createFromLong(19L);
        log.debug("irreducible " + irreducible.toBinaryString());
        GeneratorPolynomial gp = GeneratorPolynomial.findGenerator(irreducible);
        for (Long key : gp.getGeneratorPowers().keySet()) {
            log.debug("gen: " + gp.getGeneratorPowers().get(key).toBinaryString());
        }
    }
}
