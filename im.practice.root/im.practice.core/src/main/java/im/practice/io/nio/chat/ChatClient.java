package im.practice.io.nio.chat;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * class_name: $
 * package: im.practice.io.nio.chat$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class ChatClient {
    public void start(String name)throws Exception{
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",8700));

        Selector selector=Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        ChatClientHandler chatClientHandler=new ChatClientHandler(selector);
        new Thread(chatClientHandler).start();

        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String request=scanner.nextLine();
            if(request!=null && request.length()>0)
            socketChannel.write(Charset.forName("UTF-8").encode(name+" : "+request));
        }
    }
}
