package sample.model.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @author yuanjie.wang
 * @create 2018-09-20 上午12:28
 * good luck
 **/
public class ReadClientMsgRunnable implements Runnable{
    public Socket socket;
    public List<Socket> sockets;
    public ReadClientMsgRunnable(Socket socket,List<Socket> sockets){
        this.socket = socket;
        this.sockets = sockets;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        //socket inputstream
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String tempLine = null;
            while((tempLine = br.readLine())!=null){
                System.out.println(tempLine);
                //是否有客户端下线，有的话删除集合中的socket
                if("byebye".equals(tempLine)){
                    sockets.remove(socket);
                }
                //转发给其他所有客户端
                for(Socket socketItem:sockets){
                    //本人的不发
                    if(socket.getPort() == socketItem.getPort()){
                        continue;
                    }
                    OutputStream os = socketItem.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
//                    tempLine = tempLine + "&&" + socketItem.getPort() + "&&";
                    bw.write(tempLine);
                    bw.newLine();
                    bw.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!= null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
