package pl.mkoi.client;

import java.net.Socket;

/**
 * Created by DominikD on 2016-01-19.
 */
public class Connection extends Thread {


    private final Socket clientSocket;
    private boolean running;

    public Connection(Socket clientSocket) {
        super();
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        running = true;
        while (!clientSocket.isClosed() && running){

        }

    }
}
