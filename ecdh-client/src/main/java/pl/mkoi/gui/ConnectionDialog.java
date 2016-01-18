package pl.mkoi.gui;

import pl.mkoi.model.ServerAddressDetails;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class ConnectionDialog extends JDialog {
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

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        portNumber.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));

        message.setVisible(false);
        try {
            ipAddress.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###.###")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setTitle("Podaj parametry");

    }

    private void onOK() {
// add your code here
//        dispose();
        ServerAddressDetails details = new ServerAddressDetails();

        String ip = ipAddress.getText();
        String[] split = ip.split("\\.");

        System.out.println(ip);

        for (String s : split) {
            if (Integer.parseInt(s) > 255) {

                showMessage("Numer IP jest błędny");
                return;
            }
            System.out.println(s);
        }

        details.setIpAddress(ip);
        details.setNickName(nickname.getText());

        long port = Long.parseLong(portNumber.getText().replaceAll("\u00A0", ""));
        portNumber.setText(String.valueOf(port));
        if (port > 65535) {
            showMessage("Numer portu jest za wysoki");
            return;
        }

        details.setPort(port);

        showMessage("Próba połaczenie");

        tryToConnect(details);
    }

    private void tryToConnect(ServerAddressDetails details) {
        //connect code here
    }

    private void onCancel() {
// add your code here if necessary
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
}
