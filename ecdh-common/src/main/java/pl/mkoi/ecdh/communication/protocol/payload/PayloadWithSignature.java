package pl.mkoi.ecdh.communication.protocol.payload;

/**
 * {@link Payload} extending class for Signature message
 */
public abstract class PayloadWithSignature extends Payload {

    /**
     * ca signature
     */
    private String signature;

    /**
     * @return signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * sets new signature
     *
     * @param signature new signaure
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * payload to signature translate method
     */
    public abstract String payloadToSign();


}
