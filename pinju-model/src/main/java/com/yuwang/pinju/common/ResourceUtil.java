package com.yuwang.pinju.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>资源数据加载</p>
 *
 * @author gaobaowen
 * @since 2011-12-27 14:09:37
 */
public class ResourceUtil {

	private final static Log log = LogFactory.getLog(ResourceUtil.class);

	private ResourceUtil() {}

	/**
	 * <p>加载 properties 文件</p>
	 *
	 * @param name
	 * @return
	 * @throws IOException
	 *
	 * @author gaobaowen
	 * @since 2011-12-27 14:12:56
	 */
	public static Properties loadProperties( String name ) throws IOException {
		Properties properties = new Properties();
		properties.load( getInputStream(name) );
		return properties;
	}

	/**
	 * <p>加载资源文件</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @throws IOException
	 */
	private static InputStream getInputStream(String name) throws IOException {

		// 从当前类加载器中加载资源
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		if(is != null) {
			log.info("load message file " + name + " from current thread class loader context");
			return is;
		}

		log.info("cannot load message file " + name + " from current thread class loader context");

		// 从系统类加载器中加载资源
		is = ClassLoader.getSystemResourceAsStream(name);
		if(is != null) {
			log.info("load message file " + name + " from system class loader context");
			return is;
		}
		throw new IOException("cannot find " + name + " anywhere");
	}
}
