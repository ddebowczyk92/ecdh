package pl.mkoi.ecdh.communication.protocol.util;

import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ServerHelloPayload;
import pl.mkoi.ecdh.communication.protocol.SimpleMessagePayload;

/**
 * Class handles message processing and delivers reaction methods
 */
public abstract class MessageProcessor {

    /**
     * Method process messages in various ways due to message type
     *
     * @param dataUnit received and parsed message
     */
    public void process(ProtocolDataUnit dataUnit) {
        MessageType type = dataUnit.getHeader().getMessageType();
        ((SimpleMessagePayload) dataUnit.getPayload()).getMessage();

        switch (type) {
            case SIMPLE_MESSAGE:
                onSimpleMessageReceived(((SimpleMessagePayload) dataUnit.getPayload()));
                break;
            case SERVER_HELLO:
                onServerHelloReceived(((ServerHelloPayload) dataUnit.getPayload()));
                break;
            case LIST_AVAILABLE_HOSTS:
                onListHostsReceived();
                break;
            default:
                break;
        }
    }

    protected abstract void onListHostsReceived();

    protected abstract void onServerHelloReceived(ServerHelloPayload payload);

    protected abstract void onSimpleMessageReceived(SimpleMessagePayload payload);

}
