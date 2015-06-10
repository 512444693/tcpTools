package com.zm.sessionmsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

/**
 * Created by zhangmin on 2015/4/29.
 */
public class MsgTcp extends JFrame {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MsgTcp frame = new MsgTcp();
        //设置观感，"javax.swing.plaf.nimbus.NimbusLookAndFeel"  "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(frame);
        frame.setVisible(true);
    }
    public MsgTcp()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight= screenSize.height;
        int screenWidth= screenSize.width;
        setLocation(screenWidth / 3, screenHeight / 3);
        setSize(400, 75);
        setTitle("TTEST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        queryButton = new JButton("query");
        queryListButton = new JButton("queryList");
        addButton = new JButton("add");
        delButton = new JButton("del");
        pingButton = new JButton("ping");

        ButtonAction buttonAction = new ButtonAction(ConnectType.UDP);
        queryButton.setActionCommand("query");
        queryButton.addActionListener(buttonAction);
        queryListButton.setActionCommand("queryList");
        queryListButton.addActionListener(buttonAction);
        addButton.setActionCommand("add");
        addButton.addActionListener(buttonAction);
        delButton.setActionCommand("del");
        delButton.addActionListener(buttonAction);
        pingButton.setActionCommand("ping");
        pingButton.addActionListener(buttonAction);

        panel.add(queryButton);
        panel.add(queryListButton);
        panel.add(addButton);
        panel.add(delButton);
        panel.add(pingButton);

        this.add(panel);
    }
    JPanel panel;
    JButton queryButton;
    JButton queryListButton;
    JButton addButton;
    JButton delButton;
    JButton pingButton;
}
class ButtonAction implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case "query" :
                transfer.send(transfer.queryBuffer);
                break;

            case "queryList" :
                transfer.send(transfer.queryListBuffer);
                break;

            case "add" :
                transfer.send(transfer.addBuffer);
                break;

            case "del" :
                transfer.send(transfer.delBuffer);
                break;

            case "ping" :
                transfer.send(transfer.pingBuffer);
                break;

            default:
                return;
        }
    }
    public ButtonAction(ConnectType connectType)
    {
        try {
            transfer = new Transfer(connectType, "192.168.202.81", 9988);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    Transfer transfer;
}
