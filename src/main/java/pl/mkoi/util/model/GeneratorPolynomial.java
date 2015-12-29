package pl.mkoi.util.model;

import com.google.common.collect.HashBiMap;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.NoSuchElementException;


public class GeneratorPolynomial extends Polynomial {

    private static final Logger log = Logger.getLogger(GeneratorPolynomial.class);

    private HashBiMap<Long, Polynomial> generatorPowers;

    public GeneratorPolynomial(Polynomial polynomial, HashBiMap<Long, Polynomial> generatorPowers) {
        super(polynomial);
        setGeneratorPowers(generatorPowers);
    }

    public static GeneratorPolynomial findGenerator(Polynomial modPolynomial) {
        BigInteger power = BigInteger.ZERO;
        long generatorLong = 2L;

        Polynomial generator = Polynomial.createFromLong(generatorLong);
        HashBiMap<Long, Polynomial> generatorStore = HashBiMap.create();

        long powersNumber = (long) (Math.pow(modPolynomial.getDegree(), 2) - 1);

        long iterator = 0;

        while (generator.toBigInteger().compareTo(modPolynomial.toBigInteger()) < 0) {
            while (power.compareTo(BigInteger.valueOf(powersNumber)) <= 0) {

                Polynomial polynomialPow = generator.modPow(power, modPolynomial);

                if (generatorStore.containsValue(polynomialPow)) {
                    power = BigInteger.ZERO;
                    break;
                } else {
                    generatorStore.put(iterator, polynomialPow);
                    iterator++;
                    power = power.add(BigInteger.ONE);
                }
            }
            if (generatorStore.size() >= powersNumber) {
                return new GeneratorPolynomial(generator, generatorStore);
            } else {
                generatorLong += 1L;
                generator = Polynomial.createFromLong(generatorLong);
                generatorStore = HashBiMap.create();
                iterator = 0;
            }
        }
        throw new NoSuchElementException();
    }

    public HashBiMap<Long, Polynomial> getGeneratorPowers() {
        return generatorPowers;
    }

    public void setGeneratorPowers(HashBiMap<Long, Polynomial> generatorPowers) {
        this.generatorPowers = generatorPowers;
    }
}
