package sample.model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:26
 * good luck
 **/
public class TestChatServer {

    public static void main(String[] args) {
        ServerSocket ss = null;
        //1.new ServerSocket
        try {
            ss = new ServerSocket(8888);

            //4.创建写信息的线程，开启写服务
            //创建socket集合，用来保存所有接入的客户端socket
            List<Socket> sockets = new ArrayList<Socket>();
            SendMsgToClientRunnable sendMsgToClientRunnable = new SendMsgToClientRunnable(sockets);
            Thread sendThread = new Thread(sendMsgToClientRunnable);
            sendThread.start();
            System.out.println("---服务端等待客户端连入---");
            while (true){
                //2.阻塞监听客户端连接，如果有客户端接入，返回socket对象
                Socket socket = ss.accept();
                System.out.println("有新客户端接入。。。。");
                //如果有客户端接入，则存入socket集合中
                sockets.add(socket);
                //3.创建读信息线程，开启读信息服务
                ReadClientMsgRunnable readMsg = new ReadClientMsgRunnable(socket,sockets);
                Thread readThread = new Thread(readMsg);
                readThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ss!=null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void serverStartApp(){

    }
}
