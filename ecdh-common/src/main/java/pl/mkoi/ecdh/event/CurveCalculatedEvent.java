package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.crypto.model.EllipticCurve;

public class CurveCalculatedEvent extends Event {
    private final EllipticCurve curve;

    public CurveCalculatedEvent(EllipticCurve curve) {
        super();
        this.curve = curve;
    }

    public EllipticCurve getCurve() {
        return curve;
    }
}
