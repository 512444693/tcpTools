package com.zm.sessionmsg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Date;
import com.zm.tcptools.*;
/**
 * Created by zhangmin on 2015/4/29.
 */
public class Transfer {
    public Transfer(ConnectType connectType, String host, int port) throws UnknownHostException {
        init();
        this.connectType = connectType;
        ip = host;
        this.port = port;
        if(connectType == ConnectType.TCP)
        {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 1000);
                out = new BufferedOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new IllegalStateException("连接服务器出错");
            }
            tcpReceive = new TcpReceive(socket);
            new Thread(tcpReceive).start();
        }
        else if(connectType == ConnectType.UDP)
        {
            try {
                datagramSocket = new DatagramSocket();
                udpReceive = new UdpReceive(datagramSocket);
                new Thread(udpReceive).start();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("连接类型错误");
    }

    public void send(Buffer buffer)
    {
        if(connectType == ConnectType.TCP)
        {
            try {
                out.write(buffer.bm.getBuffer());
                out.flush();
                Print.print("SEND\tTCP\t\t" + buffer.type, buffer.bm.getBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(connectType == ConnectType.UDP)
        {
            try {
                InetAddress inetAddress= InetAddress.getByName(ip);
                datagramSocket.send(
                        new DatagramPacket(buffer.bm.getBuffer(), buffer.bm.getBuffer().length, inetAddress, port));
                Print.print("SEND\tUDP\t\t"+buffer.type, buffer.bm.getBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() throws UnknownHostException {
        String gcid = "1234567890123456789012345678901234567891";
        String uid = "0";
        queryBuffer = new Buffer("query");
        queryBuffer.bm.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        queryBuffer.bm.put(new Data(DataType.FOURBYTES,"seq","1"));
        queryBuffer.bm.put(new Data(DataType.ONEBYTE,"command","58"));
        queryBuffer.bm.put(new Data(DataType.STRING, "gcid",gcid));
        queryBuffer.bm.encode();

        queryListBuffer = new Buffer("queryList");
        queryListBuffer.bm.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        queryListBuffer.bm.put(new Data(DataType.FOURBYTES, "seq", "1"));
        queryListBuffer.bm.put(new Data(DataType.ONEBYTE, "command", "60"));
        queryListBuffer.bm.encode();

        addBuffer = new Buffer("add");
        addBuffer.bm.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        addBuffer.bm.put(new Data(DataType.FOURBYTES,"seq","1"));
        addBuffer.bm.put(new Data(DataType.ONEBYTE,"command","52"));
        addBuffer.bm.put(new Data(DataType.STRING, "gcid",gcid));
        addBuffer.bm.put(new Data(DataType.FOURBYTES,"uid",uid));
        addBuffer.bm.put(new Data(DataType.ONEBYTE,"type","0"));
        addBuffer.bm.put(new Data(DataType.STRING,"username","abc"));
        addBuffer.bm.put(new Data(DataType.FOURBYTES,"channellist","2"));
        addBuffer.bm.put(new Data(DataType.FOURBYTES,"channelid1","100132"));
        addBuffer.bm.put(new Data(DataType.FOURBYTES,"channelid2","100148"));
        addBuffer.bm.encode();

        delBuffer = new Buffer("del");
        delBuffer.bm.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        delBuffer.bm.put(new Data(DataType.FOURBYTES,"seq","1"));
        delBuffer.bm.put(new Data(DataType.ONEBYTE,"command","52"));
        delBuffer.bm.put(new Data(DataType.STRING, "gcid",gcid));
        delBuffer.bm.put(new Data(DataType.FOURBYTES,"uid",uid));
        delBuffer.bm.put(new Data(DataType.ONEBYTE,"type","1"));
        delBuffer.bm.put(new Data(DataType.STRING,"username","abc"));
        delBuffer.bm.put(new Data(DataType.FOURBYTES,"channellist","0"));
        //delBuffer.bm.put(new Data(DataType.FOURBYTES,"channelid1","100132"));
        //delBuffer.bm.put(new Data(DataType.FOURBYTES,"channelid2","100148"));
        delBuffer.bm.encode();

        pingBuffer = new Buffer("ping");
        pingBuffer.bm.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        pingBuffer.bm.put(new Data(DataType.FOURBYTES,"seq","1"));
        pingBuffer.bm.put(new Data(DataType.ONEBYTE,"command","54"));
        pingBuffer.bm.put(new Data(DataType.STRING,"gcid",gcid));
        pingBuffer.bm.put(new Data(DataType.FOURBYTES,"uid",uid));
        pingBuffer.bm.encode();
    }

    ConnectType connectType;
    String ip;
    int port;

    Socket socket;
    DatagramSocket datagramSocket;

    TcpReceive tcpReceive;
    UdpReceive udpReceive;

    BufferedOutputStream out;
    Buffer queryBuffer;
    Buffer queryListBuffer;
    Buffer addBuffer;
    Buffer delBuffer;
    Buffer pingBuffer;
}

class TcpReceive implements Runnable{

    @Override
    public void run() {
        int len = 0;
        while(true)
        {
            try {
                if((len = in.read(dataTmp)) != -1 )
                {
                    data = ByteUtils.subBytes(dataTmp, 0, len);
                    Print.print("RECEIVE\tTCP", data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public TcpReceive(Socket socket){
        this.socket = socket;
        try {
            in = new BufferedInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Socket socket;
    BufferedInputStream in;
    byte[] dataTmp = new byte[1024];
    byte[] data = new byte[1024];
}

class UdpReceive implements Runnable{

    @Override
    public void run() {
        while(true)
        {
            try {
                datagramSocket.receive(datagramPacket);
                data = ByteUtils.subBytes(datagramPacket.getData(), 0, datagramPacket.getLength());
                Print.print("RECEIVE\tUDP", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UdpReceive(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
        datagramPacket = new DatagramPacket(dataTmp, dataTmp.length);
    }

    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;
    byte[] dataTmp = new byte[1024];
    byte[] data = new byte[1024];
}

class Print {
    synchronized public static void print(String additional, byte[] data)
    {
        System.out.println("---------------------------------------------------");
        System.out.println(additional+"\t\t"+new Date().toLocaleString());
        System.out.println(ByteUtils.bytes2HexGoodLook(data).toString());
        //num++;
        //System.out.println(num);
    }
    static int num = 0;
}

enum ConnectType{
    TCP,UDP
}

class Buffer{
    BufferMgr bm;
    String type;
    public Buffer(String type){
        bm = new BufferMgr();
        this.type = type;
    }
}