package pl.mkoi.gui;

import com.google.common.eventbus.Subscribe;
import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ProtocolHeader;
import pl.mkoi.ecdh.communication.protocol.payload.AvailableHostsResponsePayload;
import pl.mkoi.ecdh.event.ListHostsResponseEvent;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class UserListDialog extends JDialog {
    private static final Logger log = Logger.getLogger(UserListDialog.class);
    private JPanel contentPane;
    private JPanel scrollContainer;
    private JList list;
    private JButton connectButton;
    private JButton refreshButton;
    private String[] data;
    private List<AvailableHostsResponsePayload.NicknameId> hosts;

    public UserListDialog() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setSize(getPreferredSize());

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

//        [260, 200]

        String categories[] = {"Wait for users list from server"};
        list.setListData(categories);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateListRequests();
            }
        });
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryConnectToUser(list.getSelectedIndex());
            }
        });

        setTitle("Select user from list");


        updateListRequests();

    }

    private void updateListRequests() {
        ProtocolHeader header = new ProtocolHeader();
        header.setMessageType(MessageType.LIST_AVAILABLE_HOSTS_REQUEST);

        ProtocolDataUnit pdu = new ProtocolDataUnit(header, null);
        AppContext.getInstance().registerListener(this);
        AppContext.getInstance().getClientConnection().sendMessage(pdu);
    }

    private void tryConnectToUser(int selectedIndex) {
        //try connect
//        hosts.get(selectedIndex);
        //dispose dialog if success
        log.info(selectedIndex);
    }

    private void onCancel() {
        dispose();
    }

    @Subscribe
    public void onMessage(final ListHostsResponseEvent event) {
        AvailableHostsResponsePayload payload = (AvailableHostsResponsePayload) event.getPdu().getPayload();
        hosts = payload.getHosts();

        list.setListData(payload.getNames());


    }


}
