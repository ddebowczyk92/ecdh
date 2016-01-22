package pl.mkoi.ecdh.communication.protocol;

/**
 * Created by DominikD on 2016-01-18.
 */
public enum MessageType {
    SIMPLE_MESSAGE,
    LIST_AVAILABLE_HOSTS_REQUEST,
    LIST_AVAILABLE_HOSTS_RESPONSE,
    SERVER_HELLO,
    SERVER_HELLO_RESPONSE,
    CLIENT_DISCONNECT,
    CLIENT_CONNECT_REQUEST,
    CLIENT_CONNECT_RESPONSE
}
