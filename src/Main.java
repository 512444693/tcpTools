import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by zhangmin on 2014/12/24.
 */
public class Main{
    public static void main(String[] args)throws UnknownHostException{
        BufferMgr bufferMgr = new BufferMgr();
        bufferMgr.put(new Data(DataType.ONEBYTE,"onebyte","89"));
        bufferMgr.put(new Data(DataType.TWOBYTES,"twobytes","89"));
        bufferMgr.put(new Data(DataType.FOURBYTES,"fourbytes","89"));
        bufferMgr.put(new Data(DataType.IP,"ip","127.0.0.1"));
        bufferMgr.put(new Data(DataType.EIGHTBYTES,"eightbytes","89"));
        bufferMgr.put(new Data(DataType.STRING,"string","123"));
        bufferMgr.put(new Data(DataType.HEXSTRING,"hexstring","123abcdeeff012938374abcdef"));

        Data d1 = new Data(DataType.ONEBYTE,"1");bufferMgr.get(d1);System.out.println(d1.getDataValue());
        Data d2 = new Data(DataType.TWOBYTES,"1");bufferMgr.get(d2);System.out.println(d2.getDataValue());
        Data d3 = new Data(DataType.FOURBYTES,"1");bufferMgr.get(d3);System.out.println(d3.getDataValue());
        Data d4 = new Data(DataType.IP,"1");bufferMgr.get(d4);System.out.println(d4.getDataValue());
        Data d5 = new Data(DataType.EIGHTBYTES,"1");bufferMgr.get(d5);System.out.println(d5.getDataValue());
        Data d6 = new Data(DataType.STRING,"1");bufferMgr.get(d6);System.out.println(d6.getDataValue());
        Data d7 = new Data(DataType.HEXSTRING,"1");bufferMgr.get(d7);System.out.println(d7.getDataValue());
        Data d8 = new Data(DataType.HEXSTRING,"d8");bufferMgr.get(d8);System.out.println(d8.getDataValue());
    }
}
/*
public class Main{
    public static void main(String[] args)throws UnknownHostException{
        System.out.println();
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
*/
