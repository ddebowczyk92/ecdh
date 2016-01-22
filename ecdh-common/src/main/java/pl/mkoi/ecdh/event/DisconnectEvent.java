package pl.mkoi.ecdh.event;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DisconnectEvent extends Event {
    private final int connectionId;

    public DisconnectEvent(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
