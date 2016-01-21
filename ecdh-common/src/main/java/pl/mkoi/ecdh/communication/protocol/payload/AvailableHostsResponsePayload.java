package pl.mkoi.ecdh.communication.protocol.payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DominikD on 2016-01-21.
 */
public class AvailableHostsResponsePayload extends Payload {

    private List<NicknameId> hosts = new ArrayList<>();

    public List<NicknameId> getHosts() {
        return hosts;
    }

    public void setHosts(List<NicknameId> hosts) {
        this.hosts = hosts;
    }

    public void addHost(String nickname, int id) {
        NicknameId nicknameId = new NicknameId(nickname, id);
        hosts.add(nicknameId);
    }

    public class NicknameId {
        private final String nickname;
        private final int id;

        public NicknameId(String nickname, int id) {
            this.nickname = nickname;
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }
    }
}

