package pl.mkoi.ecdh.communication.protocol.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.payload.Payload;

/**
 * Wrapper for Gson serialize/deserialize methods with configured gson instance
 */
public class PDUReaderWriter {
    private static PDUReaderWriter ourInstance = new PDUReaderWriter();
    private Gson gson;

    /**
     * Default empty constructor
     */
    private PDUReaderWriter() {
        gson = new GsonBuilder().registerTypeAdapter(Payload.class, new PayloadAdapter()).create();
    }

    /**
     * Singleton getInstance method
     */
    public static PDUReaderWriter getInstance() {
        return ourInstance;
    }

    /**
     * Method serializes pdu to json string
     *
     * @param dataUnit given pdu
     * @return json string
     */
    public synchronized String serialize(ProtocolDataUnit dataUnit) throws JsonParseException {
        return gson.toJson(dataUnit).toString();
    }

    /**
     * method deserialize input String to PDU object
     *
     * @param input input String
     * @return created PDU
     */
    public synchronized ProtocolDataUnit deserialize(String input) throws JsonParseException {
        return gson.fromJson(input, ProtocolDataUnit.class);
    }


}
