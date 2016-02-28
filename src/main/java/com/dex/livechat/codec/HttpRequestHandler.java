package com.dex.livechat.codec;

import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private final String wsUri;
    
    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }
     
    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest msg)
            throws Exception {
         
        if(wsUri.equalsIgnoreCase(msg.getUri())){
             
//            new RandomResponseGenerator(ctx).start();
             
            ctx.fireChannelRead(msg.retain());
        }
    }
}