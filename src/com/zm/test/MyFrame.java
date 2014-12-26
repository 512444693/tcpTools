package com.zm.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2014/12/25.
 */
public class MyFrame extends JFrame {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MyFrame frame = new MyFrame();
        //设置观感，"javax.swing.plaf.nimbus.NimbusLookAndFeel"  "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(frame);
        frame.setVisible(true);


    }
    JTextArea textSend;
    JTextArea textRec;
    JPanel panelRight;

    JPanel panelConf;

    JButton buttonAdd;
    JButton buttonSend;
    JPanel panelOpt;

    JPanel panelLeft;
    public MyFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        init();

        textSend = new JTextArea("send");
        textRec = new JTextArea("rec");
        panelRight = new JPanel();
        panelRight.setLayout(new GridLayout(2,1,10,10));
        panelRight.add(textSend);
        panelRight.add(textRec);


        panelConf= new JPanel();

        buttonAdd = new JButton("添加数据");
        buttonAdd.addActionListener(new ButtonAddAction());
        buttonSend = new JButton("发送");
        buttonSend.addActionListener(new ButtonSendAction());
        panelOpt = new JPanel();
        panelOpt.add(buttonAdd);
        panelOpt.add(buttonSend);
        panelLeft = new JPanel();

        panelLeft.setLayout(new BorderLayout());
        panelLeft.add(panelConf,BorderLayout.CENTER);
        panelLeft.add(panelOpt,BorderLayout.SOUTH);


        this.setLayout(new GridLayout(1,2,10,10));
        this.add(panelLeft);
        this.add(panelRight);
    }
    class ButtonSendAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            panelOpt.add(new JButton("123"));

            //弹出提示框
            JOptionPane.showMessageDialog(null,"sorry");
            panelOpt.validate();
            //panelOpt.repaint();
            /*
            panelLeft.repaint();
            MyFrame.this.repaint();
            */
        }
    }
    class ButtonAddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            panelConf.add(new PanelData());
            panelConf.validate();
        }
    }
    class PanelData extends JPanel{
        private JTextField JTFDataType;
        private JTextField JTFDataName;
        private JTextField JTFDataValue;
        public PanelData(String dataType, String dataName, String dataValue)
        {
            JTFDataType = new JTextField(dataType,10);
            JTFDataName = new JTextField(dataName,10);
            JTFDataValue = new JTextField(dataValue,52);
            this.add(JTFDataType);
            this.add(JTFDataName);
            this.add(JTFDataValue);
        }
        public PanelData()
        {
            JTFDataType = new JTextField(10);
            JTFDataName = new JTextField(10);
            JTFDataValue = new JTextField(52);
            this.add(JTFDataType);
            this.add(JTFDataName);
            this.add(JTFDataValue);
        }

        public JTextField getJTFDataType() {
            return JTFDataType;
        }

        public void setJTFDataType(JTextField JTFDataType) {
            this.JTFDataType = JTFDataType;
        }

        public JTextField getJTFDataName() {
            return JTFDataName;
        }

        public void setJTFDataName(JTextField JTFDataName) {
            this.JTFDataName = JTFDataName;
        }

        public JTextField getJTFDataValue() {
            return JTFDataValue;
        }

        public void setJTFDataValue(JTextField JTFDataValue) {
            this.JTFDataValue = JTFDataValue;
        }
    }
    void init(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenHeight= screenSize.height;
        screenWidth= screenSize.width;
        setSize(screenWidth*2 / 3, screenHeight*2/ 3);
        //setLocationByPlatform(true);
        setLocation(screenWidth / 6, screenHeight / 6);
        setTitle("蜀道难");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private int screenHeight;
    private int screenWidth;
}
