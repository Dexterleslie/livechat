package com.dex.livechat.test;

import org.junit.Test;

public class T1Test {
	
	@Test
	public void m1() throws Exception{
		int i=1;
		if(i==1)
			throw new Exception("This is a error for breaking compile");
	}
}
