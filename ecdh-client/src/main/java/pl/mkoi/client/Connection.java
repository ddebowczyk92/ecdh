package pl.mkoi.client;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.payload.DisconnectedPayload;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloPayload;
import pl.mkoi.ecdh.communication.protocol.util.MessageProcessor;
import pl.mkoi.ecdh.communication.protocol.util.PDUReaderWriter;
import pl.mkoi.ecdh.crypto.util.SignatureKeyPairGenerator;
import pl.mkoi.ecdh.event.DisconnectEvent;
import pl.mkoi.ecdh.event.ListHostsResponseEvent;
import pl.mkoi.ecdh.event.ServerInterruptEvent;
import pl.mkoi.ecdh.event.SimpleMessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;

/**
 * Created by DominikD on 2016-01-19.
 */
public class Connection extends Thread {

    private static final Logger log = Logger.getLogger(Connection.class);

    private final Socket clientSocket;
    private final AppContext context = AppContext.getInstance();
    private boolean running;
    private BufferedReader in;
    private PrintWriter out;
    private PDUReaderWriter pduReaderWriter;
    private MessageProcessor messageService;


    public Connection(Socket clientSocket) throws IOException {
        super();
        this.clientSocket = clientSocket;
        pduReaderWriter = PDUReaderWriter.getInstance();
        setupMessageService();
    }

    @Override
    public void run() {
        running = true;

        try {
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            String temp;
            while ((temp = in.readLine()) != null) {
                processData(temp);
            }

            context.postEvent(new ServerInterruptEvent());
            closeConnection();


        } catch (IOException e) {
            log.error("Closed connection");
        }
    }

    private void setupMessageService() {
        messageService = new MessageProcessor() {
            @Override
            protected void onSimpleMessageReceived(ProtocolDataUnit pdu) {
                context.postEvent(new SimpleMessageEvent(pdu));
            }

            @Override
            protected void onListHostsResponseReceived(ProtocolDataUnit dataUnit) {
                context.postEvent(new ListHostsResponseEvent(dataUnit));
            }

            @Override
            protected void onServerHelloReceived(ProtocolDataUnit pdu) {
                ServerHelloPayload payload = (ServerHelloPayload) pdu.getPayload();
                PublicKey publicKey = SignatureKeyPairGenerator.decodePublicKey(payload.getServerPublicKey());
                context.setUserId(payload.getId());
                context.setServerPublicKey(publicKey);
            }

            @Override
            protected void onClientDisconnected(ProtocolDataUnit pdu) {
                DisconnectedPayload payload = (DisconnectedPayload) pdu.getPayload();
                context.postEvent(new DisconnectEvent(payload.clientId));
            }
        };
    }

    private void processData(String data) {
        ProtocolDataUnit pdu = pduReaderWriter.deserialize(data);
        messageService.process(pdu);
    }

    public void sendMessage(ProtocolDataUnit message) {
        message.getHeader().setSourceId(context.getUserId());
        try {
            writeDataToStream(message);
        } catch (IOException e) {
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
