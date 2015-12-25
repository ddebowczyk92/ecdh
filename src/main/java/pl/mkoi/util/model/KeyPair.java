package pl.mkoi.util.model;

import java.math.BigInteger;

public class KeyPair {

    private BigInteger privateKey;

    private Point publicKey;

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public Point getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Point publicKey) {
        this.publicKey = publicKey;
    }
}
