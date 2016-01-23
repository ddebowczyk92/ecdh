package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for Connect request message
 */
public class ConnectRequestPayload extends Payload {
    /**
     * user name
     */
    private final String nickName;

    /**
     * @param nickName Sender user name
     */
    public ConnectRequestPayload(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickName;
    }
}
