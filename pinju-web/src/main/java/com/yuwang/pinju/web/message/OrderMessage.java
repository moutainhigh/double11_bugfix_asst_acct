package com.yuwang.pinju.web.message;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import org.springside.modules.utils.PropertiesUtils;

/**
 * Created on 2011-7-20
 * <p>Discription: 
 * 	  取订单资源文件信息
 * </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderMessage {
	
	private static Properties properties;
	
	private final static String MESSAGE_PROPERTY_FILENAME= "classpath:/orders_message.properties";
	
	static {
		try {
			loadMessage();
		} catch (IOException e) {
			throw new IllegalStateException("load message resource " + MESSAGE_PROPERTY_FILENAME + " error", e);
		}
	}
	
	private static void loadMessage() throws IOException {
		if(properties != null) {
			return;
		}
		properties = PropertiesUtils.loadProperties(MESSAGE_PROPERTY_FILENAME);
	}
	
	public static String getMessage(String name, Object... args) {
		String message = properties.getProperty(name);
		if(args == null || args.length == 0) {
			return message;
		}
		return MessageFormat.format(message, args);
	}
}

