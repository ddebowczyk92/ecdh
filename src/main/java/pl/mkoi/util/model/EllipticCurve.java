package pl.mkoi.util.model;

/**
 * The equation of the elliptic curve on a binary field F2m is y2 + xy = x3 + ax2 + b, where b ≠ 0.
 * Here the elements of the finite field are integers of length at most m bits.
 */
public class EllipticCurve {

    private int m;
    /**
     * Curve equation parameter
     */
    private int a;

    /**
     * Curve equation parameter
     */
    private int b;

    private Point generatorPoint;

    private Polynomial irreduciblePolynomial;

    /**
     * Scalar range +1;
     */
    private int n;


    /**
     * Co-factor h
     */
    private int numberOfPoints;

    private EllipticCurve() {
    }

    /**
     * Creates Elliptic curve for given parameters
     *
     * @param a                     parameter a of elliptic curve equation
     * @param b                     parameter b of elliptic curve equation, cant be zero
     * @param m                     parameter of binary field
     * @param gPoint                generator point
     * @param irreduciblePolynomial polynomial used for modular arithmetic
     * @param numberOfPoints        number of points defined on ec
     */
    public EllipticCurve(int a, int b, int m, Point gPoint, Polynomial irreduciblePolynomial, int numberOfPoints) {
        this.a = a;
        this.b = b;
        this.m = m;

        this.generatorPoint = gPoint;
        this.irreduciblePolynomial = irreduciblePolynomial;
        this.numberOfPoints = numberOfPoints;
    }

    private long getMaxNumberForField() {
        return (long) StrictMath.pow(2, m);
    }

    /*first J second K*/
    public Point addPoint(Point first, Point second) {
        int slope = (first.getY() + second.getY()) / (first.getX() + second.getX());

        int x = (int) (StrictMath.pow(slope, 2) + slope + first.getX() + second.getX() + slope);
        int y = slope * (first.getX() + x) + x + first.getY();

        return new Point(x, y);
    }

    public Point subtractPoint(Point first, Point second) {
        Point result = new Point();

        return addPoint(first, getNegativePoint(second));
    }

    public Point getNegativePoint(Point point) {
        Point result = new Point();
        result.setX(point.getX());
        result.setY(point.getX() + point.getY());
        return result;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
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

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }
}
