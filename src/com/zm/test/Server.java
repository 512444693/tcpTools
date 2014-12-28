package com.zm.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Created by zhangmin on 2014/12/24.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8189);
        System.out.println("server start");
        while(true)
        {
            Socket s = ss.accept();
            //System.out.println("server get a client");
            BufferedInputStream in = new BufferedInputStream(s.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
            byte[] data = new byte[1024];
            int len;
            if((len = in.read(data)) != -1)
            {
                //System.out.print(new String(data,0,len));
                System.out.println(ByteUtils.bytes2HexGoodLook(ByteUtils.subBytes(data,0,len)));
            }
            s.shutdownInput();
            out.write("123".getBytes());
            out.flush();
            s.shutdownOutput();
        }

        /*
        out.close();
        in.close();
        s.close();
        ss.close();
        */
    }

}
