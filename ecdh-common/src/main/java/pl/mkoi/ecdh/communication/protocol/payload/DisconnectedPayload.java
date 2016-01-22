package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DisconnectedPayload extends Payload {
    public final int clientId;

    public DisconnectedPayload(int clientId) {
        this.clientId = clientId;
    }
}
