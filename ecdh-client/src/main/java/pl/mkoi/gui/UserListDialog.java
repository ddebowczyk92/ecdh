package pl.mkoi.gui;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.*;

public class UserListDialog extends JDialog {
    private static final Logger log = Logger.getLogger(UserListDialog.class);
    private JPanel contentPane;
    private JPanel scrollContainer;
    private JList list;
    private JButton connectButton;
    private JButton refreshButton;
    private String[] data;

    public UserListDialog() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setSize(getPreferredSize());
        System.out.println(getPreferredSize());

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
                getUsersFromServer();
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryConnectToUser(list.getSelectedIndex());
            }
        });

        setTitle("Select user from list");

    }

    private void tryConnectToUser(int selectedIndex) {
        //try connect

        //dispose dialog if success
        log.info(selectedIndex);
    }

    private void onCancel() {
        dispose();
    }

    private void getUsersFromServer() {

        //

//        this.data =
//        list.setListData(this.data);
    }


}
