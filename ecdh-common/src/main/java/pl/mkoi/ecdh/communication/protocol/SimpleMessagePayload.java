package pl.mkoi.ecdh.communication.protocol;

/**
 * Created by DominikD on 2016-01-20.
 */
public class SimpleMessagePayload extends Payload {
    private String message;


    public SimpleMessagePayload(){

    }

    public SimpleMessagePayload(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
