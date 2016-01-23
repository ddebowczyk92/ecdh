package pl.mkoi;

import com.google.common.eventbus.EventBus;
import pl.mkoi.client.Connection;
import pl.mkoi.ecdh.crypto.model.EllipticCurve;
import pl.mkoi.ecdh.crypto.model.KeyPair;
import pl.mkoi.ecdh.crypto.model.Point;
import pl.mkoi.ecdh.event.Event;
import pl.mkoi.model.ServerAddressDetails;

import java.security.PublicKey;

/**
 * Client's context singleton
 * <p/>
 * Created by DominikD on 2016-01-17.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();

    /**
     * Connection with server
     */
    private Connection clientConnection;
    /**
     * Connection parameters
     */
    private ServerAddressDetails serverAddressDetails;
    private boolean connectedToServer;
    private String userNickName;
    /**
     * User id assigned by server
     */
    private int userId;
    private boolean connectedWithUser;
    private int connectedUserId = -1;
    /**
     * Server's public key used to signature verification
     */
    private PublicKey serverPublicKey;
    /**
     * Event bus for event posting and listeners registering
     */
    private EventBus eventBus = new EventBus("CLIENT_EVENT_BUS");
    /**
     * User nickname that client is connected with
     */
    private String connectedUserName;
    /**
     * Elliptic curve parameters
     */
    private EllipticCurve curve;
    /**
     * ECDH keypair
     */
    private KeyPair myKeyPair;
    /**
     * Connected host public key
     */
    private Point connectedHostKey;
    /**
     * Common simmetric key
     */
    private Point commonKey;
    private boolean hasKeyBeenUsed;

    private AppContext() {
    }

    public static synchronized AppContext getInstance() {
        return ourInstance;
    }

    public ServerAddressDetails getServerAddressDetails() {
        return serverAddressDetails;
    }

    public void setServerAddressDetails(ServerAddressDetails serverAddressDetails) {
        this.serverAddressDetails = serverAddressDetails;
    }

    public Connection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(Connection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public boolean isConnectedToServer() {
        return connectedToServer;
    }

    public void setConnectedToServer(boolean connectedToServer) {
        this.connectedToServer = connectedToServer;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public boolean isConnectedWithUser() {
        return connectedWithUser;
    }

    public void setConnectedWithUser(boolean connectedWithUser) {
        this.connectedWithUser = connectedWithUser;
    }

    public int getConnectedUserId() {
        return connectedUserId;
    }

    public void setConnectedUserId(int connectedUserId) {
        this.connectedUserId = connectedUserId;
    }

    public void registerListener(Object obj) {
        this.eventBus.register(obj);
    }

    public void postEvent(Event event) {
        this.eventBus.post(event);
    }

    public String getConnectedUserName() {
        return connectedUserName;
    }

    public void setConnectedUserName(String connectedUserName) {
        this.connectedUserName = connectedUserName;
    }

    public EllipticCurve getCurve() {
        return curve;
    }

    public void setCurve(EllipticCurve curve) {
        this.curve = curve;
    }

    public KeyPair getMyKeyPair() {
        return myKeyPair;
    }

    public void setMyKeyPair(KeyPair myKeyPair) {
        this.myKeyPair = myKeyPair;
    }

    public Point getConnectedHostKey() {
        return connectedHostKey;
    }

    public void setConnectedHostKey(Point connectedHostKey) {
        this.connectedHostKey = connectedHostKey;
    }

    public Point getCommonKey() {
        return commonKey;
    }

    public void setCommonKey(Point commonKey) {
        this.commonKey = commonKey;
    }

    public boolean hasKeyBeenUsed() {
        return hasKeyBeenUsed;
    }

    public void setHasKeyBeenUsed(boolean hasKeyBeenUsed) {
        this.hasKeyBeenUsed = hasKeyBeenUsed;
    }
}
