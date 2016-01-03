package pl.mkoi.util.model;

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
    private FiniteField.Element a;

    /**
     * Curve equation parameter
     */
    private FiniteField.Element b;

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
    public EllipticCurve(FiniteField.Element a, FiniteField.Element b, FiniteField field, Point gPoint, Polynomial irreduciblePolynomial) {
        this.a = a;
        this.b = b;
        this.field = field;

        this.generatorPoint = gPoint;
        this.irreduciblePolynomial = irreduciblePolynomial;
    }

    private long getMaxNumberForField() {
        return (long) StrictMath.pow(2, m);
    }

    /*first J second K*/
    public Point addPoint(Point j, Point k) {

        if (!j.equals(k)) {
            FiniteField.Element slope = field.divideElements(field.addElements(j.getY(), k.getY()), field.addElements(j.getX(), k.getX()));

            FiniteField.Element slopesSum = field.addElements(field.powerElement(slope, 2), slope);


            FiniteField.Element xL = field.addElements(field.addElements(field.addElements(slopesSum, j.getX()), k.getX()), a);
            FiniteField.Element yL = field.addElements(field.addElements(field.multiplyElements(slope, field.addElements(j.getX(), xL)), xL), j.getY());

            return new Point(xL, yL);
        } else {
            FiniteField.Element slope = field.addElements(j.getX(), field.divideElements(j.getY(), j.getX()));

            FiniteField.Element xL = field.addElements(field.addElements(field.powerElement(slope, 2), slope), a);
            FiniteField.Element yL = field.addElements(j.getX(), field.multiplyElements(field.addElements(slope, Polynomial.ONE), xL));

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
        result.setY(field.addElements(point.getX(), point.getY()));
        return result;
    }

    /**
     * check is based on generator polynomial powering
     * y^2 + xy = x^3 + ax^2 + b
     *
     * @param point
     * @return
     */
    public static boolean checkIfPointSatisfiesEquation(EllipticCurve curve, GeneratorPolynomial generatorPolynomial, Point point) {
        long m = generatorPolynomial.getGeneratorPowers().size() - 1;
        long firstPower = (point.getY().getOrderNumber() * 2) % m;
        long secondPower = (point.getY().getOrderNumber() + point.getX().getOrderNumber()) % m;
        long thirdPower = (point.getX().getOrderNumber() * 3) % m;
        long fourthPower = (curve.getA().getOrderNumber() + (point.getX().getOrderNumber() * 2)) % m;

        Polynomial firstElement = generatorPolynomial.getGeneratorPower(firstPower);
        Polynomial secondElement = generatorPolynomial.getGeneratorPower(secondPower);
        Polynomial thirdElement = generatorPolynomial.getGeneratorPower(thirdPower);
        Polynomial fourthElement = generatorPolynomial.getGeneratorPower(fourthPower);
        Polynomial fifthElement = curve.getB().getPolynomial();

        Polynomial leftEquationSide = firstElement.xor(secondElement);
        Polynomial rightEquationSide = thirdElement.xor(fourthElement).xor(fifthElement);

        return leftEquationSide.equals(rightEquationSide);

    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public FiniteField.Element getA() {
        return a;
    }

    public void setA(FiniteField.Element a) {
        this.a = a;
    }

    public FiniteField.Element getB() {
        return b;
    }

    public void setB(FiniteField.Element b) {
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
}
