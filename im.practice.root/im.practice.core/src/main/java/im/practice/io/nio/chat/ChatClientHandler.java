package im.practice.io.nio.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
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
public class ChatClientHandler implements Runnable{
    private Selector selector;

    public ChatClientHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            for(;;){
                int readselects=selector.select();
                if(readselects==0) continue;
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> iterator=selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey sk=iterator.next();
                    iterator.remove();
                    if(sk.isReadable()){
                        SocketChannel socketChannel=(SocketChannel) sk.channel();
                        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                        String msg="";
                        while (socketChannel.read(byteBuffer)>0){
                            byteBuffer.flip();
                            msg += Charset.forName("UTF-8").decode(byteBuffer);
                        }
                        if(msg.length()>0){
                            System.out.println(msg);
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
