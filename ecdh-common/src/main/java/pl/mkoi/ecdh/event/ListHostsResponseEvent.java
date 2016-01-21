package pl.mkoi.ecdh.event;

import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-21.
 */
public class ListHostsResponseEvent extends PduEvent {

    public ListHostsResponseEvent(ProtocolDataUnit pdu) {
        super(pdu);
    }
}
