package pl.mkoi;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pl.mkoi.ecdh.crypto.model.EllipticCurve;
import pl.mkoi.ecdh.crypto.util.SignatureKeyPairGenerator;
import pl.mkoi.ecdh.event.DisconnectEvent;
import pl.mkoi.ecdh.event.Event;
import pl.mkoi.server.Connection;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server context singleton
 * <p/>
 * Created by DominikD on 2016-01-18.
 */
public class AppContext {
    /**
     * instance of singleton
     */
    private static AppContext ourInstance = new AppContext();
    /**
     * KeyPair for message sigining
     */
    private final KeyPair keyPair;
    /**
     * Storage for every running connection handled by server
     */
    private ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();
    /**
     * Event bus for event posting and listeners registering
     */
    private EventBus eventBus = new EventBus("SERVER_EVENT_BUS");
    /**
     * Elliptic curve parameters
     */
    private EllipticCurve curve;

    private AppContext() {
        keyPair = SignatureKeyPairGenerator.generateSignatureKeyPair();
        eventBus.register(this);
    }

    public static AppContext getInstance() {
        return ourInstance;
    }

    public void addConnection(int id, Connection connection) {
        connections.put(id, connection);
    }

    public void removeConnection(int id) {
        Connection conn = connections.get(id);
        if (conn != null) {
            connections.remove(id);
        }
    }

    public Connection getConnection(int id) {
        return connections.get(id);
    }

    public Map<Integer, Connection> getConnections() {
        return this.connections;
    }

    public PrivateKey getPrivateKey() {
        return this.keyPair.getPrivate();
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }

    public void registerListener(Object listener) {
        eventBus.register(listener);
    }

    public void postEvent(Event event) {
        this.eventBus.post(event);
    }

    @Subscribe
    public void onClientDisconnect(final DisconnectEvent event) {
        connections.remove(event.getConnectionId());
    }

    public EllipticCurve getCurve() {
        return curve;
    }

    public void setCurve(EllipticCurve curve) {
        this.curve = curve;
    }
}
