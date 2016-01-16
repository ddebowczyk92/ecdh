package pl.mkoi.ecdh.crypto.model;

import java.math.BigInteger;

public class FiniteField {

    private final GeneratorPolynomial generator;
    private Polynomial irreducible;
    private int m;

    private FiniteField() {
        m = 0;
        generator = null;
    }

    public FiniteField(GeneratorPolynomial generator, int m, Polynomial irreducible) {
        this.generator = generator;
        this.m = (int) StrictMath.pow(2, m);
        this.irreducible = irreducible;
    }

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

    public Polynomial divideElements(Polynomial a, Polynomial b) {
        Polynomial polynomial = inverseElement(b);
        return multiplyElements(a, polynomial);
    }


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

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}
