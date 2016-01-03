package pl.mkoi.util;

import org.apache.log4j.Logger;
import pl.mkoi.util.model.*;

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
            log.debug(x.toString());
            log.debug(y.toString());
            Point point = new Point(x, y);
            if (EllipticCurve.checkIfPointSatisfiesEquation(curve, polynomial, point)) return point;
        }
    }

    private static FiniteField.Element getRandomElement(GeneratorPolynomial generatorPolynomial) {
        int random = PointGenerator.random.nextInt(generatorPolynomial.getGeneratorPowers().size());
        Object[] keys = generatorPolynomial.getGeneratorPowers().keySet().toArray();
        long power = (long) keys[random];
        return new FiniteField.Element(power, generatorPolynomial.getGeneratorPower(power));
    }
}
