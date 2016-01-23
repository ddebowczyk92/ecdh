package pl.mkoi.ecdh.communication.protocol.payload.diffiehellman;

import pl.mkoi.ecdh.communication.protocol.payload.Payload;
import pl.mkoi.ecdh.communication.protocol.payload.PayloadWithSignature;
import pl.mkoi.ecdh.crypto.model.Point;

/**
 * {@link Payload} extending class for DHResponse message
 */
public class DHResponsePayload extends PayloadWithSignature {

    /**
     * receiver generator Point times private key
     */
    private final Point bG;

    /**
     * @param bG sender public key
     */
    public DHResponsePayload(Point bG) {
        this.bG = bG;
    }

    /**
     * @return sender public key
     */
    public Point getbG() {
        return bG;
    }

    @Override
    public String payloadToSign() {
        return bG.toString();
    }
}
