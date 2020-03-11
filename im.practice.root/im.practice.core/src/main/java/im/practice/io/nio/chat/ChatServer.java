package im.practice.io.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * class_name: $
 * package: im.practice.io.nio.chat$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class ChatServer {
    public void start()throws Exception{
        Selector selector=Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8700));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务启动完成!!!!!");

        for(;;){
            int readSeletors=selector.select();
            if(readSeletors==0) continue;
            Set<SelectionKey> selectionKeySet=selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeySet.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();

                iterator.remove();

                if(selectionKey.isAcceptable()){
                    handAcceptable(serverSocketChannel,selector);
                }
                if(selectionKey.isReadable()){
                    handRead(selectionKey,selector);
                }
            }
        }
    }

    private void handAcceptable(ServerSocketChannel serverSocketChannel,Selector selector) throws Exception{
        //ServerSocketChannel serverSocketChannel=(ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel=serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
        socketChannel.write(Charset.forName("UTF-8").encode("您已经加入群聊,与其他人都不是好友关系，请注意隐私安全"));
    }

    private void handRead(SelectionKey selectionKey, Selector selector) throws Exception{
        SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        String request="";
        while (socketChannel.read(byteBuffer)>0){
            byteBuffer.flip();
            request +=Charset.forName("UTF-8").decode(byteBuffer);
        }
        final String msg=request;
        socketChannel.register(selector,SelectionKey.OP_READ);
        if(request.length()>0){
            Set<SelectionKey> selectionKeys=selector.keys();
            selectionKeys.forEach(sk->{
                Channel targetChannle=sk.channel();
                if(targetChannle instanceof SocketChannel && targetChannle!=socketChannel){
                    try {
                        ((SocketChannel)targetChannle).write(Charset.forName("UTF-8").encode(msg));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static void main(String[] args) throws Exception{
        ChatServer chatServer=new ChatServer();
        chatServer.start();
    }



}
