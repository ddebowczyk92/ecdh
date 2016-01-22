package pl.mkoi.ecdh.crypto.model;

public class PointAtInfinity extends Point {

    private final boolean isFinite = false;

    @Override
    public String toString() {
        return "Point at Infinity";
    }
}
