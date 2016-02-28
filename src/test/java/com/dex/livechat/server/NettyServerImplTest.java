package com.dex.livechat.server;

public class NettyServerImplTest {
	public static void main(String []args) throws Exception{
		Server server=new NettyServerImpl();
		server.start();
	}
}
