package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * Created by DominikD on 2016-01-22.
 */
public class ConnectRequestPayload extends Payload {
    private final String nickName;

    public ConnectRequestPayload(String nickName) {
        this.nickName = nickName;
    }

    public String getNickname() {
        return nickName;
    }
}
