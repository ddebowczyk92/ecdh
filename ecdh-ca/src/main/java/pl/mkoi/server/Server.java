package pl.mkoi.server;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by DominikD on 2016-01-18.
 */
public class Server extends Thread {

    private static final Logger log = Logger.getLogger(Server.class);


    private final int port;
    private final int connectionLimit;
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private boolean running;
    private ServerSocket socket;
    private ExecutorService executorService;

    public Server(int port, int connectionLimit) {
        this.connectionLimit = connectionLimit;
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(this.connectionLimit);
    }

    @Override
    public void run() {
        try {
            socket = new ServerSocket(this.port, this.connectionLimit);
            this.running = true;
            log.info("Server started running on port: " + this.port);
            while (!socket.isClosed() && this.running) {
                Socket clientSocket = socket.accept();
                int id = idGenerator.getAndIncrement();
                Connection newConn = new Connection(id, clientSocket);
                AppContext.getInstance().addConnection(id, newConn);
                AppContext.getInstance().registerListener(newConn);
                executorService.submit(newConn);
            }

        } catch (IOException e) {
            log.error("Error while running server", e);
            stopServer();
        }
    }

    public void stopServer() {
        if (isRunning()) {
            this.running = false;
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Error while closing server socket", e);
            }
        }
        log.info("Server closed");
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getPort() {
        return port;
    }

    public int getConnectionLimit() {
        return connectionLimit;
    }


}
