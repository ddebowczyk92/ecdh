package pl.mkoi;

import pl.mkoi.ecdh.crypto.util.SignatureKeyPairGenerator;
import pl.mkoi.server.Connection;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DominikD on 2016-01-18.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();
    private final KeyPair keyPair;
    private ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

    public static AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
        keyPair = SignatureKeyPairGenerator.generateSignatureKeyPair();
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
}
