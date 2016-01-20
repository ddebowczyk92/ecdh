package pl.mkoi.ecdh.communication.protocol.util;

import com.google.gson.*;
import pl.mkoi.ecdh.communication.protocol.Payload;

import java.lang.reflect.Type;

/**
 * Created by DominikD on 2016-01-18.
 */
class PayloadAdapter implements JsonSerializer<Payload>, JsonDeserializer<Payload> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public Payload deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> objectClass = null;
        try {
            objectClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return jsonDeserializationContext.deserialize(jsonObject.get(INSTANCE), objectClass);
    }

    @Override
    public JsonElement serialize(Payload payload, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject retValue = new JsonObject();
        String className = payload.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = jsonSerializationContext.serialize(retValue);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
