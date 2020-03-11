package im.practice.io.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * class_name: $
 * package: im.practice.io.netty$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class NettyServerHandler extends ChannelHandlerAdapter {
    //有客户端连接时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client connect...");
    }

    //断开连接时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("one client disconnect...");
    }

    //接收客户端发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端："+msg.toString());
        /*InputStreamReader in=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(in);
        String result="";
        result=bufferedReader.readLine();
        ctx.write(result);
        ctx.flush();*/
        ctx.write(msg);
        ctx.flush();
    }
}
