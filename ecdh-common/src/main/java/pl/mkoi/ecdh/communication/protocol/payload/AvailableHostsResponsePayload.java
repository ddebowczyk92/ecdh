package pl.mkoi.ecdh.communication.protocol.payload;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DominikD on 2016-01-21.
 */
public class AvailableHostsResponsePayload extends Payload {

    @SerializedName("hosts")
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

    public String[] getNames() {
        String[] list = new String[hosts.size()];

        for (int i = 0; i < hosts.size(); i++) {
            list[i] = hosts.get(i).getNickname();
        }

        return list;
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

