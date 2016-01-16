package pl.mkoi.ecdh.crypto.model;

import org.apache.log4j.Logger;

import java.math.BigInteger;


public class Point implements Cloneable {
    private final static Logger log = Logger.getLogger(Point.class);
    private Polynomial x;
    private Polynomial y;

    public Point() {
        this.x = Polynomial.ONE;
        this.y = Polynomial.ONE;
    }

    public Point(Polynomial x, Polynomial y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = new Polynomial(point.getX());
        this.y = new Polynomial(point.getY());
    }

    public Polynomial getX() {
        return x;
    }

    public void setX(Polynomial x) {
        this.x = x;
    }

    public Polynomial getY() {
        return y;
    }

    public void setY(Polynomial y) {
        this.y = y;
    }

    public Point multiplyByScalar(BigInteger scalar, EllipticCurve curve) {
        Point point = this, add = this;

        for (int i = 0; i < scalar.longValue(); i++) {
            point = curve.addPoint(point, add);
        }
        return point;
    }

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
