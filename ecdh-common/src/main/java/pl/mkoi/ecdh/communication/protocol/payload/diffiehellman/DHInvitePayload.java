package pl.mkoi.ecdh.communication.protocol.payload.diffiehellman;

import pl.mkoi.ecdh.communication.protocol.payload.PayloadWithSignature;
import pl.mkoi.ecdh.crypto.model.Point;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DHInvitePayload extends PayloadWithSignature {

    private final Point aG;

    public DHInvitePayload(Point aG) {
        this.aG = aG;
    }

    public Point getaG() {
        return aG;
    }

    @Override
    public String payloadToSign() {
        return aG.toString();
    }
}
