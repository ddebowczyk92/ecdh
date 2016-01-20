package pl.mkoi.server;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.*;
import pl.mkoi.ecdh.communication.protocol.util.PDUReaderWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    private BufferedReader in;
    private PrintWriter out;
    private PDUReaderWriter pduReaderWriter;

    public Connection(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.context = AppContext.getInstance();
        this.pduReaderWriter = PDUReaderWriter.getInstance();

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public void run() {
        log.info("Client " + this.id + " connected to server");

        ProtocolHeader header = new ProtocolHeader();
        header.setMessageType(MessageType.SERVER_HELLO);
        Payload payload = new ServerHelloPayload(id);
        ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);

        try {
            writeDataToStream(pdu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.write("Server says Hello\n");
        out.flush();
        running = true;

        while (isRunning() && !socket.isClosed()) {
            String inputLine;

            try {
                inputLine = in.readLine();
                log.info("I read! " + inputLine);

                if (inputLine == null) {
                    log.info("Disconnected ");
                    return;
                }
                //process message


            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                log.error("error while closing input stream for connection " + getId(), e);
            }
        }
        if (out != null) {
            out.close();
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Error while closing connection id" + getId(), e);
            }
        }
    }

    private void writeDataToStream(ProtocolDataUnit message) throws IOException {
        String dataToSend = pduReaderWriter.serialize(message);
        out.println(dataToSend);
        out.flush();
    }
}
