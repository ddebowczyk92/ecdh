package pl.mkoi;

import pl.mkoi.server.Server;

/**
 * Hello world!
 */
public class AppServer {
    public static void main(String[] args) {
        Server server = new Server(455, 10);
        server.run();

    }
}
