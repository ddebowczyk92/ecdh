package pl.mkoi.server;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DominikD on 2016-01-18.
 */
public class Connection implements Runnable {

    private static final Logger log = Logger.getLogger(Connection.class);

    private final int id;
    private final Socket socket;
    private AppContext context;
    private boolean running;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Connection(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.context = AppContext.getInstance();
    }

    @Override
    public void run() {
        try {
            while (isRunning() && !socket.isClosed()) {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }
        } catch (IOException e) {
            log.error("error connection " + getId(), e);
        }
    }

    public int getId() {
        return id;
    }

    public boolean isRunning() {
        return running;
    }

    public void setIsRunning(boolean isRunning) {
        this.running = isRunning;
    }

    public void closeConnection() {
        log.info("closing connection " + getId());
        if (this.isRunning()) {
            this.running = false;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("error while closing input stream for connection " + getId(), e);
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("error while closing output stream for connection " + getId(), e);
            }
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Error while closing connection id" + getId(), e);
            }
        }
    }
}
