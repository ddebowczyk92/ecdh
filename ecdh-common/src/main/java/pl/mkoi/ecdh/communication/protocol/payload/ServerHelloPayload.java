package pl.mkoi.ecdh.communication.protocol.payload;

import pl.mkoi.ecdh.crypto.model.EllipticCurve;


/**
 * {@link Payload} extending class for ServerHello message
 */
public class ServerHelloPayload extends Payload {
    private final int id;
    private final String serverPublicKey;
    private final EllipticCurve ellipticCurve;

    /**
     * Default Constructor
     *
     * @param id              id given for client
     * @param serverPublicKey public key of server
     * @param ellipticCurve   calculated elliptic curve
     */
    public ServerHelloPayload(int id, String serverPublicKey, EllipticCurve ellipticCurve) {
        this.id = id;
        this.serverPublicKey = serverPublicKey;
        this.ellipticCurve = ellipticCurve;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return ServerPublicKey
     */
    public String getServerPublicKey() {
        return serverPublicKey;
    }

    /**
     * @return elliptic curve
     */
    public EllipticCurve getEllipticCurve() {
        return ellipticCurve;
    }
}
