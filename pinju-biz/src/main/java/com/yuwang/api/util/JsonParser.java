package com.yuwang.api.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.Parser;

/**
 * 
 * @author xiazhenyu
 * 
 *         创建时间：2011-9-5
 */
public class JsonParser extends Parser {

	/**
	 * JSON解析
	 * 
	 * @param response
	 * @return JSON字符串
	 */
	@Override
	public String parser(ApiResponse response) {
//		XStream xStream = new XStream(new Sun14ReflectionProvider(),
//				new JettisonMappedXmlDriver());JsonHierarchicalStreamDriver
		XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
		xStream.registerConverter(new DateConverter());
		setAlias(xStream);
		xStream.autodetectAnnotations(true);
//		xStream.addImplicitCollection(ResponseDatas.class, "data");
		if (null == response.getTotalResults())
			xStream.omitField(ApiResponse.class, "totalResults");
		if (null == response.getImageServer())
			xStream.omitField(ApiResponse.class, "imageServer");
		return filter(xStream.toXML(response));
	}

	private String filter(String jsonStr) {
		return jsonStr;// .replaceAll("\\\\\\", "");
	}
}
