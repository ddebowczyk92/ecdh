package pl.mkoi.client;

import org.apache.log4j.Logger;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.util.PDUReaderWriter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by DominikD on 2016-01-19.
 */
public class Connection extends Thread {

    private static final Logger log = Logger.getLogger(Connection.class);

    private final Socket clientSocket;
    private boolean running;
    private final ConcurrentLinkedQueue<ProtocolDataUnit> pduSendQueue;
    private DataInputStream dis;
    private DataOutputStream dos;
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
        while (!clientSocket.isClosed() && running) {
            if (!pduSendQueue.isEmpty()) {
                try {
                    this.dis = new DataInputStream(this.clientSocket.getInputStream());
                    this.dos = new DataOutputStream(this.clientSocket.getOutputStream());
                    ProtocolDataUnit pdu = pduSendQueue.poll();
                    writeDataToStream(pdu);
                    this.dis.close();
                    this.dos.close();
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
        this.dos.write(dataToSend.getBytes(Charset.forName("UTF-8")));
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
        if (this.dis != null) {
            try {
                this.dis.close();
            } catch (IOException e) {
                log.error("Error while closing input stream");
            }
        }

        if (this.dos != null) {
            try {
                this.dos.close();
            } catch (IOException e) {
                log.error("Error while closing output stream");
            }
        }
    }


}
