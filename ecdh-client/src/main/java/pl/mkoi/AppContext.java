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
}
