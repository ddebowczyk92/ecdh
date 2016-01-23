package pl.mkoi.ecdh.crypto.model;

import com.google.common.collect.HashBiMap;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.NoSuchElementException;

/**
 * Object representation of generator polynomial
 */
public class GeneratorPolynomial extends Polynomial {

    private static final Logger log = Logger.getLogger(GeneratorPolynomial.class);

    private transient HashBiMap<Long, Polynomial> generatorPowers;

    public GeneratorPolynomial(Polynomial polynomial, HashBiMap<Long, Polynomial> generatorPowers) {
        super(polynomial);
        setGeneratorPowers(generatorPowers);
    }

    /**
     * Function for finding generator polynomial by brute force algorithm
     *
     * @param modPolynomial
     * @return
     */
    public static GeneratorPolynomial findGenerator(Polynomial modPolynomial) {
        BigInteger power = BigInteger.ZERO;
        long generatorLong = 2L;

        Polynomial generator = Polynomial.createFromLong(generatorLong);
        HashBiMap<Long, Polynomial> generatorStore = HashBiMap.create();

        long powersNumber = (long) Math.pow(2, modPolynomial.getDegree()) - 1;

        while (generator.toBigInteger().compareTo(modPolynomial.toBigInteger()) < 0) {
            while (power.compareTo(BigInteger.valueOf(powersNumber)) <= 0) {

                Polynomial polynomialPow = generator.modPow(power, modPolynomial);

                if (generatorStore.containsValue(polynomialPow)) {
                    power = BigInteger.ZERO;
                    break;
                } else {
                    generatorStore.put(power.longValue(), polynomialPow);
                    power = power.add(BigInteger.ONE);
                }
            }
            if (generatorStore.size() >= powersNumber) {
                return new GeneratorPolynomial(generator, generatorStore);
            } else {
                generatorLong += 1L;
                generator = Polynomial.createFromLong(generatorLong);
                generatorStore = HashBiMap.create();
                power = BigInteger.ZERO;
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

    /**
     * Computes power of generator candidate
     *
     * @param power
     * @return
     */
    public Polynomial getGeneratorPower(long power) {

        Polynomial poly = getGeneratorPowers().get(power);

        if (poly != null) {
            return poly;
        } else if (power == 0) {
            Long key = this.generatorPowers.inverse().get(Polynomial.ONE);
            return generatorPowers.get(key);
        }


        return this.generatorPowers.get(power);
    }
}
