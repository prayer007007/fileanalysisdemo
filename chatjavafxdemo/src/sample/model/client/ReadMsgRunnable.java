package sample.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:54
 * good luck
 **/
public class ReadMsgRunnable implements Runnable {
    public Socket socket;
    public ReadMsgRunnable(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        //1.从socket获取输入流
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            //2.然后读取发来的信息，循环读
            String tempLine = null;
            //判断输出流是否未关闭，未关闭说明客户端的写线程没有下线，可以接着读;若关闭，说明客户端下线，则停止读
            while(!socket.isOutputShutdown() && (tempLine=br.readLine())!=null){
                System.out.println(tempLine);
            }
            //读循环结束的时候，只关闭socket的输入流
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //判断socket中的输入流和输出流是否都关闭，若都关闭则关闭整个流
            while(socket.isOutputShutdown() && socket.isInputShutdown()){
                if(br!=null){
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
}
