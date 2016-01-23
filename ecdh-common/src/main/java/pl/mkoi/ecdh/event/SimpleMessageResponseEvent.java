package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-22.
 */
public class SimpleMessageResponseEvent extends PduEvent {

    public SimpleMessageResponseEvent(ProtocolDataUnit pdu) {
        super(pdu);
    }
}
