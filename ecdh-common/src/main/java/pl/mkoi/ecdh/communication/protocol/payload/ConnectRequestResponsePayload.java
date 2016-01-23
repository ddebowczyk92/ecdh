package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for ConnectRequestResponse message
 */
public class ConnectRequestResponsePayload extends Payload {

    /**
     * if connection was accepted
     */
    private final boolean accepted;

    /**
     * @param accepted if connection was accepted
     */
    public ConnectRequestResponsePayload(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * @return if connection was accepted
     */
    public boolean hasAccepted() {
        return accepted;
    }
}
