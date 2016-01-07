package pl.mkoi.util;

import org.apache.log4j.Logger;
import pl.mkoi.util.model.EllipticCurve;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Point;
import pl.mkoi.util.model.Polynomial;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by DominikD on 2016-01-02.
 */
public class PointGenerator {

    private final static Logger log = Logger.getLogger(PointGenerator.class);

    private final static Random random = new Random();

    public static Point generatePoint(EllipticCurve curve, GeneratorPolynomial polynomial) {
        while (true) {
            Polynomial x = getRandomElement(polynomial);
            Polynomial y = getRandomElement(polynomial);
//            log.debug("x " + x.toString() + "y " + y.toString());
            Point point = new Point(x, y);
            if (EllipticCurve.checkIfPointSatisfiesEquation(curve, point)) return point;
        }
    }

    public static int getPointOrder(EllipticCurve curve, Point point) {
        BigInteger scalar = BigInteger.ONE;
        Point pointCopy = new Point(point.getX(), point.getY());
        Set<Point> multiplications = new HashSet<>();
        while (true) {
            pointCopy.multiplyByScalar(scalar, curve);
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
