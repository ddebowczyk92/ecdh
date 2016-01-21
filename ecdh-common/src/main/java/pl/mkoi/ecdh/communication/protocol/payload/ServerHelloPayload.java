package pl.mkoi.ecdh.communication.protocol.payload;

import com.google.gson.annotations.SerializedName;

public class ServerHelloPayload extends Payload {

    @SerializedName("id")
    private int id;

    public ServerHelloPayload(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
