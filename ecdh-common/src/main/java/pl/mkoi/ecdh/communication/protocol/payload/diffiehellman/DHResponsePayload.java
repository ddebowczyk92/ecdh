package pl.mkoi.ecdh.communication.protocol.payload.diffiehellman;

import pl.mkoi.ecdh.communication.protocol.payload.PayloadWithSignature;
import pl.mkoi.ecdh.crypto.model.Point;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DHResponsePayload extends PayloadWithSignature {

    private final Point bG;

    public DHResponsePayload(Point bG) {
        this.bG = bG;
    }

    public Point getbG() {
        return bG;
    }

    @Override
    public String payloadToSign() {
        return bG.toString();
    }
}
