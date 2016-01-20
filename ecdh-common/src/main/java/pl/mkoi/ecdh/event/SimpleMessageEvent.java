package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-20.
 */
public class SimpleMessageEvent extends Event {

    public SimpleMessageEvent(ProtocolDataUnit pdu) {
        this.pdu = pdu;
    }

    private final ProtocolDataUnit pdu;

    public ProtocolDataUnit getPdu() {
        return pdu;
    }
}
