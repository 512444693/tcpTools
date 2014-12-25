package com.zm.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/24.
 */
public class Main{
    public static void main(String[] args)throws UnknownHostException{
        new Main().test3();
    }
    void test() throws UnknownHostException {
        byte[] data = new byte[0];
        byte b = (byte) Integer.parseInt("89");
        short s = (short) Integer.parseInt("89");
        int i = Integer.parseInt("89");
        long l = Integer.parseInt("89");
        String ip = "127.0.0.1";
        String str = "123";
        String hex = "123abcdef";

        //dataType a = dataType.EIGHTBYTES;
        //System.out.println(a);
        data = ByteUtils.bytesMerger(data,b);
        data = ByteUtils.bytesMerger(data,ByteUtils.short2Byte(s));
        data = ByteUtils.bytesMerger(data,ByteUtils.int2Bytes(i));
        data = ByteUtils.bytesMerger(data,ByteUtils.long2Bytes(l));
        data = ByteUtils.bytesMerger(data,ByteUtils.ip2Bytes(ip));
        data = ByteUtils.bytesMerger(data, str.getBytes());
        data = ByteUtils.bytesMerger(data,ByteUtils.hex2Bytes(hex));
        byte[] dataRec = null;
        try {
            if ((dataRec = Tcp.send("192.168.205.54", 8189, data)) != null)
            {
                System.out.println("client send: \r\n"+ByteUtils.bytes2HexGoodLook(data)+"\r\n");
                System.out.println("client rec: \r\n"+ByteUtils.bytes2HexGoodLook(dataRec));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送数据出错,服务器可能没启动哦");
        }
    }
    void test2() throws UnknownHostException,IllegalArgumentException {
        BufferMgr bufferMgr = new BufferMgr();
        bufferMgr.put(new Data(DataType.ONEBYTE,"onebyte","89"));
        bufferMgr.put(new Data(DataType.TWOBYTES,"twobytes","89"));
        bufferMgr.put(new Data(DataType.FOURBYTES,"fourbytes","89"));
        bufferMgr.put(new Data(DataType.IP,"ip","127.0.0.1"));
        bufferMgr.put(new Data(DataType.EIGHTBYTES,"eightbytes","89"));
        bufferMgr.put(new Data(DataType.STRING,"string","123"));
        bufferMgr.put(new Data(DataType.HEXSTRING,"hexstring","123abcdeeff012938374abcdef"));
        byte[] dataRec = null;
        try {
            if ((dataRec = Tcp.send("192.168.205.54", 8189, bufferMgr.getBuffer())) != null)
            {
                System.out.println("client send: \r\n"+ByteUtils.bytes2HexGoodLook(bufferMgr.getBuffer())+"\r\n");
                System.out.println("client rec: \r\n"+ByteUtils.bytes2HexGoodLook(dataRec));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送数据出错,服务器可能没启动哦");
        }
    }
    void test3() throws UnknownHostException,IllegalArgumentException {
        BufferMgr bufferMgr = new BufferMgr();

        Data[] ip1 = new Data[1];
        ip1[0] = new Data(DataType.IP,"ip1","127.0.0.1");
        Data[] ip2 = new Data[1];
        ip2[0] = new Data(DataType.IP,"ip2","127.0.0.2");
        ArrayList<Data[]> a =new ArrayList<Data[]>();
        a.add(ip1);
        a.add(ip2);

        Data[] ip3 = new Data[1];
        ip3[0] = new Data(DataType.IP,"ip3","127.0.0.3");
        Data[] ip4 = new Data[1];
        ip4[0] = new Data(DataType.IP,"ip4","127.0.0.4");
        ArrayList<Data[]> b =new ArrayList<Data[]>();
        b.add(ip3);
        b.add(ip4);

        Data[] task1 = new Data[2];
        task1[0] = new Data(DataType.STRING,"gcid1","zhangmin");
        task1[1] = new Data(DataType.ARRAY,"ips",a);
        Data[] task2 = new Data[2];
        task2[0] = new Data(DataType.STRING,"gcid2","xiaozhang");
        task2[1] = new Data(DataType.ARRAY,"ips",b);

        ArrayList<Data[]> arrayList = new ArrayList<Data[]>();
        arrayList.add(task1);
        arrayList.add(task2);


        bufferMgr.put(new Data(DataType.HEXSTRING,"eightbytes","255123ABC"));
        bufferMgr.put(new Data(DataType.ARRAY,"tasklist",arrayList));

        byte[] dataRec = null;
        try {
            if ((dataRec = Tcp.send("192.168.205.54", 8189, bufferMgr.getBuffer())) != null)
            {
                System.out.println("client send: \r\n"+ByteUtils.bytes2HexGoodLook(bufferMgr.getBuffer())+"\r\n");
                System.out.println("client rec: \r\n"+ByteUtils.bytes2HexGoodLook(dataRec));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送数据出错,服务器可能没启动哦");
        }
    }
}
