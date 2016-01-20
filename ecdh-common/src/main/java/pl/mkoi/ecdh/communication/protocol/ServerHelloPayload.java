package pl.mkoi.ecdh.communication.protocol;

public class ServerHelloPayload extends Payload {
    private final int id;

    public ServerHelloPayload(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }
}
