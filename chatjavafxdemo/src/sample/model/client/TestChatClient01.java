package sample.model.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:32
 * good luck
 **/
public class TestChatClient01 {
    public void clientStartApp(){
        try {
            //1.new
            Socket socket = new Socket("127.0.0.1",8888);
            //2.通过传入socket对象，创建发送信息的线程，往服务端发送信息
            SendMsgRunnable sendMsg = new SendMsgRunnable(socket);
            Thread sendThread = new Thread(sendMsg);
            sendThread.start();
            //3.通过传入socket对象，从服务端读信息
            ReadMsgRunnable readMsgRunnable = new ReadMsgRunnable(socket);
            Thread readThread = new Thread(readMsgRunnable);
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
