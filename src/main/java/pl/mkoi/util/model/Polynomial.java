package pl.mkoi.util.model;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by DominikD on 2015-11-01.
 */
public class Polynomial {

    private final static Logger log = Logger.getLogger(Polynomial.class);

    public static final Polynomial X = Polynomial.createFromLong(2L);

    public static final Polynomial ONE = Polynomial.createFromLong(1L);

    private BitSet degrees;

    public Polynomial(BitSet degrees) {
        this.setDegrees((BitSet) degrees.clone());
    }

    public Polynomial(Polynomial polynomial) {
        this.setDegrees((BitSet) polynomial.getDegrees().clone());
    }

    public BitSet getDegrees() {
        return degrees;
    }

    public void setDegrees(BitSet degrees) {
        this.degrees = degrees;
    }

    public String toBinaryString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = this.degrees.length() - 1; i >= 0; i--) {
            buffer.append(this.degrees.get(i) ? 1 : 0);
        }
        return buffer.toString();
    }

    public static Polynomial createIrreducible(long degree) {
        while (true) {
            Polynomial p = createRandom(degree);
            if (!p.isReducibile()) return p;
        }
    }

    public static Polynomial createRandom(long degree) {
        Random random = new Random();
        byte[] bytes = new byte[(int) (degree / 8) + 1];
        random.nextBytes(bytes);
        return createFromBytes(bytes, degree);
    }

    public static Polynomial createFromLong(long value) {
        BitSet degress = new BitSet();
        int i = 0;
        while (value != 0) {
            if ((value & 1) == 1) degress.set(i);
            i++;
            value >>= 1;
        }
        return new Polynomial(degress);
    }

    public static Polynomial createFromBigInteger(BigInteger value) {
        BitSet degrees = new BitSet();
        int i = 0;
        while (!value.equals(BigInteger.ZERO)) {
            if (value.and(BigInteger.ONE).equals(BigInteger.ONE)) degrees.set(i);
            i++;
            value.shiftRight(1);
        }
        return new Polynomial(degrees);
    }

    public static Polynomial createFromBytes(byte[] bytes, long degree) {
        BitSet degrees = new BitSet();
        for (int i = 0; i < degree; i++) {
            int aidx = (i / 8);
            int bidx = i % 8;
            byte b = bytes[aidx];
            if (((b >> bidx) & 1) == 1) degrees.set(i);
        }
        degrees.set((int) degree);
        return new Polynomial(degrees);
    }

    public Polynomial or(Polynomial polynomial) {
        BitSet polynomialDgrs = (BitSet) polynomial.getDegrees().clone();
        polynomialDgrs.or(this.degrees);
        return new Polynomial(polynomialDgrs);
    }

    public Polynomial and(Polynomial polynomial) {
        BitSet polynomialDgrs = (BitSet) polynomial.getDegrees().clone();
        polynomialDgrs.and(this.degrees);
        return new Polynomial(polynomialDgrs);
    }

    public Polynomial xor(Polynomial polynomial) {
        BitSet polynomialDgrs = (BitSet) polynomial.getDegrees().clone();
        polynomialDgrs.xor(this.degrees);
        return new Polynomial(polynomialDgrs);
    }

    public Polynomial mod(Polynomial polynomial) {
        int da = this.getDegree();
        int db = polynomial.getDegree();
        Polynomial register = new Polynomial(this.degrees);
        for (int i = da - db; i >= 0; i--) {
            if (register.hasDegree(i + db)) {
                Polynomial shifted = polynomial.shiftLeft(i);
                register = register.xor(shifted);
            }
        }
        return register;
    }

    public Polynomial modPow(BigInteger e, Polynomial m) {
        Polynomial result = Polynomial.ONE;
        Polynomial b = new Polynomial(this);

        while (e.bitCount() != 0) {
            if (e.testBit(0)) {
                result = result.multiply(b).mod(m);
            }
            e = e.shiftRight(1);
            b = b.multiply(b).mod(m);
        }
        return result;
    }


    public Polynomial multiply(Polynomial polynomial) {
        BitSet degrees = new BitSet();
        for (int i = this.getDegree(); i >= 0; i--) {
            if (!this.hasDegree(i)) continue;
            for (int j = polynomial.getDegree(); j >= 0; j--) {
                if (!polynomial.hasDegree(j)) continue;
                int sum = i + j;
                if (degrees.get(sum)) degrees.clear(sum);
                else degrees.set(sum);
            }
        }
        return new Polynomial(degrees);
    }

    public Polynomial gcd(Polynomial b) {
        Polynomial a = new Polynomial(this);
        while (!b.isEmpty()) {
            Polynomial t = new Polynomial(b);
            b = a.mod(b);
            a = t;
        }
        return a;
    }


    public boolean hasDegree(int k) {
        return this.degrees.get(k);
    }

    public Polynomial shiftLeft(int shift) {
        BitSet degrees = new BitSet(this.degrees.length() + shift);
        for (int i = 0; i < this.degrees.length(); i++) {
            if (this.degrees.get(i)) degrees.set(i + shift);
        }
        return new Polynomial(degrees);
    }

    public int getDegree() {
        return this.degrees.length() - 1;
    }

    public boolean isEmpty() {
        return this.degrees.isEmpty();
    }

    public int compareTo(Polynomial o) {
        int cmp = Integer.compare(this.getDegree(), o.getDegree());
        if (cmp != 0) return cmp;
        Polynomial x = this.xor(o);
        if (x.isEmpty()) return 0;
        return this.hasDegree(x.getDegree()) ? 1 : 0;
    }

    private Polynomial reduceExponent(final int p) {
        BigInteger q_to_p = BigInteger.valueOf(2L).pow(p);
        Polynomial x_to_q_to_p = X.modPow(q_to_p, this);
        return x_to_q_to_p.xor(X).mod(this);
    }

    public boolean isReducibile() {
        final long degree = this.getDegree();
        for (int i = 1; i <= (int) (degree / 2); i++) {
            Polynomial b = reduceExponent(i);
            Polynomial g = this.gcd(b);
            if (g.compareTo(Polynomial.ONE) != 0) return true;
        }
        return false;
    }

    public BigInteger toBigInteger() {
        return new BigInteger(this.toBinaryString(), 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polynomial that = (Polynomial) o;

        return degrees.equals(that.degrees);

    }


    @Override
    public int hashCode() {
        return degrees.hashCode();
    }
}
