package com.yuwang.api.util;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.yuwang.api.ApiException;
import com.yuwang.api.ApiResponse;
import com.yuwang.api.Parser;
import com.yuwang.api.common.Constants;
import com.yuwang.api.domain.ResponseDatas;

/**
 * 
 * @author xiazhenyu
 * 
 *         创建时间：2011-9-5
 */
public class XMLParser extends Parser {

	private final String xmlTag = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";

	/**
	 * XML解析
	 * 
	 * @param response
	 * @return XML字符串
	 */
	@Override
	public String parser(ApiResponse response) {
		XStream xStream = new XStream();
		xStream.registerConverter(new DateConverter());
		setAlias(xStream);
		xStream.autodetectAnnotations(true);
		xStream.addImplicitCollection(ResponseDatas.class, "data");
		if (null == response.getTotalResults())
			xStream.omitField(ApiResponse.class, "totalResults");
		if (null == response.getImageServer())
			xStream.omitField(ApiResponse.class, "imageServer");
		String rtn = xStream.toXML(response);
		return xmlTag + rtn;
	}

	/**
	 * @param args
	 */
	// TODO delete
	public static void main(String[] args) {
		List<ApiException> list = new ArrayList<ApiException>();
		list.add(new ApiException(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG));
		list.add(new ApiException(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG));
		ApiResponse ar = new ApiResponse();
		ar.setErrorCode(0);
		ar.setMsg("who am i");
		ar.setRespData(list);
		System.out.println(ar.toString());
	}
}
