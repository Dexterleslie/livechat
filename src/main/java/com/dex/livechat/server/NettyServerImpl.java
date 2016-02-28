package com.dex.livechat.server;

import com.dex.livechat.codec.HttpRequestHandler;
import com.dex.livechat.codec.TextWebSocketFrameHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 基于Netty服务器端
 * @author Dexter.chan
 *
 */
public class NettyServerImpl implements Server {

	private EventLoopGroup bossGroup=null;
	private EventLoopGroup workerGroup=null;
	
	private ChannelFuture channelFuture=null;
	
	private int port=9999;
	
	@Override
	public void start() throws Exception {
		bossGroup = new NioEventLoopGroup();   
        workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();   
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)   
                    .childHandler(new ChannelInitializer<SocketChannel>() {   
                                @Override  
                                public void initChannel(SocketChannel ch) throws Exception {  
                                	ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast(new HttpServerCodec());
                                    pipeline.addLast(new ChunkedWriteHandler());
                                    pipeline.addLast(new HttpObjectAggregator(64*1024));
                                    pipeline.addLast(new HttpRequestHandler("/ws"));
                                    pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                                    pipeline.addLast(new TextWebSocketFrameHandler());
//                                    pipeline.addLast(new FlashPolicyHandler());
                                }  
                            });   
  
            channelFuture = b.bind(port).sync(); 
            System.out.println("started server.");
        } finally {  
        	this.stop();
        }
	}

	@Override
	public void stop() throws Exception {
		channelFuture.channel().closeFuture().sync();  
		workerGroup.shutdownGracefully();  
        bossGroup.shutdownGracefully();  
	}

}
