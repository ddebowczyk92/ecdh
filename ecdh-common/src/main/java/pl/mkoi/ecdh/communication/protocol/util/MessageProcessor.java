package pl.mkoi.ecdh.communication.protocol.util;

import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        switch (type) {
            case SIMPLE_MESSAGE:
                onSimpleMessageReceived(dataUnit);
                break;
            case SERVER_HELLO:
                onServerHelloReceived(dataUnit);
                break;
            case SERVER_HELLO_RESPONSE:
                onServerHelloResponseReceived(dataUnit);
                break;
            case LIST_AVAILABLE_HOSTS_REQUEST:
                onListHostsRequestReceived(dataUnit);
                break;
            case LIST_AVAILABLE_HOSTS_RESPONSE:
                onListHostsResponseReceived(dataUnit);
                break;
            case CLIENT_DISCONNECT:
                onClientDisconnected(dataUnit);
                break;
            default:
                throw new NoSuchMethodError();
        }
    }

    protected void onListHostsResponseReceived(ProtocolDataUnit dataUnit) {
        throw new NotImplementedException();
    }

    protected abstract void onSimpleMessageReceived(ProtocolDataUnit pdu);

    protected void onServerHelloResponseReceived(ProtocolDataUnit dataUnit) {
        throw new NotImplementedException();
    }

    protected void onListHostsRequestReceived(ProtocolDataUnit pdu) {
        throw new NotImplementedException();
    }

    protected void onServerHelloReceived(ProtocolDataUnit pdu) {
        throw new NotImplementedException();
    }

    protected void onClientDisconnected(ProtocolDataUnit pdu) {
        throw new NotImplementedException();
    }


}
