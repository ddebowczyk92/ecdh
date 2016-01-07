package pl.mkoi.util.model;

import java.math.BigInteger;


public class Point implements Cloneable {
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

    public void multiplyByScalar(BigInteger scalar, EllipticCurve curve) {
        Point point = this;

        for (int i = 0; i < scalar.longValue(); i++) {
            point = curve.addPoint(point, this);
        }

        this.x = point.getX();
        this.y = point.getY();
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
