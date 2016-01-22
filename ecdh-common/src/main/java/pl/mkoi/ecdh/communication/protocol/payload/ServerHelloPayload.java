package pl.mkoi.ecdh.communication.protocol.payload;

import pl.mkoi.ecdh.crypto.model.EllipticCurve;

public class ServerHelloPayload extends Payload {
    private final int id;
    private final String serverPublicKey;
    private final EllipticCurve ellipticCurve;

    public ServerHelloPayload(int id, String serverPublicKey, EllipticCurve ellipticCurve) {
        this.id = id;
        this.serverPublicKey = serverPublicKey;
        this.ellipticCurve = ellipticCurve;
    }

    public int getId() {
        return id;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }

    public EllipticCurve getEllipticCurve() {
        return ellipticCurve;
    }
}
