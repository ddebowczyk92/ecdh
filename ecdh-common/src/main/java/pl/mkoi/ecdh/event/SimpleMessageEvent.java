package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-20.
 */
public class SimpleMessageEvent extends PduEvent {


    public SimpleMessageEvent(ProtocolDataUnit pdu) {
        super(pdu);
    }
}
