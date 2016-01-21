package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-21.
 */
public abstract class PduEvent extends Event {

    private final ProtocolDataUnit pdu;

    public PduEvent(ProtocolDataUnit pdu){
        this.pdu = pdu;
    }

    public ProtocolDataUnit getPdu() {
        return pdu;
    }
}
