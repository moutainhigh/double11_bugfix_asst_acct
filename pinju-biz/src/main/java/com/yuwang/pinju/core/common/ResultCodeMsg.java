package com.yuwang.pinju.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Project: pinju-biz
 * @Description: 为业务层设置ResultCode
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午01:45:02
 * @update 2011-8-12 下午01:45:02
 * @version V1.0
 */
public class ResultCodeMsg {

	private final static Log log = LogFactory.getLog(ResultCodeMsg.class);
	
	/**
	 * 资源文件名称
	 */
	private final static String MESSAGE_PROPERTY_FILENAME = "resultcode.properties";
	private static Properties properties;

	static {
		try {
			loadMessage();
		} catch (IOException e) {
			throw new IllegalStateException("load message resource " + MESSAGE_PROPERTY_FILENAME + " error", e);
		}
	}

    /**
     * Created on 2011-8-13 
     * <p>Discription:[根据Key获取MSG]</p>
     * @param 
     * @return
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
	public static String getResultMessage(String name, Object... args) {
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
		properties.load(getInputStream());
	}

	/**
	 * Created on 2011-8-12 
	 * <p>Discription:[加载资源文件]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
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
