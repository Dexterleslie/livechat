package com.dex.livechat.client;

import com.dex.livechat.codec.JsonClientDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
	public static void main(String []args) throws InterruptedException{
		EventLoopGroup group = new NioEventLoopGroup();  
		try{
	        Bootstrap b = new Bootstrap();  
	        b.group(group).channel(NioSocketChannel.class);  
	        final JsonClientDecoder decoder=new JsonClientDecoder();
	        b.handler(new ChannelInitializer<Channel>() {  
	            @Override  
	            protected void initChannel(Channel ch) throws Exception {  
	                ChannelPipeline pipeline = ch.pipeline();  
//	                pipeline.addLast(new LengthFieldPrepender(4, false)); 
//	                pipeline.addLast(new StringDecoder());  
//	                pipeline.addLast(new StringEncoder()); 
//	                pipeline.addLast(decoder);
	            }  
	        });  
	        b.option(ChannelOption.SO_KEEPALIVE, true); 
	        Channel channel = b.connect("localhost", 8080).sync().channel(); 
	        byte[] data = "{action:'createUser',params:{loginName:'dexter'}}".getBytes();
	        ByteBuf bb = Unpooled.buffer();
	        bb.writeBytes(data, 0, data.length);
	        ChannelFuture f=channel.writeAndFlush(bb).sync();  
	     // Wait until the connection is closed.
            f.channel().closeFuture().await();
            System.out.println(decoder.getResponse());
        } finally {
            group.shutdownGracefully();
        }
	}
}
