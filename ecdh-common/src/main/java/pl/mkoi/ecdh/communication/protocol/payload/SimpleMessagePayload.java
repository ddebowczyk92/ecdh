package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for SimpleMessagePayload message
 */
public class SimpleMessagePayload extends Payload {

    /**
     * message for receiver
     */
    private final String message;

    /**
     * @param message message receiver
     */
    public SimpleMessagePayload(String message) {
        this.message = message;
    }

    /**
     * @return message for receiver
     */
    public String getMessage() {
        return message;
    }


}
