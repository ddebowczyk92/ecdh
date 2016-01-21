package pl.mkoi.test;

import com.google.gson.Gson;
import org.junit.Test;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloPayload;

public class JsonTest {

    @Test
    public void JsonTest() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(new ServerHelloPayload(3)).toString());

    }


}
