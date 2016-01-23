package pl.mkoi.ecdh.communication.protocol.payload;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Payload} extending class for AvailableHostsResponse message
 */
public class AvailableHostsResponsePayload extends Payload {

    /**
     * List collection for available hosts details
     */
    @SerializedName("hosts")
    private List<NicknameId> hosts = new ArrayList<>();

    /**
     * @return hosts List
     */
    public List<NicknameId> getHosts() {
        return hosts;
    }

    /**
     * Method sets new host list
     *
     * @param hosts new given list
     */
    public void setHosts(List<NicknameId> hosts) {
        this.hosts = hosts;
    }

    /**
     * Adds new host to list
     *
     * @param id       id of new host
     * @param nickname name of new host
     */
    public void addHost(String nickname, int id) {
        NicknameId nicknameId = new NicknameId(nickname, id);
        hosts.add(nicknameId);
    }

    /**
     * @return name array for JList
     */
    public String[] getNames() {
        String[] list = new String[hosts.size()];

        for (int i = 0; i < hosts.size(); i++) {
            list[i] = hosts.get(i).getNickname();
        }

        return list;
    }

    /**
     * Name and id pair
     */
    public class NicknameId {
        private final String nickname;
        private final int id;

        /**
         * @param id new nickane
         * @param id new id
         */
        public NicknameId(String nickname, int id) {
            this.nickname = nickname;
            this.id = id;
        }

        /**
         * @return nickname from current pair
         */
        public String getNickname() {
            return nickname;
        }

        /**
         * @return id for current pair
         */
        public int getId() {
            return id;
        }
    }

}

