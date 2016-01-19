package pl.mkoi;

import pl.mkoi.server.Connection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DominikD on 2016-01-18.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();

    private ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

    public static AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
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
}
