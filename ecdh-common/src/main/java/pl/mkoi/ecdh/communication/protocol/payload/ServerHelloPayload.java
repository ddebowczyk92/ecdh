package pl.mkoi.ecdh.communication.protocol.payload;

public class ServerHelloPayload extends Payload {
    private final int id;
    private final String serverPublicKey;

    public ServerHelloPayload(int id, String serverPublicKey) {
        this.id = id;
        this.serverPublicKey = serverPublicKey;
    }

    public int getId() {
        return id;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }
}
