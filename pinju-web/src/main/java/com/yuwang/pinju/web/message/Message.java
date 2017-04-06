package com.yuwang.pinju.web.message;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p></p>
 * @author gaobaowen
 * 2011-6-10 上午09:13:27
 */
public class Message {

	private final static Log log = LogFactory.getLog(Message.class);
	
	/**
	 * 资源文件名称
	 */
	private final static String MESSAGE_PROPERTY_FILENAME = "message.properties";
	private static Properties properties;

	static {
		try {
			loadMessage();
		} catch (IOException e) {
			throw new IllegalStateException("load message resource " + MESSAGE_PROPERTY_FILENAME + " error", e);
		}
	}

	/**
	 * <p>通过 {@link MessageName} 中的键名字获取资源文字。</p>
	 *
	 * @param name  资源的名字
	 * @param args  资源需要填充的参数，用于填充 {0}、{1} 之类的占位符
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static String getMessage(String name, Object... args) {
		String message = properties.getProperty(name);
		if (StringUtils.isBlank(message)) {
			return null;
		}
		if(args == null || args.length == 0) {
			return message;
		}
		return MessageFormat.format(message, args);
	}

	private static void loadMessage() throws IOException {
		if(properties != null) {
			return;
		}
		properties = new Properties();
		properties.load( getInputStream() );
	}

	/**
	 * <p>加载资源文件</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	private static InputStream getInputStream() {

		// 从当前类加载器中加载资源
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(MESSAGE_PROPERTY_FILENAME);
		if(is != null) {
			log.info("load message file " + MESSAGE_PROPERTY_FILENAME + " from current thread class loader context");
			return is;
		}

		log.info("cannot load message file " + MESSAGE_PROPERTY_FILENAME + " from current thread class loader context");

		// 从系统类加载器中加载资源
		is = ClassLoader.getSystemResourceAsStream(MESSAGE_PROPERTY_FILENAME);
		if(is != null) {
			log.info("load message file " + MESSAGE_PROPERTY_FILENAME + " from system class loader context");
			return is;
		}
		throw new IllegalStateException("cannot find " + MESSAGE_PROPERTY_FILENAME + " anywhere");
	}
}
