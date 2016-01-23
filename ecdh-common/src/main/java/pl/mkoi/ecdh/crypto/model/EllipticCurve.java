package pl.mkoi.ecdh.crypto.model;

import java.math.BigInteger;

/**
 * The equation of the elliptic curve on a binary field F2m is y2 + xy = x3 + ax2 + b, where b ≠ 0.
 * Here the elements of the finite field are integers of length at most m bits.
 */
public class EllipticCurve {


    public static final Point INFINITY = new PointAtInfinity();
    private final FiniteField field;
    /**
     * Curve equation parameter
     */
    private final Polynomial a;

    /**
     * Curve equation parameter
     */
    private final Polynomial b;

    private Point generatorPoint;

    private Polynomial irreduciblePolynomial;


    /**
     * Creates Elliptic curve for given parameters
     *
     * @param a                     parameter a of elliptic curve equation
     * @param b                     parameter b of elliptic curve equation, cant be zero
     * @param irreduciblePolynomial polynomial used for modular arithmetic
     */
    public EllipticCurve(Polynomial a, Polynomial b, FiniteField field, Polynomial irreduciblePolynomial) {
        this.a = a;
        this.b = b;
        this.field = field;
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

            if (getNegativePoint(j).equals(k) && INFINITY != null) return INFINITY;

            if (j instanceof PointAtInfinity || k instanceof PointAtInfinity) {
                return j instanceof PointAtInfinity ? k : j;
            }

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

    /**
     * method substract points
     *
     * @param first  minuend
     * @param second subtrahend
     */
    public Point subtractPoint(Point first, Point second) {
        Point result = new Point();

        return addPoint(first, getNegativePoint(second));
    }

    /**
     * Methods returns -point
     *
     * @param point given point
     */
    public Point getNegativePoint(Point point) {
        Point result = new Point();
        result.setX(point.getX());
        result.setY(point.getX().xor(point.getY()));
        return result;
    }

    /**
     * @return m for curve's field
     */
    public int getM() {
        return field.getM();
    }

    /**
     * @param m set m parameter of a field
     */
    public void setM(int m) {
        field.setM(m);
    }

    /**
     * @return a parameter of curve equation
     */
    public Polynomial getA() {
        return a;
    }

    /**
     * @return b paramter of curve equation
     */
    public Polynomial getB() {
        return b;
    }

    /**
     * @return generator point of a field
     */
    public Point getGeneratorPoint() {
        return generatorPoint;
    }

    /**
     * sets generator point for a curve
     */
    public void setGeneratorPoint(Point generatorPoint) {
        this.generatorPoint = generatorPoint;
    }

    /**
     * @return irreducible polynomial for field
     */
    public Polynomial getIrreduciblePolynomial() {
        return irreduciblePolynomial;
    }

    /**
     * set irreducible sets new irreducible polynomial for field
     */
    public void setIrreduciblePolynomial(Polynomial irreduciblePolynomial) {
        this.irreduciblePolynomial = irreduciblePolynomial;
    }

    /**
     * @return finite field object
     */
    public FiniteField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "\n Elliptic Curve with parameters: a = " + a.toBinaryString() + " b = "
                + b.toBinaryString() + "\n Generator Point " + generatorPoint;
    }
}
