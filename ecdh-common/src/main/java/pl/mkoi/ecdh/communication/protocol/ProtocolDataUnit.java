package pl.mkoi.ecdh.communication.protocol;

import pl.mkoi.ecdh.communication.protocol.payload.Payload;

/**
 * Class for PDU and message unification
 */
public class ProtocolDataUnit {
    ProtocolHeader header;
    Payload payload;

    /**
     * Default constructor methods
     *
     * @param header  new header for this pdu
     * @param payload new payload for this pdu
     */
    public ProtocolDataUnit(ProtocolHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    /**
     * @return header pdu header
     */
    public ProtocolHeader getHeader() {
        return header;
    }

    /**
     * @param header new header to be set
     */
    public void setHeader(ProtocolHeader header) {
        this.header = header;
    }

    /**
     * @return payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * @param payload new payload for pdu
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}

