package im.practice.io.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * class_name: $
 * package: im.practice.io.nio.reactor$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class NioReServer implements Runnable{

    private Selector selector;
    private Selector selectorRead;

    public NioReServer() throws Exception{
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket=serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8888));
        selector=Selector.open();
        selectorRead=Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务已经启动");
    }

    public void listener() throws Exception{
        while (true){
            int count =selector.select();
            if (count>0){
                Set<SelectionKey> selectionKeySet=selector.selectedKeys();
                Iterator<SelectionKey> iterator=selectionKeySet.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey=iterator.next();
                    if (selectionKey.isAcceptable()){
                        System.out.println("建立一个连接-----");
                        ServerSocketChannel serverSocketChannel=(ServerSocketChannel)selectionKey.channel();
                        SocketChannel socketChannel =serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selectorRead,selectionKey.OP_READ);
                    }
                    iterator.remove();
                }

            }
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println("扫描selectRead");
                int count =selectorRead.select();
                if (count>0){
                    Iterator<SelectionKey> iterator=selectorRead.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey selectionKey=iterator.next();
                        if (selectionKey.isReadable()){
                            SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                            int size=socketChannel.read(readBuffer);
                            System.out.println(new String(readBuffer.array(),0,size));
                            readBuffer.clear();

                            ByteBuffer wirteBuffer=ByteBuffer.allocate(1024);
                            wirteBuffer.put("111111".getBytes());
                            wirteBuffer.flip();
                            socketChannel.write(wirteBuffer);

                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
