package com.yuwang.pinju.web.message;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @see
 * <p>Discription: 
 * 	 退款资源加载器
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-6
 */
public class RefundMessage {

	private final static Log log = LogFactory.getLog(RefundMessage.class);
	
	private final static String MESSAGE_PROPERTY_FILENAME = "refund_message.properties";
	private static Properties properties;

	static {
		try {
			loadMessage();
		} catch (IOException e) {
			throw new IllegalStateException("load message resource " + MESSAGE_PROPERTY_FILENAME + " error", e);
		}
	}

	/**
	 * 
	 * @see com.yuwang.pinju.web.message.RefundMessage.java
	 * <p>Discription: 
	 * 	 
	 * </p>
	 * @param name
	 * @param args
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-6
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static String getMessage(String name, Object... args) {
		String message = properties.getProperty(name);
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
	 * 
	 * @see com.yuwang.pinju.web.message.RefundMessage.java
	 * <p>Discription: 
	 * 	 
	 * </p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-6
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
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


