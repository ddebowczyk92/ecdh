package pl.mkoi.ecdh.crypto.model;

import java.math.BigInteger;

/**
 * Model class for representation of binary finite field f2m
 */
public class FiniteField {

    private final GeneratorPolynomial generator;
    private Polynomial irreducible;
    private int m;


    private FiniteField() {
        m = 0;
        generator = null;
    }

    /**
     * default constructor
     *
     * @param generator   generator polynomial of a field
     * @param m           m parameter of a field
     * @param irreducible irreducible polynomial for curve
     */
    public FiniteField(GeneratorPolynomial generator, int m, Polynomial irreducible) {
        this.generator = generator;
        this.m = (int) StrictMath.pow(2, m);
        this.irreducible = irreducible;
    }

    /**
     * multiply two elements of field mod irreducible polynomial
     *
     * @param a first factor
     * @param b second factor
     * @return new result polynomial
     */
    public Polynomial multiplyElements(Polynomial a, Polynomial b) {
        return a.multiply(b).mod(irreducible);
    }

    public Polynomial powerElement(Polynomial a, int power) {
        if (a.getDegree() <= 0) {
            return a;
        } else {
            return a.modPow(BigInteger.valueOf(power), irreducible).mod(irreducible);
        }
    }

    /**
     * multiplies element and an inverse of second one
     *
     * @param a first factor
     * @param b factor to be inverted
     * @return new result polynomial
     */
    public Polynomial divideElements(Polynomial a, Polynomial b) {
        Polynomial polynomial = inverseElement(b);
        return multiplyElements(a, polynomial);
    }

    /**
     * inverts element in a field with using binary extended euclidean algorithm
     *
     * @param a polynomial to be inverted
     * @return new inverted polynomial
     */
    public Polynomial inverseElement(Polynomial a) {
        Polynomial u = new Polynomial(a);
        Polynomial v = new Polynomial(irreducible);
        Polynomial g1 = Polynomial.ONE;
        Polynomial g2 = Polynomial.ZERO;

        int j;

        while (!u.equals(Polynomial.ONE)) {
            j = u.getDegree() - v.getDegree();
            if (j < 0) {

                Polynomial temp = u;
                u = v;
                v = temp;

                temp = g1;
                g1 = g2;
                g2 = temp;

                j = -j;
            }

            u = u.xor(Polynomial.createFromLong((long) Math.pow(2, j)).multiply(v)).mod(irreducible);
            g1 = g1.xor(Polynomial.createFromLong((long) Math.pow(2, j)).multiply(g2).mod(irreducible));
        }

        return g1;
    }

    /**
     * @return m parameter of a field
     */
    public int getM() {
        return m;
    }

    /**
     * set m parameter of a field
     */
    public void setM(int m) {
        this.m = m;
    }
}
