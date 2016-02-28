package com.dex.livechat.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
            TextWebSocketFrame msg) throws Exception {
        String message = msg.content().toString(io.netty.util.CharsetUtil.UTF_8);
        ctx.writeAndFlush(new TextWebSocketFrame("[server] receive message ["+message+"] successfully"));
    }
 
}
