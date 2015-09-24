package com.zm.access;

import com.zm.tcptools.BufferMgr;
import com.zm.tcptools.ByteUtils;
import com.zm.tcptools.Data;
import com.zm.tcptools.DataType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhangmin on 2015/8/27.
 */
public class Transfer implements Runnable {
    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("Usage: java Transfer port result[0/1]");
            return;
        }
        new Transfer(Integer.parseInt(args[0]), args[1]);
    }

    public Transfer(int port, String result) throws IOException {
        serverSocket = new ServerSocket(port);
        bufferMgr = new BufferMgr();
        bufferMgr.put(new Data(DataType.FOURBYTES, "protocolversion", "200"));
        bufferMgr.put(new Data(DataType.FOURBYTES, "seq", "1"));
        bufferMgr.put(new Data(DataType.ONEBYTE, "command", "60"));
        bufferMgr.put(new Data(DataType.ONEBYTE, "result", result));
        bufferMgr.encode();

        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true){
            try {
                Tcp tcp = new Tcp(serverSocket.accept());
                tcp.rec();
                tcp.send(bufferMgr.getBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ServerSocket serverSocket;
    BufferMgr bufferMgr;
}

class Tcp{
    public void send(byte[] data) throws IOException {
        out.write(data);
        out.flush();

        System.out.println("================发送================");
        System.out.println(ByteUtils.bytes2HexGoodLook(data));

        out.close();
        socket.close();
    }

    public void rec() throws IOException {
        byte[] data = new byte[1024];
        int len = in.read(data);
        System.out.println("================接收================");
        System.out.println(ByteUtils.bytes2HexGoodLook(ByteUtils.subBytes(data, 0, len)));
    }

    public Tcp(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedInputStream(socket.getInputStream());
        out = new BufferedOutputStream(socket.getOutputStream());
    }

    Socket socket = null;
    BufferedOutputStream out = null;
    BufferedInputStream in = null;
}