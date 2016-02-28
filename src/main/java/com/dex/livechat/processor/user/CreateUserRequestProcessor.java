package com.dex.livechat.processor.user;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dex.livechat.processor.RequestProcessor;

public class CreateUserRequestProcessor implements RequestProcessor {

	@Override
	public String getAction() {
		return "createUser";
	}

	@Override
	public Object process(JSONObject params) throws Exception {
		System.out.println("params:"+params.toJSONString());
		Map<String,Object> mapRet=new HashMap<String,Object>();
		mapRet.put("k1","oookay!");
		return mapRet;
	}

}
