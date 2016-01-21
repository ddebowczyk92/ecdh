package pl.mkoi.ecdh.communication.protocol;

import pl.mkoi.ecdh.communication.protocol.payload.Payload;

/**
 * Created by DominikD on 2016-01-18.
 */
public class ProtocolDataUnit {
    ProtocolHeader header;
    Payload payload;

    public ProtocolDataUnit(ProtocolHeader header, Payload payload) {
        this.header = header;
        this.payload = payload;
    }

    public ProtocolHeader getHeader() {
        return header;
    }

    public void setHeader(ProtocolHeader header) {
        this.header = header;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}

