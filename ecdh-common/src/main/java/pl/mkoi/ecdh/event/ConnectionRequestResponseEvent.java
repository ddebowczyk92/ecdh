package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-22.
 */
public class ConnectionRequestResponseEvent extends PduEvent {
    public ConnectionRequestResponseEvent(ProtocolDataUnit pdu) {
        super(pdu);
    }
}
