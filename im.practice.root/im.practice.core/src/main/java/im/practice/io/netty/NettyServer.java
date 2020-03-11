package im.practice.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline=socketChannel.pipeline();
                        channelPipeline.addLast(new FixedLengthFrameDecoder(20));
                        channelPipeline.addLast("decoder", new StringDecoder());
                        channelPipeline.addLast("encoder", new StringEncoder());
                        channelPipeline.addLast(new FixedLengthFrameEncoder(20));
                        channelPipeline.addLast(new NettyServerHandler());
                    }
                });
        try {
            ChannelFuture cf=serverBootstrap.bind(8099).sync();
            System.out.println("服务端已经启动,监听端口："+8099);
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
