package pl.mkoi.util.model;

import java.math.BigInteger;


public class Point {
    private int x;
    private int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void multiplyByScalar(BigInteger scalar, EllipticCurve curve) {
        Point point = this;

        //// TODO: 25.11.2015 probably to slow
        for (int i = 0; i < scalar.longValue(); i++) {

            //// TODO: 25.11.2015 modulo polynomial (probably)
            point = curve.addPoint(point, this);
        }

        this.x = point.getX();
        this.y = point.getY();
    }
}
