package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * Created by DominikD on 2016-01-22.
 */
public class ConnectRequestResponsePayload extends Payload {
    private final boolean accepted;


    public ConnectRequestResponsePayload(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean hasAccepted() {
        return accepted;
    }
}
