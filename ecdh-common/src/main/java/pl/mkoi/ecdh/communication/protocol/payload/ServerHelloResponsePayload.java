package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for ServerHelloResponse message
 */
public class ServerHelloResponsePayload extends Payload {

    /**
     * sender name
     */
    private final String nickname;

    /**
     * @param nickname sender name
     */
    public ServerHelloResponsePayload(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return sender name
     */
    public String getNickname() {
        return nickname;
    }

}
