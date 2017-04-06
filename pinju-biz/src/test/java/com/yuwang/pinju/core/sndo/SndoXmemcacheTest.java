package com.yuwang.pinju.core.sndo;

import org.unitils.spring.annotation.SpringBean;

import net.rubyeye.xmemcached.MemcachedClient;

import com.yuwang.pinju.biz.BaseTestCase;

public class SndoXmemcacheTest extends BaseTestCase {
	@SpringBean("memcachedClient")
	private MemcachedClient memcachedClient;
	
	
	/**
	 * 测试部署在本地的盛大缓存
	 * 
	 * @throws Exception
	 */
	public void testMemcached()throws Exception{
		memcachedClient.set("mike", 1111, "success");
		System.out.println(memcachedClient.get("mike"));
	}
}
