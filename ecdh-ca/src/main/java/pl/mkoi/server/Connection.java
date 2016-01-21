package pl.mkoi.server;

import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ProtocolHeader;
import pl.mkoi.ecdh.communication.protocol.payload.AvailableHostsResponsePayload;
import pl.mkoi.ecdh.communication.protocol.payload.Payload;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloPayload;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloResponsePayload;
import pl.mkoi.ecdh.communication.protocol.util.MessageProcessor;
import pl.mkoi.ecdh.communication.protocol.util.PDUReaderWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * Created by DominikD on 2016-01-18.
 */
public class Connection implements Runnable {

    private static final Logger log = Logger.getLogger(Connection.class);

    private final int id;
    private final Socket socket;
    private String nickName;
    private AppContext context;
    private boolean running;
    private BufferedReader in;
    private PrintWriter out;
    private PDUReaderWriter pduReaderWriter;
    private MessageProcessor messageService;

    public Connection(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.context = AppContext.getInstance();
        this.pduReaderWriter = PDUReaderWriter.getInstance();
        setupMessageService();
    }

    @Override
    public void run() {
        log.info("Client " + this.id + " connected to server");
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            running = true;
            ProtocolHeader header = new ProtocolHeader();
            header.setMessageType(MessageType.SERVER_HELLO);
            Payload payload = new ServerHelloPayload(id);
            ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);
            writeDataToStream(pdu);
            String stringData;
            while ((stringData = in.readLine()) != null) {
                processData(stringData);
            }
            closeConnection();
        } catch (IOException e) {
            log.error(e);

        }
    }

    public synchronized void writeDataToStream(ProtocolDataUnit message) {
        String dataToSend = pduReaderWriter.serialize(message);
        out.println(dataToSend);
        System.out.println("SEND : " + dataToSend);
        out.flush();
    }

    private void setupMessageService() {
        messageService = new MessageProcessor() {
            @Override
            protected void onSimpleMessageReceived(ProtocolDataUnit pdu) {
                Connection connection = context.getConnection(pdu.getHeader().getDestinationId());
                connection.writeDataToStream(pdu);
            }

            @Override
            protected void onServerHelloResponseReceived(ProtocolDataUnit dataUnit) {
                ServerHelloResponsePayload payload = (ServerHelloResponsePayload) dataUnit.getPayload();
                setNickName(payload.getNickname());
            }

            @Override
            protected void onListHostsRequestReceived(ProtocolDataUnit pdu) {
                Integer senderId = pdu.getHeader().getSourceId();
                ProtocolHeader header = new ProtocolHeader(MessageType.LIST_AVAILABLE_HOSTS_RESPONSE);
                AvailableHostsResponsePayload payload = new AvailableHostsResponsePayload();
                Map<Integer, Connection> connections = context.getConnections();
                for (Integer id : connections.keySet()) {
                    if (id != senderId) {
                        Connection conn = connections.get(id);
                        payload.addHost(conn.getNickName(), id);
                    }
                }
                ProtocolDataUnit newPdu = new ProtocolDataUnit(header, payload);
                writeDataToStream(newPdu);
            }
        };
    }

    private void processData(String data) {
        ProtocolDataUnit pdu = pduReaderWriter.deserialize(data);
        messageService.process(pdu);
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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


}
