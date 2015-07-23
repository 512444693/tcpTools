package com.zm.mxlbarrage;

import com.zm.tcptools.BufferMgr;
import com.zm.tcptools.ByteUtils;
import com.zm.tcptools.Data;
import com.zm.tcptools.DataType;

import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * Created by zhangmin on 2015/7/22.
 */
public class Transfer {
    public Transfer(String ip, int port){
        this.ip = ip;
        this.port = port;
        try {
            socket = new DatagramSocket();
            udpSend = new UdpSend(socket);
            udpReceive = new UdpReceive(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data){
        udpSend.send(data, ip, port);
    }

    public void sendQuery(String gcid, String info){
        System.out.println("3 "+new Date().toLocaleString());
        BufferMgr  bufferMgr = new BufferMgr();
        System.out.println("4 "+new Date().toLocaleString());
        try {
            bufferMgr.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
            bufferMgr.put(new Data(DataType.ONEBYTE, "command", "60"));
            bufferMgr.put(new Data(DataType.HEXSTRING, "gcid", gcid));
            bufferMgr.put(new Data(DataType.STRING, "info", info));
            System.out.println("5 "+new Date().toLocaleString());
            udpSend.send(bufferMgr.getBuffer(), ip, port);
            System.out.println("6 "+new Date().toLocaleString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendPing(String gcid){
        BufferMgr  bufferMgr = new BufferMgr();
        try {
            bufferMgr.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
            bufferMgr.put(new Data(DataType.ONEBYTE, "command", "64"));
            bufferMgr.put(new Data(DataType.HEXSTRING, "gcid", gcid));
            udpSend.send(bufferMgr.getBuffer(), ip, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendExit(String gcid){
        BufferMgr  bufferMgr = new BufferMgr();
        try {
            bufferMgr.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
            bufferMgr.put(new Data(DataType.ONEBYTE, "command", "66"));
            bufferMgr.put(new Data(DataType.HEXSTRING, "gcid", gcid));
            udpSend.send(bufferMgr.getBuffer(), ip, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DatagramSocket socket;
    private String ip;
    private int port;
    private  UdpSend udpSend;
    private  UdpReceive udpReceive;
}

class UdpSend{
    public UdpSend(DatagramSocket socket){
        this.socket = socket;
    }
    public void send(byte[] data, String ip, int port){
        try {
            packet = new DatagramPacket(data, 0, data.length, InetAddress.getByName(ip), port);
            socket.send(packet);
            MyFrame.append(1, null, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DatagramSocket socket;
    private DatagramPacket packet;
}

class UdpReceive implements Runnable{

    @Override
    public void run() {
        while(true)
        {
            try {
                socket.receive(packet);
                MyFrame.append(2, null, ByteUtils.subBytes(data, 0 ,packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UdpReceive(DatagramSocket socket){
        this.socket = socket;
        packet = new DatagramPacket(data, data.length);
        new Thread(this).start();
    }

    DatagramSocket socket;
    DatagramPacket packet;
    byte[] data = new byte[1024];
}