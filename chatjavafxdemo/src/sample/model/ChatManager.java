package sample.model;

import javafx.application.Platform;
import sample.model.server.ReadClientMsgRunnable;
import sample.model.server.SendMsgToClientRunnable;
import sample.view.MainWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午9:53
 * good luck
 **/
public class ChatManager {
    private String ip = "127.0.0.1";
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedWriter bw;
    private BufferedReader br = null;
    private MainWindow mainWindow;

    private ChatManager() {
//        try {
//            this.socket = new Socket(ip,8888);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static final ChatManager instance = new ChatManager();

    public static ChatManager getInstance() {
        return instance;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * 链接服务器
     */
//    public void connect(String ip, String account) {//ip username
    public void connect() {//ip username
        new Thread() {
            //        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("run start!");
                    socket = new Socket(ip, 8888);
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);
                    String tempLine = null;
                    //判断输出流是否未关闭，未关闭说明客户端的写线程没有下线，可以接着读;若关闭，说明客户端下线，则停止读
                    while (!socket.isOutputShutdown() && (tempLine = br.readLine()) != null) {
//                        mainWindow.addLeft(new Userdata().getHead(), tempLine);
                        System.out.println(tempLine);
                        String finalTempLine = tempLine;
                        //解决UI组件线程不安全问题
                        Platform.runLater(()->{
                            mainWindow.addLeft("head2", finalTempLine);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //判断socket中的输入流和输出流是否都关闭，若都关闭则关闭整个流
                    while (socket.isOutputShutdown() && socket.isInputShutdown()) {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        }.start();
//        });
//            @Override
//            public void run() {
//                    System.out.println("haha");
//                    socket = new Socket(ip,8888);
//                    in = new DataInputStream(socket.getInputStream());
//                    out = new DataOutputStream(socket.getOutputStream());
//                    String line;
//                    while ((line = in.readUTF()) != null) {
//                        System.out.println(line);
//                        String Msg = line + " ";
//                        mainWindow.addLeft(new Userdata().getHead(), Msg);
//                    }
//                    in.close();
//                    out.close();
//                    in = null;
//                    out = null;
//                }catch (IOException e){
//
//                }
//            }
//        }.start();
    }

    /**
     * 开启服务端
     */
    public void serverStart(){
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

    public void send(final String Msg) throws IOException {//发送消息  向服务器发送
        if (bw != null) {
            bw.write(Msg);
            bw.newLine();
            bw.flush();
        } else {
            System.out.println("发送失败");
        }

//        if (out != null) {
//            out.writeUTF(Msg);
//            out.flush();
//        } else {
////            controller.alert.setInformation("发送失败!");
////            controller.alert.exec();
//            System.out.println("out problem!");
//        }
    }

    public void read(final String Msg) throws IOException{

    }
}
