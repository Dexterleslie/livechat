package com.dex.livechat.server;

/**
 * 服务器端启动或者关闭，接收客服端请求
 * @author Dexter.chan
 *
 */
public interface Server {
	/**
	 * 在默认端口启动服务器
	 * @throws Exception
	 */
	public void start() throws Exception;
	
	/**
	 * 关闭服务器
	 * @throws Exception
	 */
	public void stop() throws Exception;
}
