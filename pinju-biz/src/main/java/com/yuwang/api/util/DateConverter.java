/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 日期类型转换类
 * 
 * @author liyouguo
 * 
 * @since 2011-9-29
 */
public class DateConverter implements Converter {

	public DateConverter() {
		super();
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return Calendar.class.isAssignableFrom(clazz)
				|| Date.class.isAssignableFrom(clazz);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		SimpleDateFormat datetimeFmt = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.US);
		if (value instanceof Calendar) {
			Calendar calendar = (Calendar) value;
			Date date = calendar.getTime();
			writer.setValue(datetimeFmt.format(date));
		} else if (value instanceof Date) {
			writer.setValue(datetimeFmt.format(value));
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		SimpleDateFormat datetimeFmt = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.US);
		try {
			return datetimeFmt.parse(reader.getValue());
		} catch (ParseException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}

}
