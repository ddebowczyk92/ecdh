package pl.mkoi.util.model;

import java.math.BigInteger;


public class Point {
    private FiniteField.Element x;
    private FiniteField.Element y;

    public Point() {
        this.x = new FiniteField.Element(0l, Polynomial.ONE);
        this.y = new FiniteField.Element(0l, Polynomial.ONE);
    }

    public Point(FiniteField.Element x, FiniteField.Element y) {
        this.x = x;
        this.y = y;
    }

    public FiniteField.Element getX() {
        return x;
    }

    public void setX(FiniteField.Element x) {
        this.x = x;
    }

    public FiniteField.Element getY() {
        return y;
    }

    public void setY(FiniteField.Element y) {
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

        return (point.getX().getOrderNumber().equals(this.getX().getOrderNumber()))
                && (point.getY().getOrderNumber().equals(this.getY().getOrderNumber()));
    }
}
