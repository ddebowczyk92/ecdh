package pl.mkoi.ecdh.crypto.model;

import java.math.BigInteger;

/**
 * Model representation of public-private KeyPair
 */
public class KeyPair {

    private BigInteger privateKey;
    private Point publicKey;

    /**
     * @return generated privateKey big integer number
     */
    public BigInteger getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey sets new private key
     */
    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * @return current public key a*G
     */
    public Point getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey new public key
     */
    public void setPublicKey(Point publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "KeyPair:( Private: " + privateKey.longValue() + " ; Public: " + publicKey.getX().toBinaryString() + "," + publicKey.getY().toBinaryString() + ")";
    }
}
