package sample.model.client;

import java.io.*;
import java.net.Socket;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:33
 * good luck
 **/
public class SendMsgRunnable implements Runnable{
    public Socket socket;
    public BufferedWriter bw = null;

    public SendMsgRunnable(Socket socket){
        this.socket = socket;
        //创建输出流，用来往服务端发消息
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("bw出错了");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //定义本地输入
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            //开始从键盘输入读数据，发送给服务端
            String tempLine = null;
            while((tempLine = br.readLine())!= null){
                bw.write(tempLine);
                bw.newLine();
                bw.flush();
                //如果键盘输出byebye则退出循环读
//                if("byebye".equals(tempLine)){
//                    break;
//                }
            }
            //退出写信息的时候，只关闭socket的输出流
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //不能直接关闭输出流，要根据具体的socket通道中的outputstream和inputstream都shotdown之后再关闭
            while (socket.isInputShutdown() && socket.isOutputShutdown()){
                if(bw!=null){
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //关闭流后退出循环
                break;
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
