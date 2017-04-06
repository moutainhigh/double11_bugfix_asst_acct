package com.yuwang.pinju.core.sndo;

import java.net.InetSocketAddress;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

import org.junit.Test;

import com.snda.cache.client.zmemcached.ZmemcachedClientFactory;
import com.snda.cache.client.zmemcached.index.DirectSocketIndexFetcher;


public class SndoXmemcachedOtherTest {
	@Test
	public void testXmemceached()throws Exception{
		ZmemcachedClientFactory zcf = new ZmemcachedClientFactory();
		  String indexKey = "com.yuwang.cache.index"; //每个业务应该有自己的indexKey
		  zcf.setIndexKey(indexKey);
		  zcf.setIndexFetcher(new DirectSocketIndexFetcher(new InetSocketAddress("10.245.130.182", 11211)));
		  zcf.setUpdateWhenCreation(true);
		  MemcachedClient mc = zcf.createZmemcachedClient(new XMemcachedClient());
		  //以下测试
		  //1111是有效时间，单位是秒
		  mc.set("mike", 1111, "hello world");
		  System.out.println(mc.get("mike"));
		  //务必关闭MemcachedClient
		  mc.shutdown();
	}
}
