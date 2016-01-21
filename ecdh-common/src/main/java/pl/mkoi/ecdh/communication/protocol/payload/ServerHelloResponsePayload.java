package pl.mkoi.ecdh.communication.protocol.payload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DominikD on 2016-01-21.
 */
public class ServerHelloResponsePayload extends Payload {
    @SerializedName("nickname")
    private final String nickname;

    public ServerHelloResponsePayload(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
