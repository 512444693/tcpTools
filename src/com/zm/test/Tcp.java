package com.zm.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/24.
 */
public class Tcp {
    public static void main(String[] args)
    {
        byte[] data = null;
        try {
            if((data = send("localhost",8189,"软件测试技术大全".getBytes())) != null)
                System.out.println(new String(data));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送数据出错");
        }

    }
    public static byte[] send(String host, int port, byte[] data) throws IOException {
        Socket s = null;
        BufferedInputStream in = null;
        BufferedOutputStream out =null;
        byte[] dataRec = new byte[0];
        try {
            s = new Socket(host,port);
            in = new BufferedInputStream(s.getInputStream());
            out = new BufferedOutputStream(s.getOutputStream());

            out.write(data);
            out.flush();
            s.shutdownOutput();

            byte[] dataTemp = new byte[2014];
            int len = 0;
            while((len = in.read(dataTemp)) != -1 )
            {
                dataRec = ByteUtils.bytesMerger(dataRec,dataTemp,0,len);
            }
            s.shutdownInput();
        } catch (IOException e) {
           throw e;
        }
        finally {
                try {
                    if(s != null)
                        s.close();
                    if(in != null)
                        in.close();
                    if(out != null)
                        out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("关闭资源异常");
                }
        }
        return dataRec;
    }
}
