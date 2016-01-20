package pl.mkoi;

import pl.mkoi.client.Connection;
import pl.mkoi.model.ServerAddressDetails;

/**
 * Created by DominikD on 2016-01-17.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();

    private Connection clientConnection;
    private ServerAddressDetails serverAddressDetails;
    private boolean  connectedToServer;

    public static synchronized AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
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
}
