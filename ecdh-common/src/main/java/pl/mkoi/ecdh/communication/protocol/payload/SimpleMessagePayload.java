package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * Created by DominikD on 2016-01-20.
 */
public class SimpleMessagePayload extends Payload {
    private final String message;

    public SimpleMessagePayload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
