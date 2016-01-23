package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for Disconnected message
 */
public class DisconnectedPayload extends Payload {
    /**
     * Client Id
     */
    public final int clientId;

    /**
     * @param clientId disconnected client id
     */
    public DisconnectedPayload(int clientId) {
        this.clientId = clientId;
    }
}
