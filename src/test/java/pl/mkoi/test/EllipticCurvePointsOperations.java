package pl.mkoi.test;

import com.google.common.collect.HashBiMap;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.model.*;

import java.math.BigInteger;

public class EllipticCurvePointsOperations {

    private final static Logger log = Logger.getLogger(EllipticCurvePointsOperations.class);

    @Test
    public void pointsOperationTest() {

        GeneratorPolynomial generator = new GeneratorPolynomial(Polynomial.createFromLong(2), null);
        Polynomial irreducible = Polynomial.createFromLong(11L);

        HashBiMap<Long, Polynomial> powers = HashBiMap.create(7);
        powers.put(1L, generator.modPow(BigInteger.valueOf(1), irreducible));
        powers.put(2L, generator.modPow(BigInteger.valueOf(2), irreducible));
        powers.put(3L, generator.modPow(BigInteger.valueOf(3), irreducible));
        powers.put(4L, generator.modPow(BigInteger.valueOf(4), irreducible));
        powers.put(5L, generator.modPow(BigInteger.valueOf(5), irreducible));
        powers.put(6L, generator.modPow(BigInteger.valueOf(6), irreducible));
        powers.put(7L, generator.modPow(BigInteger.valueOf(7), irreducible));

        generator.setGeneratorPowers(powers);

        FiniteField.Element x1 = new FiniteField.Element(2L, powers.get(2L));
        FiniteField.Element y1 = new FiniteField.Element(6L, powers.get(6L));

        FiniteField.Element x2 = new FiniteField.Element(5L, powers.get(5L));
        FiniteField.Element y2 = new FiniteField.Element(5L, powers.get(5L));

        FiniteField.Element x3 = new FiniteField.Element(3L, powers.get(3L));
        FiniteField.Element y3 = new FiniteField.Element(6L, powers.get(6L));

        FiniteField finiteField = new FiniteField(generator, 7);

        EllipticCurve curve = new EllipticCurve(new FiniteField.Element(2L, powers.get(2L)), new FiniteField.Element(6L, powers.get(6L)), finiteField, null, irreducible);

        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, generator, new Point(x1, y1)));
        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, generator, new Point(x2, y2)));
        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, generator, new Point(x3, y3)));

        Point result = curve.addPoint(new Point(x1, y1), new Point(x2, y2));

        Assert.assertTrue(result.getX().getOrderNumber().equals(3L) && result.getY().getOrderNumber().equals(6L));

        curve = new EllipticCurve(new FiniteField.Element(2L, powers.get(2L)), new FiniteField.Element(6L, powers.get(6L)), finiteField, null, irreducible);

        x1 = new FiniteField.Element(3L, powers.get(3L));
        y1 = new FiniteField.Element(4L, powers.get(4L));

        result = curve.addPoint(new Point(x1, y1), new Point(x1, y1));

        Assert.assertTrue(result.getX().getOrderNumber() == 2 && result.getY().getOrderNumber() == 3);

        log.info("TEST PASSED");
    }


}
