package pl.mkoi.util.model;

import java.math.BigInteger;

/**
 * The equation of the elliptic curve on a binary field F2m is y2 + xy = x3 + ax2 + b, where b ≠ 0.
 * Here the elements of the finite field are integers of length at most m bits.
 */
public class EllipticCurve {


    private final FiniteField field;
    /**
     * F2m <- this m is it
     */
    private int m;
    /**
     * Curve equation parameter
     */
    private Polynomial a;

    /**
     * Curve equation parameter
     */
    private Polynomial b;

    private Point generatorPoint;

    private Polynomial irreduciblePolynomial;

    /**
     * Scalar range +1;
     */
    private int n;

    private EllipticCurve() {
        field = null;
    }

    /**
     * Creates Elliptic curve for given parameters
     *
     * @param a                     parameter a of elliptic curve equation
     * @param b                     parameter b of elliptic curve equation, cant be zero
     * @param gPoint                generator point
     * @param irreduciblePolynomial polynomial used for modular arithmetic
     */
    public EllipticCurve(Polynomial a, Polynomial b, FiniteField field, Point gPoint, Polynomial irreduciblePolynomial) {
        this.a = a;
        this.b = b;
        this.field = field;

        this.generatorPoint = gPoint;
        this.irreduciblePolynomial = irreduciblePolynomial;
    }

    /**
     * check is based on generator polynomial powering
     * y^2 + xy = x^3 + ax^2 + b
     *
     * @param point is checked in this function
     * @return if points belongs to the curve
     */
    public static boolean checkIfPointSatisfiesEquation(EllipticCurve curve, Point point) {
        Polynomial x = point.getX();
        Polynomial y = point.getY();

        Polynomial firstElement = y.modPow(BigInteger.valueOf(2L), curve.irreduciblePolynomial);
        Polynomial secondElement = x.multiply(y).mod(curve.irreduciblePolynomial);
        Polynomial thirdElement = x.modPow(BigInteger.valueOf(3L), curve.irreduciblePolynomial);
        Polynomial fourthElement = curve.getField().multiplyElements(curve.a, x.modPow(BigInteger.valueOf(2), curve.irreduciblePolynomial));
        Polynomial fifthElement = curve.b;

        Polynomial leftEquationSide = firstElement.xor(secondElement);
        Polynomial rightEquationSide = thirdElement.xor(fourthElement).xor(fifthElement);

        return leftEquationSide.equals(rightEquationSide);

    }

    /*first J second K*/
    public Point addPoint(Point j, Point k) {

        if (!j.equals(k)) {
            Polynomial slope = field.divideElements(j.getY().xor(k.getY()), j.getX().xor(k.getX()));

            Polynomial slopesSum = field.powerElement(slope, 2).xor(slope);

            Polynomial xL = slopesSum.xor(j.getX()).xor(k.getX()).xor(a);
            Polynomial yL = field.multiplyElements(slope, j.getX()).xor(field.multiplyElements(slope, xL)).xor(xL).xor(j.getY());

            return new Point(xL, yL);
        } else {
            Polynomial slope = j.getX().xor(field.divideElements(j.getY(), j.getX()));

            Polynomial xL = field.powerElement(slope, 2).xor(slope).xor(a);
            Polynomial yL = field.powerElement(j.getX(), 2).xor(field.multiplyElements(slope, xL)).xor(xL);

            return new Point(xL, yL);
        }

    }

    public Point subtractPoint(Point first, Point second) {
        Point result = new Point();

        return addPoint(first, getNegativePoint(second));
    }

    public Point getNegativePoint(Point point) {
        Point result = new Point();
        result.setX(point.getX());
        result.setY(point.getX().xor(point.getY()));
        return result;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public Polynomial getA() {
        return a;
    }

    public void setA(Polynomial a) {
        this.a = a;
    }

    public Polynomial getB() {
        return b;
    }

    public void setB(Polynomial b) {
        this.b = b;
    }

    public Point getGeneratorPoint() {
        return generatorPoint;
    }

    public void setGeneratorPoint(Point generatorPoint) {
        this.generatorPoint = generatorPoint;
    }

    public Polynomial getIrreduciblePolynomial() {
        return irreduciblePolynomial;
    }

    public void setIrreduciblePolynomial(Polynomial irreduciblePolynomial) {
        this.irreduciblePolynomial = irreduciblePolynomial;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public FiniteField getField() {
        return field;
    }
}
