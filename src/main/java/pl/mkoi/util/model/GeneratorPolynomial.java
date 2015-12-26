package pl.mkoi.util.model;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Created by DominikD on 2015-12-25.
 */
public class GeneratorPolynomial extends Polynomial {

    private static final Logger log = Logger.getLogger(GeneratorPolynomial.class);

    private HashSet<Polynomial> generatorPowers;

    public GeneratorPolynomial(Polynomial polynomial, HashSet<Polynomial> generatorPowers) {
        super(polynomial);
        setGeneratorPowers(generatorPowers);
    }

    public static GeneratorPolynomial findGenerator(Polynomial modPolynomial) {
        BigInteger power = BigInteger.ZERO;
        long generatorLong = 2L;
        Polynomial generator = Polynomial.createFromLong(generatorLong);
        HashSet<Polynomial> generatorStore = new HashSet<>();
        long powersNumber = (long) (Math.pow(modPolynomial.getDegree(), 2) - 1);
        while (generator.toBigInteger().compareTo(modPolynomial.toBigInteger()) < 0) {
            while (power.compareTo(BigInteger.valueOf(powersNumber)) <= 0) {
                Polynomial polynomialPow = generator.modPow(power, modPolynomial);
                if (generatorStore.contains(polynomialPow)) {
                    power = BigInteger.ZERO;
                    break;
                } else {
                    generatorStore.add(polynomialPow);
                    power = power.add(BigInteger.ONE);
                }
            }
            if (generatorStore.size() >= powersNumber) return new GeneratorPolynomial(generator, generatorStore);
            else {
                generatorLong += 1L;
                generator = Polynomial.createFromLong(generatorLong);
                generatorStore = new HashSet<>();
            }
        }
        throw new NoSuchElementException();
    }

    public HashSet<Polynomial> getGeneratorPowers() {
        return generatorPowers;
    }

    public void setGeneratorPowers(HashSet<Polynomial> generatorPowers) {
        this.generatorPowers = generatorPowers;
    }
}
