package pl.mkoi.test;

import com.google.common.collect.HashBiMap;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.model.*;

public class EllipticCurvePointsOperations {

    private final static Logger log = Logger.getLogger(EllipticCurvePointsOperations.class);

    @Test
    public void pointsOperationTest() {

        HashBiMap<Long, Polynomial> powers = HashBiMap.create(7);
        powers.put(0L, Polynomial.createFromLong(0));
        powers.put(1L, Polynomial.createFromLong(2));
        powers.put(2L, Polynomial.createFromLong(4));
        powers.put(3L, Polynomial.createFromLong(3));
        powers.put(4L, Polynomial.createFromLong(6));
        powers.put(5L, Polynomial.createFromLong(7));
        powers.put(6L, Polynomial.createFromLong(5));
        powers.put(7L, Polynomial.createFromLong(1));

        GeneratorPolynomial generator = new GeneratorPolynomial(Polynomial.createFromLong(2), powers);

        Polynomial irreducible = Polynomial.createFromLong(11L);

        Assert.assertEquals(irreducible.toBinaryString(), "1011");

        FiniteField.Element x1 = new FiniteField.Element(2L, powers.get(2L));
        FiniteField.Element y1 = new FiniteField.Element(6L, powers.get(6L));

        FiniteField.Element x2 = new FiniteField.Element(5L, powers.get(5L));
        FiniteField.Element y2 = new FiniteField.Element(5L, powers.get(5L));

        FiniteField.Element x3 = new FiniteField.Element(3L, powers.get(3L));
        FiniteField.Element y3 = new FiniteField.Element(6L, powers.get(6L));

        FiniteField finiteField = new FiniteField(generator, 3);

        EllipticCurve curve = new EllipticCurve(new FiniteField.Element(2L, powers.get(2L)), new FiniteField.Element(6L, powers.get(6L)), finiteField, null, irreducible);

        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, generator, new Point(x1, y1)));
        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, generator, new Point(x2, y2)));
        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve,generator, new Point(x3, y3)));

        Point result = curve.addPoint(new Point(x1, y1), new Point(x2, y2));

        Assert.assertTrue( result.getX().getOrderNumber().equals(3L) && result.getY().getOrderNumber().equals(6L));

        curve = new EllipticCurve(new FiniteField.Element(2L, powers.get(2L)), new FiniteField.Element(6L, powers.get(6L)), finiteField, null, irreducible);

        x1 = new FiniteField.Element(3L, powers.get(3L));
        y1 = new FiniteField.Element(4L, powers.get(4L));

        result = curve.addPoint(new Point(x1, y1), new Point(x1, y1));

       Assert.assertTrue( result.getX().getOrderNumber() == 2 && result.getY().getOrderNumber() == 3);
    }


}
