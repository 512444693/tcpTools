package testtools.sessionmsg;

import java.net.UnknownHostException;

/**
 * Created by zhangmin on 2015/5/5.
 */
public class Test extends Transfer implements Runnable {
    @Override
    public void run() {
        this.send(pingBuffer);
        while(true){
        }

    }
    public Test(ConnectType connectType, String host, int port) throws UnknownHostException {
        super(connectType, host, port);
        new Thread(this).start();
    }

    public static void main(String[] args)
    {
        try {
            for(int i=0; i<100; i++)
                new Test(ConnectType.TCP, "192.168.202.81", 9988);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
