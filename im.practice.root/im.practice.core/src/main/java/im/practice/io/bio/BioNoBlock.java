package im.practice.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * class_name: $
 * package: im.practice.io.bio$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class BioNoBlock {
    public static void main(String[] args) {
        try {
            ServerSocket server=new ServerSocket(8811);
            System.out.println("服务端已经启动.............");

            while(true){
                final Socket socket=server.accept();
                System.out.println("来了一个新的客户端连接...........");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream=socket.getInputStream();
                            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                            while (true){
                                byte[] bytes=new byte[1024];
                                int flag=inputStream.read(bytes);
                                if(flag==-1){
                                    break;
                                }
                                String result=new String(bytes);
                                System.out.println(Thread.currentThread().getId()+":"+flag+":"+result);
                                out.println("ok");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
