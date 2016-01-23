package pl.mkoi.ecdh.crypto.model;

/**
 * Model representation for point at infinity3
 */
public class PointAtInfinity extends Point {

    private final boolean isFinite = false;

    @Override
    public String toString() {
        return "Point at Infinity";
    }
}
