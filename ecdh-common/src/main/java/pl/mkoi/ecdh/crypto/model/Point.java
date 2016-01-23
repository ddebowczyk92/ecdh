package pl.mkoi.ecdh.crypto.model;

import org.apache.log4j.Logger;

import java.math.BigInteger;

/**
 * Model representation of elliptic curve point
 */
public class Point implements Cloneable {
    private final static Logger log = Logger.getLogger(Point.class);
    private Polynomial x;
    private Polynomial y;

    /**
     * empty constructor
     */
    public Point() {
        this.x = Polynomial.ONE;
        this.y = Polynomial.ONE;
    }

    /**
     * @param x x coordinate of point
     * @param y y coordinate of point
     */
    public Point(Polynomial x, Polynomial y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs new Point from another
     */
    public Point(Point point) {
        this.x = new Polynomial(point.getX());
        this.y = new Polynomial(point.getY());
    }

    /**
     * @return x coordinate
     */
    public Polynomial getX() {
        return x;
    }

    /**
     * @param x new x coordinate
     */
    public void setX(Polynomial x) {
        this.x = x;
    }

    /**
     * @return y coordinate
     */
    public Polynomial getY() {
        return y;
    }

    /**
     * @param y set new y coordinate
     */
    public void setY(Polynomial y) {
        this.y = y;
    }

    /**
     * multiply point by scalar by sequence of additions
     *
     * @param scalar point will by multiplies by this number
     * @param curve  curve for adding on it
     */
    public Point multiplyByScalar(BigInteger scalar, EllipticCurve curve) {
        Point point = this, add = this;

        for (int i = 0; i < scalar.longValue(); i++) {
            point = curve.addPoint(point, add);
        }
        return point;
    }

    /**
     * multiply scalar times generator
     *
     * @param scalar generator will be added to this point scalar times
     * @param curve  curve for adding on it
     */
    public Point multiplyByScalarTimesGenerator(BigInteger scalar, EllipticCurve curve) {

        Point point = this, add = curve.getGeneratorPoint();

        for (int i = 0; i < scalar.longValue(); i++) {
            point = curve.addPoint(point, add);
        }

        return point;
    }


    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;

        return this.x.equals(point.x) && this.y.equals(point.y);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Point: x: " + x.toString() + " y: " + y.toString();
    }
}
