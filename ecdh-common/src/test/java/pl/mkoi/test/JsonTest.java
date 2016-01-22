package pl.mkoi.test;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Test;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloPayload;

public class JsonTest {

    private static final Logger log = Logger.getLogger(JsonTest.class);
    @Test
    public void JsonTest() {
        Gson gson = new Gson();
        log.debug(gson.toJson(new ServerHelloPayload(3, "xx")));

    }


}
