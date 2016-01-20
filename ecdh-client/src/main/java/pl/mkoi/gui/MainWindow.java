package pl.mkoi.gui;

import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.MessageType;
import pl.mkoi.ecdh.communication.protocol.ProtocolDataUnit;
import pl.mkoi.ecdh.communication.protocol.ProtocolHeader;
import pl.mkoi.ecdh.communication.protocol.SimpleMessagePayload;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
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

    public MainWindow() throws HeadlessException {
        super("ecdh");
        setupGui();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppContext context = AppContext.getInstance();
                if (context.isConnectedToServer()) {
                    ProtocolHeader header = new ProtocolHeader();
                    header.setMessageType(MessageType.SIMPLE_MESSAGE);
                    SimpleMessagePayload payload = new SimpleMessagePayload(inputField.getText());
                    ProtocolDataUnit pdu = new ProtocolDataUnit(header, payload);
                    context.getClientConnection().sendMessage(pdu);
                }
            }
        });
    }

    private void setupGui() {
        setSize(new Dimension(WIDTH, WIDTH));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        setTitle("ECDH");

        setContentPane(rootPanel);
        setResizable(false);
        setLocationRelativeTo(null);
        menuBar = new JMenuBar();
        optionsMenu = new JMenu("Options");
        JMenuItem optionMenuItem = new JMenuItem("Setup connection");
        optionsMenu.add(optionMenuItem);
        optionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionDialog == null) {
                    connectionDialog = new ConnectionDialog();
                }
                connectionDialog.setVisible(true);
            }
        });
        menuBar.add(optionsMenu);
        logTextPane.setEditable(false);

        setJMenuBar(menuBar);
        setVisible(true);
    }
}
