package pl.mkoi.test;

import com.google.common.collect.HashBiMap;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.model.*;

public class EllipticCurvePointsOperations {

    private final static Logger log = Logger.getLogger(EllipticCurvePointsOperations.class);


    @Test
    public void morePointsOperationTest() {


        Polynomial irreducible = Polynomial.createFromLong(19L);
        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);

        HashBiMap<Long, Polynomial> powers = generator.getGeneratorPowers();

        FiniteField field = new FiniteField(generator, 4, irreducible);

        Polynomial a = Polynomial.createFromLong(8L);
        Polynomial b = Polynomial.createFromLong(9L);

        System.out.println("Polynomial " + irreducible.toBinaryString());
        System.out.println("Generator " + generator.toBinaryString());
        System.out.println("CURVE PARAMETERS: a: " + a + " CURVE PARAMETERS: b: " + b);

        EllipticCurve curve = new EllipticCurve(a, b, field, null, irreducible);

        //curve is defined, operations below

        Polynomial x1 = Polynomial.createFromLong(2L);
        Polynomial y1 = Polynomial.createFromLong(15L);

        Polynomial x2 = Polynomial.createFromLong(12L);
        Polynomial y2 = Polynomial.createFromLong(12L);

        Point point = curve.addPoint(new Point(x1, y1), new Point(x2, y2));

        System.out.println(x1.toString());
        System.out.println(y1.toString());

        System.out.println(x2.toString());
        System.out.println(y2.toString());

        System.out.println(point.toString());

        System.out.println("---------------------------------------");

        x1 = Polynomial.createFromLong(2L);
        y1 = Polynomial.createFromLong(15L);
        point = curve.addPoint(new Point(x1, y1), new Point(x1, y1));

        System.out.println(x1.toString());
        System.out.println(y1.toString());

        System.out.println(point.toString());

        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, new Point(x1, y1)));
        Assert.assertTrue(EllipticCurve.checkIfPointSatisfiesEquation(curve, new Point(x2, y2)));
    }


    @Test
    public void calculationStopTest() {
        int m = 4;
        Polynomial irreducible = Polynomial.createIrreducible(m);
        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);
        FiniteField finiteField = new FiniteField(generator, m, irreducible);

        int a = 4;
        int b = 14;

        Polynomial polyA = Polynomial.createFromLong(a);
        Polynomial polyB = Polynomial.createFromLong(b);

        log.info("Points multiplication started for curve A = " + polyA + " B = " + polyB);

        EllipticCurve curve = new EllipticCurve(polyA, polyB, finiteField, null, irreducible);

        curve.addPoint(new Point(polyA, polyB), new Point(polyA, polyB));

        log.info("Points addition finished ");

        log.info("Finished");

    }


}
