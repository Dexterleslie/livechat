package com.dex.livechat.codec;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dex.livechat.processor.RequestProcessor;
import com.dex.livechat.processor.user.CreateUserRequestProcessor;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonServerDecoder extends SimpleChannelInboundHandler<String>{
	private Map<String,RequestProcessor> requestProcessors=null;
	
	public JsonServerDecoder(){
		this.requestProcessors=new HashMap<String,RequestProcessor>();
		RequestProcessor processor=new CreateUserRequestProcessor();
		this.requestProcessors.put(processor.getAction(),processor);
	}
	
	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, String json) throws Exception {
		JSONObject objTemp1=JSON.parseObject(json);
		
		if(!objTemp1.containsKey("action"))
			throw new Exception("非法请求！");
		
		String action=objTemp1.getString("action");
		if(StringUtils.isBlank(action))
			throw new Exception("非法请求！");
		
		if(!this.requestProcessors.containsKey(action))
			throw new Exception("action:"+action+"没有对应处理逻辑");
		
		RequestProcessor processorTemp=this.requestProcessors.get(action);
		if(processorTemp==null)
			throw new Exception("action:"+action+"没有对应处理逻辑");
		
		JSONObject params=objTemp1.getJSONObject("params");
		
		Map<String,Object> mapResponse=new HashMap<String,Object>();
		try{
			Thread.sleep(5000);
			Object retObject=processorTemp.process(params);
			mapResponse.put("errorCode",-1);
			if(retObject!=null)
				mapResponse.put("dataJson",JSON.toJSONString(retObject));
		}catch(Exception ex){
			ex.printStackTrace();
			mapResponse.put("errorCode",5000);
			mapResponse.put("errorMessage",ex.getMessage());
		}finally{
			String jsonResponse=JSON.toJSONString(mapResponse);
			ctx.writeAndFlush(jsonResponse);
		}
		
		
//		final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//		final ChannelFuture f = ctx.writeAndFlush(time); // (3)
////        f.addListener(new ChannelFutureListener() {
////            @Override
////            public void operationComplete(ChannelFuture future) {
////                assert f == future;
////                ctx.close();
////            }
////        });
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {   
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) //flush掉所有写回的数据  
        .addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel  
    }  
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
