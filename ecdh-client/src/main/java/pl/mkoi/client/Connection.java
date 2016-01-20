package pl.mkoi.client;

import org.apache.log4j.Logger;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.util.PDUReaderWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by DominikD on 2016-01-19.
 */
public class Connection extends Thread {

    private static final Logger log = Logger.getLogger(Connection.class);

    private final Socket clientSocket;
    private final ConcurrentLinkedQueue<ProtocolDataUnit> pduSendQueue;
    private boolean running;
    private BufferedReader in;
    private PrintWriter out;
    private PDUReaderWriter pduReaderWriter;

    public Connection(Socket clientSocket) throws IOException {
        super();
        this.clientSocket = clientSocket;
        this.pduSendQueue = new ConcurrentLinkedQueue<>();

        pduReaderWriter = PDUReaderWriter.getInstance();
    }


    @Override
    public void run() {
        running = true;

        try {
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);

            String temp = in.readLine();
            log.info("Received: " + temp);
        } catch (IOException e) {
            log.error("Error while sending data", e);
        }

        while (!clientSocket.isClosed() && running) {
            if (!pduSendQueue.isEmpty()) {
                try {
                    ProtocolDataUnit pdu = pduSendQueue.poll();
                    writeDataToStream(pdu);

                } catch (IOException e) {
                    log.error("Error while sending data", e);
                }
            }
        }
    }

    public void sendMessage(ProtocolDataUnit message) {
        try {
            this.pduSendQueue.add(message);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void writeDataToStream(ProtocolDataUnit message) throws IOException {
        String dataToSend = pduReaderWriter.serialize(message);
        out.println(dataToSend);
        out.flush();
    }


    public void closeConnection() {
        this.running = false;
        if (!this.clientSocket.isClosed()) {
            try {
                this.clientSocket.close();
            } catch (IOException e) {
                log.error("Error while closing connection", e);
            }

        }
        if (this.in != null) {
            try {
                this.in.close();
            } catch (IOException e) {
                log.error("Error while closing input stream");
            }
        }

        if (this.out != null) {
            this.out.close();
        }

        log.info("Connection closed");
    }

    @Override
    protected void finalize() throws Throwable {

        log.info("Finalized");
        closeConnection();
        super.finalize();
    }
}
