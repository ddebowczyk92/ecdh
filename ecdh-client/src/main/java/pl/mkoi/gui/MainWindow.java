package pl.mkoi.gui;

import com.google.common.eventbus.Subscribe;
import pl.mkoi.AppContext;
import pl.mkoi.ecdh.communication.protocol.*;
import pl.mkoi.ecdh.event.SimpleMessageEvent;

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

        setJMenuBar(menuBar);
        setVisible(true);
    }

    @Subscribe
    public void onMessage(final SimpleMessageEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ServerHelloPayload payload = (ServerHelloPayload) event.getPdu().getPayload();
                StringBuffer buffer = new StringBuffer(logTextPane.getText());
                buffer.append(payload.getId() + "\n");
                logTextPane.setText(buffer.toString());
            }
        });
    }
}
