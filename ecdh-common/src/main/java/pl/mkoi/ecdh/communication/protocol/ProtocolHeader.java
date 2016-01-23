package pl.mkoi.ecdh.communication.protocol;

import org.joda.time.DateTime;

import java.sql.Timestamp;

/**
 * Class for unification pdu headers
 */
public class ProtocolHeader {
    private final String timestamp = new Timestamp(new DateTime().getMillis()).toString();
    private MessageType messageType;
    private int sourceId;
    private int destinationId;

    /**
     * Empty constructor
     */
    public ProtocolHeader() {

    }

    /**
     * Default constructor
     *
     * @param messageType enum message type
     */
    public ProtocolHeader(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * @return current message type
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType new message type
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * @return sender id
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * @param sourceId set sender id
     */
    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * @return receiver id
     */
    public int getDestinationId() {
        return destinationId;
    }

    /**
     * @param destinationId new receiver id
     */
    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    /**
     * @return message timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }
}
