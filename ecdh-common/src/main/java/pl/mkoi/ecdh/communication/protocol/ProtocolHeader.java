package pl.mkoi.ecdh.communication.protocol;

import org.joda.time.DateTime;

import java.sql.Timestamp;

public class ProtocolHeader {
    private final String timestamp = new Timestamp(new DateTime().getMillis()).toString();
    private MessageType messageType;
    private int sourceId;
    private int destinationId;

    public ProtocolHeader(){

    }

    public ProtocolHeader(MessageType messageType){
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
