package sample.model.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:43
 * good luck
 **/
public class SendMsgToClientRunnable implements Runnable {
//    public Socket socket;
//    public SendMsgToClientRunnable(Socket socket){
//        this.socket = socket;
//    }

    public List<Socket> sockets;
    public SendMsgToClientRunnable(List<Socket> sockets){
        this.sockets = sockets;
    }

    @Override
    public void run() {
        BufferedWriter bw = null;
        //1.read,数据来源于键盘输入，要建立本地输入流，获取键盘输入
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            //3.keybord read ->send
            String tempLine = null;
            while((tempLine = br.readLine())!=null){
                //循环所有客户端连接的socket，分别输出到客户端
                for(Socket socket:sockets){
                    //2.new inputstream
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    bw = new BufferedWriter(osw);

                    bw.write(tempLine);
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
