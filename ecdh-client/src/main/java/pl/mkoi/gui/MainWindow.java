package pl.mkoi.gui;

import com.google.common.eventbus.Subscribe;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ProtocolHeader;
import pl.mkoi.ecdh.communication.protocol.payload.ConnectRequestPayload;
import pl.mkoi.ecdh.communication.protocol.payload.ConnectRequestResponsePayload;
import pl.mkoi.ecdh.communication.protocol.payload.SimpleMessagePayload;
import pl.mkoi.ecdh.communication.protocol.payload.diffiehellman.DHInvitePayload;
import pl.mkoi.ecdh.crypto.util.AesCipher;
import pl.mkoi.ecdh.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main window of client's application
 * <p/>
 * Created by DominikD on 2016-01-16.
 */
public class MainWindow extends JFrame {
    private final static int WIDTH = 600;
    private JPanel rootPanel;
    private JButton sendButton;
    private JFormattedTextField inputField;
    private JTextPane logTextPane;
    private JPanel messagePanel;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private ConnectionDialog connectionDialog;
    private AppContext context = AppContext.getInstance();

    public MainWindow() throws HeadlessException {
        super("ecdh");
        setupGui();
        context.registerListener(this);

    }

    private void setupGui() {
        setSize(new Dimension(WIDTH, WIDTH));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                if (AppContext.getInstance().getClientConnection() != null) {
                    AppContext.getInstance().getClientConnection().closeConnection();
                }

                System.exit(0);
            }
        });

        setTitle("ECDH");

        setContentPane(rootPanel);
        setResizable(false);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();

        optionsMenu = new JMenu("Options");
        final JMenuItem optionMenuItem = new JMenuItem("Setup connection");
        final JMenuItem userList = new JMenuItem("Available user list");

        optionsMenu.add(optionMenuItem);
        optionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionDialog == null) {
                    connectionDialog = new ConnectionDialog();
                }
                connectionDialog.setVisible(true);

                userList.setEnabled(true);

            }
        });
        menuBar.add(optionsMenu);


        optionsMenu.add(userList);
        userList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (context.isConnectedToServer()) {
                UserListDialog userListDialog = new UserListDialog();
                userListDialog.setVisible(true);

//                }
            }
        });

        userList.setEnabled(false);

        logTextPane.setEditable(false);

        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendDHInvite();
            }
        });
        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void sendDHInvite() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (context.isConnectedToServer() && context.getConnectedUserId() != -1) {
                    ProtocolHeader header = new ProtocolHeader();
                    header.setSourceId(context.getUserId());
                    header.setDestinationId(context.getConnectedUserId());
                    header.setMessageType(MessageType.DH_INVITE);
                    DHInvitePayload payload = new DHInvitePayload(context.getMyKeyPair().getPublicKey());
                    context.getClientConnection().sendMessage(new ProtocolDataUnit(header, payload));
                    sendButton.setEnabled(false);

                }
            }
        });
    }

    @Subscribe
    public void onDHResponse(DHResponseEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (context.isConnectedToServer() && context.getConnectedUserId() != -1) {
                    ProtocolHeader header = new ProtocolHeader();
                    header.setSourceId(context.getUserId());
                    header.setDestinationId(context.getConnectedUserId());
                    header.setMessageType(MessageType.SIMPLE_MESSAGE);
                    String encryptedMessage = AesCipher.encrypt(context.getCommonKey(), inputField.getText());
                    SimpleMessagePayload payload = new SimpleMessagePayload(encryptedMessage);
                    ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);
                    context.getClientConnection().sendMessage(pdu);
                    StringBuffer buffer = new StringBuffer(logTextPane.getText());
                    buffer.append("[" + header.getTimestamp() + "] ");
                    buffer.append("You: " + inputField.getText() + "\n");
                    logTextPane.setText(buffer.toString());
                    inputField.setText("");

                }
            }
        });
    }

    @Subscribe
    public void onMessage(final SimpleMessageEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleMessagePayload payload = (SimpleMessagePayload) event.getPdu().getPayload();
                String decrypted = AesCipher.decrypt(context.getCommonKey(), payload.getMessage());
                StringBuffer buffer = new StringBuffer(logTextPane.getText());
                buffer.append("[" + event.getPdu().getHeader().getTimestamp() + "] ");
                buffer.append(context.getConnectedUserName() + " sent: " + decrypted + "\n");
                context.setHasKeyBeenUsed(true);
                logTextPane.setText(buffer.toString());
            }
        });
    }

    @Subscribe
    public void onMessageResponse(SimpleMessageResponseEvent event) {
        sendButton.setEnabled(true);
    }

    @Subscribe
    public void onConnectionRequest(ConnectionRequestEvent event) {
        showConnectionRequestDialog(event.getPdu());
    }

    @Subscribe
    public void onConnectionRequestResponse(ConnectionRequestResponseEvent event) {
        ProtocolDataUnit pdu = event.getPdu();
        ProtocolHeader header = pdu.getHeader();
        ConnectRequestResponsePayload payload = (ConnectRequestResponsePayload) pdu.getPayload();
        showConnectionRequestResponseDialog(payload.hasAccepted());
        if (payload.hasAccepted()) {
            context.setConnectedUserId(header.getSourceId());
            context.setConnectedWithUser(true);
            sendButton.setEnabled(true);
        }
    }


    private void showConnectionRequestDialog(ProtocolDataUnit pdu) {
        ProtocolHeader header = pdu.getHeader();
        ConnectRequestPayload payload = (ConnectRequestPayload) pdu.getPayload();
        int n = JOptionPane.showConfirmDialog(this, payload.getNickname() + " wants to talk with you! Do you accept this connection request?", "Connection request", JOptionPane.YES_NO_OPTION);
        boolean response = false;
        if (n == 0) {
            response = true;
            context.setConnectedUserId(header.getSourceId());
            context.setConnectedUserName(payload.getNickname());
            sendButton.setEnabled(true);
        } else if (n == 1) {
            response = false;
        }
        sendConnectionRequestResponse(header.getSourceId(), response);
    }

    private void showConnectionRequestResponseDialog(boolean accept) {
        String responseMessage;
        if (accept) {
            responseMessage = "Connection request accepted";
        } else {
            responseMessage = "Connection request rejected";
        }
        JOptionPane.showMessageDialog(this, responseMessage);
    }

    private void sendConnectionRequestResponse(final int destinationId, final boolean accept) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProtocolHeader header = new ProtocolHeader(MessageType.CLIENT_CONNECT_RESPONSE);
                header.setDestinationId(destinationId);
                ConnectRequestResponsePayload payload = new ConnectRequestResponsePayload(accept);
                ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);
                context.getClientConnection().sendMessage(pdu);
            }
        });

    }
}
