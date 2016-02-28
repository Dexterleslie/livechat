package com.dex.livechat.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonClientDecoder extends SimpleChannelInboundHandler<String> {

	private String response=null;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		this.response=msg;
	}

	public String getResponse(){
		return this.response;
	}
}
