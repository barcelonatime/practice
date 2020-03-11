package im.practice.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * class_name: $
 * package: im.practice.io.netty$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p=socketChannel.pipeline();
                            p.addLast(new FixedLengthFrameDecoder(20));
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new FixedLengthFrameEncoder(20));
                            p.addLast(new NettyClientHandler());
                            //p.addLast(new ClientHandlerBak());
                        }
                    });
            ChannelFuture cf=bootstrap.connect("127.0.0.1",8099).sync();
            cf.channel().writeAndFlush("this is client");
            cf.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
