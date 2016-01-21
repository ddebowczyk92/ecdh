package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * Created by DominikD on 2016-01-21.
 */
public class ServerHelloResponsePayload extends Payload {

    private final String nickname;

    public ServerHelloResponsePayload(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
