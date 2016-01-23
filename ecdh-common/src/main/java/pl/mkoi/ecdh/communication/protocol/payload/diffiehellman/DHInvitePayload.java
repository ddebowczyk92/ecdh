package pl.mkoi.ecdh.communication.protocol.payload.diffiehellman;

import pl.mkoi.ecdh.communication.protocol.payload.Payload;
import pl.mkoi.ecdh.communication.protocol.payload.PayloadWithSignature;
import pl.mkoi.ecdh.crypto.model.Point;

/**
 * {@link Payload} extending class for DHInvite message message
 */
public class DHInvitePayload extends PayloadWithSignature {

    /**
     * generator point times sender private key
     */
    private final Point aG;

    /**
     * @param aG new public key
     */
    public DHInvitePayload(Point aG) {
        this.aG = aG;
    }

    /**
     * @return public key
     */
    public Point getaG() {
        return aG;
    }

    @Override
    public String payloadToSign() {
        return aG.toString();
    }
}
