package pl.mkoi.util;

import org.apache.log4j.Logger;
import pl.mkoi.util.model.EllipticCurve;
import pl.mkoi.util.model.FiniteField;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Point;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by DominikD on 2016-01-02.
 */
public class PointGenerator {

    private final static Logger log = Logger.getLogger(PointGenerator.class);

    private final static Random random = new Random();

    public static Point generatePoint(EllipticCurve curve, GeneratorPolynomial polynomial) {
        while (true) {
            FiniteField.Element x = getRandomElement(polynomial);
            FiniteField.Element y = getRandomElement(polynomial);
            log.debug("x " + x.toString() + "y " + y.toString());
            Point point = new Point(x, y);
            if (EllipticCurve.checkIfPointSatisfiesEquation(curve, polynomial, point)) return point;
        }
    }

    public static int getPointOrder(EllipticCurve curve, Point point) {
        Point pointCopy = new Point(point.getX(), point.getY());
        BigInteger scalar = BigInteger.ONE;
        do {
            pointCopy.multiplyByScalar(scalar, curve);
            scalar.add(BigInteger.ONE);
            log.debug("point x: " + point.getX().getOrderNumber() + "point y: " + point.getY().getOrderNumber());
            log.debug("multiplicated point x: " + pointCopy.getX().getOrderNumber() + "point y: " + pointCopy.getY().getOrderNumber());
            log.debug(scalar);
        } while (!point.equals(pointCopy));

        return scalar.intValue();
    }

    private static FiniteField.Element getRandomElement(GeneratorPolynomial generatorPolynomial) {
        int random = PointGenerator.random.nextInt(generatorPolynomial.getGeneratorPowers().size());
        Object[] keys = generatorPolynomial.getGeneratorPowers().keySet().toArray();
        long power = (long) keys[random];
        return new FiniteField.Element(power, generatorPolynomial.getGeneratorPower(power));
    }
}
