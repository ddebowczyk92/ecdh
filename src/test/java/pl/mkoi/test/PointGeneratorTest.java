package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.util.PointGenerator;
import pl.mkoi.util.model.*;

/**
 * Created by DominikD on 2016-01-03.
 */
public class PointGeneratorTest {

    private final static Logger log = Logger.getLogger(PointGeneratorTest.class);

    @Test
    public void pointGeneratorTest() {
        long mParam = 5L;
        Polynomial irreducible = Polynomial.createIrreducible(mParam);

        log.debug("irreducible " + irreducible.toBinaryString());

        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);

        log.info("Generator storage size: " + generator.getGeneratorPowers().size());
//
        FiniteField finiteField = new FiniteField(generator, (int) mParam);

        EllipticCurve curve = new EllipticCurve(new FiniteField.Element(2L, generator.getGeneratorPower(2L)), new FiniteField.Element(6L, generator.getGeneratorPower(6L)), finiteField, null, irreducible);

        Point point = PointGenerator.generatePoint(curve, generator);

        log.info("point x: " + point.getX().getPolynomial().toBinaryString() + " y: " + point.getY().getPolynomial().toBinaryString());

        int pointOrder = PointGenerator.getPointOrder(curve, point);
        log.info("point order: " + pointOrder);
    }

    @Test
    public void pointGeneratorTest2() {
        Polynomial irreducible = Polynomial.createFromLong(19L);
        log.debug("irreducible " + irreducible.toBinaryString());
        GeneratorPolynomial gp = GeneratorPolynomial.findGenerator(irreducible);
        for(Long key: gp.getGeneratorPowers().keySet()){
            log.debug("gen: " + gp.getGeneratorPowers().get(key).toBinaryString());
        }

    }
}
