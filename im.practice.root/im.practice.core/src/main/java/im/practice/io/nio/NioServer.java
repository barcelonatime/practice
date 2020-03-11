package im.practice.io.nio;

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
 * package: im.practice.io.nio$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class NioServer {
    private Integer flag=0;
    private Integer BLOCK=1024;
    private ByteBuffer sendBuffer=ByteBuffer.allocate(BLOCK);
    private ByteBuffer receiveBuffer=ByteBuffer.allocate(BLOCK);
    private Selector selector;
    public NioServer(){

    }
    public NioServer(Integer port) throws Exception{
        // 打开服务器套接字通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        // 服务器配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 检索与此通道关联的服务器套接字
        ServerSocket serverSocket=serverSocketChannel.socket();
        // 进行服务的绑定
        serverSocket.bind(new InetSocketAddress(port));
        // 通过open()方法找到Selector
        selector=Selector.open();
        // 注册到selector，等待连接
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("服务端已经启动,端口="+port);
    }

    private void listener() throws Exception{
        System.out.println();
        while (true){
            // 选择一组键，并且相应的通道已经打开
            selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                handKey(selectionKey);
                iterator.remove();
            }
        }
    }

    private void handKey(SelectionKey selectionKey) throws Exception{
        // 接受请求
        ServerSocketChannel server=null;
        SocketChannel client=null;
        String recevieText;
        String sendText;
        int count=0;
        // 测试此键的通道是否已准备好接受新的套接字连接。
        if(selectionKey.isAcceptable()){
            System.out.println("新建立了一个连接======");
            // 返回为之创建此键的通道。
            server=(ServerSocketChannel)selectionKey.channel();
            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client=server.accept();
            // 配置为非阻塞
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector,SelectionKey.OP_READ);
        }else if (selectionKey.isReadable()){
            // 返回为之创建此键的通道。
            client=(SocketChannel) selectionKey.channel();
            //将缓冲区清空以备下次读取
            receiveBuffer.clear();
            //读取服务器发送来的数据到缓冲区中
            count=client.read(receiveBuffer);
            if(count>0){
                recevieText=new String(receiveBuffer.array(),0,count);
                System.out.println("线程号="+Thread.currentThread().getId()+"，服务器接收到的数据为="+recevieText);
                client.register(selector,SelectionKey.OP_WRITE);
            }

        }else if (selectionKey.isWritable()){
            //将缓冲区清空以备下次写入
            sendBuffer.clear();
            // 返回为之创建此键的通道。
            client=(SocketChannel)selectionKey.channel();
            sendText="server is send："+flag++;
            //向缓冲区中输入数据
            sendBuffer.put(sendText.getBytes());
            //将缓冲区各标志复位,因为向里面put了数据标志被改变,要想从中读取数据发向服务器,就要复位
            sendBuffer.flip();
            //输出到通道
            client.write(sendBuffer);
            System.out.println("服务器返回数据="+sendText);
            client.register(selector,SelectionKey.OP_READ);
        }
    }

    public static void main(String[] args) {
        try {
            NioServer nioServer=new NioServer(8811);
            nioServer.listener();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
