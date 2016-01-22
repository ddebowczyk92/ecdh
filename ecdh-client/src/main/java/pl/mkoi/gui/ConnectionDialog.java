package pl.mkoi.gui;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import pl.mkoi.AppContext;
import pl.mkoi.client.Connection;
import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ProtocolHeader;
import pl.mkoi.ecdh.communication.protocol.payload.Payload;
import pl.mkoi.ecdh.communication.protocol.payload.ServerHelloResponsePayload;
import pl.mkoi.gui.util.RegexFormatter;
import pl.mkoi.model.ServerAddressDetails;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.NumberFormat;

public class ConnectionDialog extends JDialog {

    private static final Logger log = Logger.getLogger(ConnectionDialog.class);
    private static final String IP_REGEX = "\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField nickname;
    private JFormattedTextField ipAddress;
    private JFormattedTextField portNumber;
    private JLabel message;

    public ConnectionDialog() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setSize(getPreferredSize());
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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

        portNumber.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));

        message.setVisible(false);

        setTitle("Connection parameters setup");

    }

    private void cleanUp() {
        this.portNumber.setText("");
        this.ipAddress.setText("");
        this.nickname.setText("");
        this.message.setText("");
    }

    private void disableEverything() {
        this.portNumber.setEnabled(false);
        this.ipAddress.setEnabled(false);
        this.nickname.setEnabled(false);
        this.buttonOK.setEnabled(false);
        this.buttonCancel.setEnabled(false);
    }

    private void enableEverything() {
        this.portNumber.setEnabled(true);
        this.ipAddress.setEnabled(true);
        this.nickname.setEnabled(true);
        this.buttonOK.setEnabled(true);
        this.buttonCancel.setEnabled(true);
    }

    private void onOK() {
        ServerAddressDetails details = new ServerAddressDetails();
        StringBuilder errorBuilder = new StringBuilder();
        String portString = portNumber.getText();
        String ipString = ipAddress.getText();

        boolean isDataValid = true;
        this.message.setText("");

        if (isIpValid(ipString)) {
            details.setIpAddress(ipString);
        } else {
            isDataValid = false;
            errorBuilder.append("Invalid IP address\n");
        }

        if (!Strings.isNullOrEmpty(portString)) {

            long port = 0;
            try {
                port = Long.parseLong(portString.replaceAll("\u00A0", ""));
                if (isPortValid(port)) {
                    details.setPort(port);
                } else {
                    errorBuilder.append("Port number is invalid");
                    isDataValid = false;
                }
            } catch (NumberFormatException e) {
                errorBuilder.append("Invalid port number format");
                isDataValid = false;
            }

        } else {
            errorBuilder.append("Provide port number");
            isDataValid = false;
        }
        details.setNickName(nickname.getText());
        AppContext.getInstance().setUserNickName(details.getNickName());
        if (isDataValid) {

            tryToConnect(details);
        } else {
            showMessage(errorBuilder.toString());
        }


    }

    private boolean isIpValid(String ip) {
        boolean result = true;
        if (!Strings.isNullOrEmpty(ip)) {
            String[] split = ip.split("\\.");
            try {
                for (String s : split) {
                    String trimmed = s.trim();
                    if (Integer.parseInt(trimmed) > 255 || Integer.parseInt(trimmed) < 0) {
                        result = false;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                result = false;
            }
        }
        return result;
    }

    private boolean isPortValid(long portNumber) {
        boolean result = false;
        if (portNumber < 65536 && portNumber > 0) result = true;

        return result;
    }

    private void tryToConnect(final ServerAddressDetails details) {
        showMessage("Trying to connect...");
        final String name = nickname.getText().trim();
        SwingWorker<Boolean, Integer> worker = new SwingWorker() {
            @Override
            protected Boolean doInBackground() throws Exception {

                disableEverything();
                Socket clientSocket = new Socket();
                try {
                    clientSocket.connect(new InetSocketAddress(details.getIpAddress(), (int) details.getPort()), 10000);
                    Connection clientConnection = new Connection(clientSocket);
                    clientConnection.start();
                    AppContext context = AppContext.getInstance();
                    context.setClientConnection(clientConnection);
                    context.setConnectedToServer(true);

                    ProtocolHeader header = new ProtocolHeader(MessageType.SERVER_HELLO_RESPONSE);
                    header.setSourceId(context.getUserId());
                    Payload payload = new ServerHelloResponsePayload(name);

                    ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);
                    context.getClientConnection().sendMessage(pdu);

                    showMessage("Connected");
                    dispose();

                } catch (IOException e) {
                    enableEverything();
                    showMessage("Failed to connect");
                    log.error("Connection exception", e);
                }
                return true;
            }

        };
        worker.execute();

    }

    private void onCancel() {
        cleanUp();
        dispose();
    }

    private void showMessage(String message) {
        this.message.setText(message);
        this.message.setVisible(true);
    }

    private void hideError() {
        message.setText("");
        message.setVisible(false);
    }

    private void createUIComponents() {
        ipAddress = new JFormattedTextField(new RegexFormatter(IP_REGEX));
    }
}
