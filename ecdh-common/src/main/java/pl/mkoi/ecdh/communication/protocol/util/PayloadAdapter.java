package pl.mkoi.ecdh.communication.protocol.util;

import com.google.gson.*;
import pl.mkoi.ecdh.communication.protocol.Payload;

import java.lang.reflect.Type;

/**
 * Created by DominikD on 2016-01-18.
 */
class PayloadAdapter implements JsonSerializer<Payload>, JsonDeserializer<Payload> {

    @Override
    public Payload deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return jsonDeserializationContext.deserialize(element, Class.forName("com.googlecode.whiteboard.model." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }

    @Override
    public JsonElement serialize(Payload payload, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(payload.getClass().getSimpleName()));
        result.add("properties", jsonSerializationContext.serialize(payload, payload.getClass()));

        return result;
    }
}
