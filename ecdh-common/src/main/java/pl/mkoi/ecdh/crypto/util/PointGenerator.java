package pl.mkoi.ecdh.crypto.util;

import org.apache.log4j.Logger;
import pl.mkoi.ecdh.crypto.model.EllipticCurve;
import pl.mkoi.ecdh.crypto.model.GeneratorPolynomial;
import pl.mkoi.ecdh.crypto.model.Point;
import pl.mkoi.ecdh.crypto.model.Polynomial;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Util class for generating generator points for curve
 */
public class PointGenerator {

    private final static Logger log = Logger.getLogger(PointGenerator.class);

    private final static Random random = new Random();

    /**
     * Method generates point for curve and generator polynomial
     *
     * @param curve      curve parameter
     * @param polynomial given generator polynomial
     * @return generated point
     */
    public static Point generatePoint(EllipticCurve curve, GeneratorPolynomial polynomial) {
        while (true) {
            Polynomial x = getRandomElement(polynomial);
            Polynomial y = getRandomElement(polynomial);
            Point point = new Point(x, y);
            if (EllipticCurve.checkIfPointSatisfiesEquation(curve, point)) return point;
        }
    }

    /**
     * returns order of a point
     *
     * @param curve given curve
     * @param point given point
     */
    public static int getPointOrder(EllipticCurve curve, Point point) {
        BigInteger scalar = BigInteger.ONE;
        Point pointCopy = new Point(point.getX(), point.getY());
        Set<Point> multiplications = new HashSet<>();
        while (true) {
            pointCopy = pointCopy.multiplyByScalar(scalar, curve);
            Point newPoint = new Point(pointCopy.getX(), pointCopy.getY());
            if (multiplications.contains(newPoint)) break;
            else {
                multiplications.add(newPoint);
            }
        }
        return multiplications.size() + 1;
    }


    private static Polynomial getRandomElement(GeneratorPolynomial generatorPolynomial) {
        int random = PointGenerator.random.nextInt(generatorPolynomial.getGeneratorPowers().size());
        Object[] keys = generatorPolynomial.getGeneratorPowers().keySet().toArray();
        long power = (long) keys[random];
        return generatorPolynomial.getGeneratorPower(power);
    }
}
