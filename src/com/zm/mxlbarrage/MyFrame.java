package com.zm.mxlbarrage;

import com.zm.tcptools.ByteUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by zhangmin on 2015/7/22.
 */
public class MyFrame extends JFrame {
    public static void main(String[] args){
        MyFrame myFrame = new MyFrame();
        try {
            //设置样式
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(myFrame);
        myFrame.setVisible(true);
        /*
        for(int i=0; i<20 ;i++){
            myFrame.transfer.sendQuery("0123456789012345678901234567890123456789", new Integer(i).toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }

    public MyFrame(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight= screenSize.height;
        int screenWidth= screenSize.width;
        setLocation(screenWidth / 4, screenHeight / 4);
        setSize(screenWidth / 2, screenHeight / 2);
        setTitle("sdk用户");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        labelGcid = new JLabel("gcid:");
        labelInfo = new JLabel("info:");
        bal = new ButtonActionListener();
        buttonQuery = new JButton("请求");
        buttonQuery.setActionCommand("query");
        buttonQuery.addActionListener(bal);
        buttonPing = new JButton("报活");
        buttonPing.setActionCommand("ping");
        buttonPing.addActionListener(bal);
        buttonExit = new JButton("退出");
        buttonExit.setActionCommand("exit");
        buttonExit.addActionListener(bal);
        buttonClear = new JButton("clear");
        buttonClear.setActionCommand("clear");
        buttonClear.addActionListener(bal);
        textGcid = new JTextField(108);
        textGcid.setText("0123456789012345678901234567890123456789");
        textInfo = new JTextField(108);
        textInfo.setText("abcd");
        logArea = new JTextArea("", 17, 80);

        transfer = new Transfer("192.168.202.81", 19353);

        panel.add(labelGcid);
        panel.add(textGcid);
        panel.add(labelInfo);
        panel.add(textInfo);
        panel.add(buttonQuery);
        panel.add(buttonPing);
        panel.add(buttonExit);
        panel.add(buttonClear);

        panel.add(new JScrollPane(logArea));
        this.add(panel);
    }

    synchronized public static void append(int type, String str, byte[] data){
        String send = "------------------------------发送-----------------------\r\n";
        String rec = "------------------------------接收------------------------\r\n";
        switch (type){
            case 1: //发送包添加
                logArea.append(send + ByteUtils.bytes2HexGoodLook(data) + "\r\n");
                break;
            case 2: //收包添加
                logArea.append(rec + ByteUtils.bytes2HexGoodLook(data) + "\r\n");
                break;
            default: //普通添加
                logArea.append(str + "\r\n");
        }
        //滚动到底部
        logArea.setCaretPosition(logArea.getText().length());
    }

    synchronized public static void clear(){
        logArea.setText("");
    }


    private JPanel panel;
    private JButton buttonQuery;
    private JButton buttonPing;
    private JButton buttonExit;
    private JButton buttonClear;
    private JLabel labelGcid;
    private JTextField textGcid;
    private JLabel labelInfo;
    private JTextField textInfo;
    private static JTextArea logArea;

    private  ButtonActionListener bal;

    private Transfer transfer;

    class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "query" :
                    transfer.sendQuery(textGcid.getText(), textInfo.getText());
                    break;
                case "ping" :
                    transfer.sendPing(textGcid.getText());
                    break;
                case "exit" :
                    transfer.sendExit(textGcid.getText());
                    break;
                case "clear" :
                    clear();
                    break;
                default : return;
            }
        }
    }
}
