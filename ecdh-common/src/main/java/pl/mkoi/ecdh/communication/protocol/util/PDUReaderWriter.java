package pl.mkoi.ecdh.communication.protocol.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import pl.mkoi.ecdh.communication.protocol.Payload;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;

/**
 * Created by DominikD on 2016-01-18.
 */
public class PDUReaderWriter {
    private static PDUReaderWriter ourInstance = new PDUReaderWriter();
    private Gson gson;

    public static PDUReaderWriter getInstance() {
        return ourInstance;
    }

    private PDUReaderWriter() {
        gson = new GsonBuilder().registerTypeAdapter(Payload.class, new PayloadAdapter()).create();
    }

    private synchronized String serialize(ProtocolDataUnit dataUnit) throws JsonParseException {
        return gson.toJson(dataUnit).toString();
    }

    private synchronized ProtocolDataUnit deserialize(String input) throws JsonParseException {
        return gson.fromJson(input, ProtocolDataUnit.class);
    }


}
