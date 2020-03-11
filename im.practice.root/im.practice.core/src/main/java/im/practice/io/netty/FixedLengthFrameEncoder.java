package im.practice.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * class_name: $
 * package: im.practice.io.netty$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class FixedLengthFrameEncoder extends MessageToByteEncoder<String> {
    private int length;
    public FixedLengthFrameEncoder(int length) {
        this.length = length;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        if(s.length()<length){
            s=addSpace(s);
        }
        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer(s.getBytes()));
    }

    // 进行空格补全
    private String addSpace(String msg) {
        StringBuilder builder = new StringBuilder(msg);
        for (int i = 0; i < length - msg.length(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
