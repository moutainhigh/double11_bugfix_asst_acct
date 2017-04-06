/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.yuwang.api.common.BaseDO;
import com.yuwang.api.common.Constants;
import com.yuwang.api.common.ParserAlias;
import com.yuwang.api.domain.ResponseDatas;
import com.yuwang.api.util.JsonParser;
import com.yuwang.api.util.XMLParser;

/**
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
@XStreamAlias("response")
public class ApiResponse extends BaseDO {
	@XStreamOmitField
	private final Log logger = LogFactory.getLog("open-api");
	private static final long serialVersionUID = 2272194787824554149L;

	@XStreamOmitField
	private List<ParserAlias> parseAlias = new ArrayList<ParserAlias>();

	/**
	 * 成功与否flag
	 */
	private Integer errorCode = Constants.SUBMIT_APIMETHOD_INNER_SUCCESS;

	/**
	 * 返回信息
	 */
	@XStreamAlias(value = "message")
	private String msg = Constants.SUBMIT_APIMETHOD_INNER_SUCCESS_MSG;
	
	/**
	 * 数据总数(查询接口用)
	 */
	private Integer totalResults;
	
	/**
	 * 图片地址
	 */
	private String imageServer;

	/**
	 * 返回数据结果集
	 */
	@XStreamAlias(value = "respDatas")
	private ResponseDatas respData;

	/**
	 * 转换接口
	 */
	@XStreamOmitField
	private Parser parser;

	public ApiResponse() {
		this.setParser(Constants.OPEN_API_RESPONSE_PARSER_XML);
	}

	public ApiResponse(String format) {
		setParser(format);
	}

	public ApiResponse(Integer errorCode) {
		this();
		this.errorCode = errorCode;
	}

	public ApiResponse(Integer errorCode, String msg) {
		this();
		this.msg = msg;
		this.errorCode = errorCode;
	}

	public ApiResponse(Integer errorCode, String msg, String format) {
		this(format);
		this.msg = msg;
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResponseDatas getRespData() {
		return respData;
	}

	public void setRespData(List<?> data) {
		if (respData == null)
			respData = new ResponseDatas();
		respData.setData(data);
	}

	public void setRespData(Object data) {
		if (respData == null)
			respData = new ResponseDatas();
		respData.setData(data);
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public void setParser(String format) {
		// 生成指定格式的解析器【如果不存在，则赋值为默认的xml】
		if (Constants.OPEN_API_RESPONSE_PARSER_JSON.equals(format))
			this.parser = new JsonParser();
		else
			this.parser = new XMLParser();
		parser.setAliasList(parseAlias);
	}

	public String toString() {
		long startTime = System.currentTimeMillis();
		try {
			return parser.parser(this);
		} finally {
			logger.debug("##############Parser object to XML/JSON time:"
					+ (System.currentTimeMillis() - startTime) + "ms.");
		}
	}

	public void setAliasClazz(ParserAlias parserAlias) {
		parseAlias.add(parserAlias);
	}

	public void setAliasClazz(List<ParserAlias> aliasList) {
		if (aliasList != null)
			parseAlias.addAll(aliasList);
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public String getImageServer() {
		return imageServer;
	}

	public void setImageServer(String imageServer) {
		this.imageServer = imageServer;
	}
	
	public ApiResponse toClone(boolean isQuery) {
		ApiResponse resp = new ApiResponse();
		resp.setMsg(this.msg);
		resp.setErrorCode(this.errorCode);
		resp.setAliasClazz(this.parseAlias);
		resp.setImageServer(this.imageServer);
		resp.setTotalResults(this.totalResults);
		if(!isQuery && this.respData != null)
			resp.setRespData(this.respData.getData());
		resp.setParser(Constants.OPEN_API_RESPONSE_PARSER_JSON);
		return resp;
	}
}
