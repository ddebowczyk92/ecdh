package pl.mkoi.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import pl.mkoi.util.model.FiniteField;
import pl.mkoi.util.model.GeneratorPolynomial;
import pl.mkoi.util.model.Polynomial;

import java.math.BigInteger;


/**
 * Created by DominikD on 2015-10-31.
 */
public class PolynomialTest {

    private final static Logger log = Logger.getLogger(PolynomialTest.class);

    @Test
    public void or() {
        Polynomial polynomial1 = Polynomial.createFromLong(35);
        Polynomial polynomial2 = Polynomial.createFromLong(35);
        Polynomial polynomial3 = polynomial1.or(polynomial2);
        log.info("35 | 35 = " + polynomial3.toBinaryString());
        Assert.assertEquals(polynomial3.toBinaryString(), polynomial1.toBinaryString());
        Assert.assertEquals(polynomial3.toBinaryString(), polynomial2.toBinaryString());

        Polynomial polynomial4 = Polynomial.createFromLong(15);
        Polynomial polynomial5 = Polynomial.createFromLong(11);
        Polynomial polynomial6 = polynomial4.or(polynomial5);
        log.info("15 | 11 = " + polynomial6.toBinaryString());
        Assert.assertEquals(polynomial6.toBinaryString(), "1111");
    }

    @Test
    public void and() {
        Polynomial polynomial1 = Polynomial.createFromLong(15);
        Polynomial polynomial2 = Polynomial.createFromLong(11);
        Polynomial polynomial3 = polynomial1.and(polynomial2);
        log.info("15 & 11 = " + polynomial3.toBinaryString());
        Assert.assertEquals(polynomial3.toBinaryString(), "1011");
    }

    @Test
    public void xor() {
        Polynomial polynomial1 = Polynomial.createFromLong(15);
        Polynomial polynomial2 = Polynomial.createFromLong(11);
        Polynomial polynomial3 = polynomial1.xor(polynomial2);
        log.info("15 xor 11 = " + polynomial3.toBinaryString());
        Assert.assertEquals(polynomial3.toBinaryString(), "100");
    }

    @Test
    public void shiftLeft() {
        Polynomial polynomial1 = Polynomial.createFromLong(15);
        Polynomial polynomial2 = polynomial1.shiftLeft(3);
        log.info("1111 << 3 (in GF2^k) = " + polynomial2.toBinaryString());
        Assert.assertEquals(polynomial2.toBinaryString(), "1111000");
    }

    @Test
    public void mod() {
        Polynomial polynomial1 = Polynomial.createFromLong(14);
        Polynomial polynomial2 = Polynomial.createFromLong(4);
        Polynomial polynomial3 = polynomial1.mod(polynomial2);
        log.info("12 % 3 = " + polynomial3.toBinaryString());

    }

    @Test
    public void multiply() {
        Polynomial polynomial1 = Polynomial.createFromLong(32);
        Polynomial polynomial2 = Polynomial.createFromLong(13);
        Polynomial polynomial3 = polynomial1.multiply(polynomial2);
        log.info(polynomial1.toBinaryString() + " * " + polynomial2.toBinaryString() + " = " + polynomial3.toBinaryString());
    }

    @Test
    public void gcd() {
        Polynomial polynomial1 = Polynomial.createFromLong(25);
        Polynomial polynomial2 = Polynomial.createFromLong(20);
        Polynomial polynomial3 = polynomial1.gcd(polynomial2);
        log.info("gcd(" + polynomial1.toBinaryString() + ", " + polynomial2.toBinaryString() + ") = " + polynomial3.toBinaryString());
    }

    @Test
    public void createIrreducible() {
        Polynomial polynomial = Polynomial.createIrreducible(4);
        log.info("irreducible : " + polynomial.toBinaryString());
    }

    @Test
    public void isIrreducible() {
        Polynomial polynomial = Polynomial.createFromLong(19L);
        log.info("is irreducible " + polynomial.toBinaryString() + "? " + !polynomial.isReducibile());
    }

    @Test
    public void pow() {
        Polynomial generator = Polynomial.createFromLong(2L);
        BigInteger power = BigInteger.valueOf(15L);
        Polynomial mod = Polynomial.createFromLong(19L);
        Polynomial result = generator.modPow(power, mod);
        log.info(generator.toBinaryString() + " pow " + power.toString() + " mod " + mod.toBinaryString() + " = " + result.toBinaryString());

    }

    @Test
    public void inverse() {

        int m = 16;
        Polynomial irreducible = Polynomial.createIrreducible(16);
        GeneratorPolynomial generator = GeneratorPolynomial.findGenerator(irreducible);

        FiniteField finiteField = new FiniteField(generator, 16, irreducible);

        Polynomial temp, inv;

        for (int i = (int) (Math.pow(2, m) - 1); i > 0; i--) {
            temp = Polynomial.createFromLong(i);
            inv = finiteField.inverseElement(temp);

            assert temp.multiply(inv).mod(irreducible).equals(Polynomial.ONE);
            log.info(i + " passed");

        }

        log.info("test passed");

    }


}
