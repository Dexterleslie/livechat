package com.dex.livechat.processor;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求处理器
 * @author Dexter.chan
 *
 */
public interface RequestProcessor {
	public String getAction();
	public Object process(JSONObject params) throws Exception;
}
