package pl.mkoi.model;

public class ServerAddressDetails {
    private String nickName;
    private String ipAddress;
    private long port;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "NickName " + nickName + ";IP Address " + ipAddress + " ; " + port + " ; ";
    }
}
