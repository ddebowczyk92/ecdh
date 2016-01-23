package pl.mkoi.ecdh.communication.protocol;

/**
 * Enum with all kind of message that can be handled
 */
public enum MessageType {
    SIMPLE_MESSAGE,
    SIMPLE_MESSAGE_RESPONSE,
    LIST_AVAILABLE_HOSTS_REQUEST,
    LIST_AVAILABLE_HOSTS_RESPONSE,
    SERVER_HELLO,
    SERVER_HELLO_RESPONSE,
    CLIENT_DISCONNECT,
    CLIENT_CONNECT_REQUEST,
    CLIENT_CONNECT_RESPONSE,
    DH_INVITE,
    DH_RESPONSE
}
