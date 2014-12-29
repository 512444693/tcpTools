import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2014/12/24.
 */
public class Tcp {
    public static void main(String[] args)
    {
        /*
        byte[] data = null;
        try {
            if((data = send("localhost",8189,"软件测试技术大全".getBytes())) != null)
                System.out.println(new String(data));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送数据出错");
        }
        */
    }
    public static byte[] send(String host, int port, byte[] data,JTextArea textSend,JTextArea textRec) throws IllegalStateException {
        Socket s = null;
        BufferedInputStream in = null;
        BufferedOutputStream out =null;
        byte[] dataRec = new byte[0];
        try {
            s = new Socket(host,port);
        } catch (IOException e) {
            throw new IllegalStateException("连接服务器错误，服务器没启动或ip、端口错误");
        }
        try {
            s.setSoTimeout(1000);
            in = new BufferedInputStream(s.getInputStream());
            out = new BufferedOutputStream(s.getOutputStream());

            out.write(data);
            out.flush();
            //s.shutdownOutput();
            textSend.setText(new Date().toLocaleString()+"\r\n\r\n"+ ByteUtils.bytes2HexGoodLook(data).toString());
            System.out.println("发送数据成功");
            byte[] dataTemp = new byte[2014];
            int len = 0;
            while((len = in.read(dataTemp)) != -1 )
            {
                dataRec = ByteUtils.bytesMerger(dataRec, dataTemp, 0, len);
            }
            s.shutdownInput();
            textRec.setText(new Date().toLocaleString()+"\r\n\r\n"+ ByteUtils.bytes2HexGoodLook(dataRec).toString());
        } catch (IOException e) {
            try {
                if(s != null)
                    s.close();
                if(in != null)
                    in.close();
                if(out != null)
                    out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.print("关闭资源异常");
            }
            throw new IllegalStateException("1秒内服务器没有回包，断开连接");
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
