package com.yuwang.pinju.core.httpclient;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.domain.search.SearchBaseDO;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.SearchShopItem;
/**
 * 
 * @author zhouzhaohua
 * @deprecated SDO 遗留的和盛大搜索引擎通信类
 */
public class HttpServiceImpl implements HttpService {

	private static Logger logger = Logger.getLogger(HttpServiceImpl.class);

	private String encode = "utf-8";

	private HttpClient httpClient;
	
	private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;
	
	/**
	 * end before ? ex:http://10.245.135.201/
	 */
	private String serverUrl;
	/**
	 * 数据等待时间
	 */
	
	private Integer soTimeout = 1000;

	public HttpServiceImpl() {
		logger.info("\n HttpServiceImpl init");
		multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		//连接超时
		multiThreadedHttpConnectionManager.getParams().setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
		// 数据等待超时
		multiThreadedHttpConnectionManager.getParams().setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
		// super.getParams().setStaleCheckingEnabled(true);
		// 最大连接数 ##每个主机的最大并行链接数 默认是2
		multiThreadedHttpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(
				PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
		// 最大活动连接数，##客户端总并行链接最大数，默认为20
		multiThreadedHttpConnectionManager.getParams().setMaxTotalConnections(
				PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
		httpClient = new HttpClient(multiThreadedHttpConnectionManager);
		serverUrl = PinjuConstant.SEARCH_SERVERURL;

		logger.info("\n连接超时时间=" + multiThreadedHttpConnectionManager.getParams().getConnectionTimeout() + "\n数据等待时间="
				+ multiThreadedHttpConnectionManager.getParams().getSoTimeout() + "\n每个主机的最大并行链接数="
				+ multiThreadedHttpConnectionManager.getParams().getDefaultMaxConnectionsPerHost() + "\n最大并行链接数="
				+ multiThreadedHttpConnectionManager.getParams().getMaxTotalConnections() + "\n搜索接口=" + serverUrl);
	}

	@Override
	public String doGet(Map<String, String> query) throws IOException {
		StringBuffer _sb = new StringBuffer();
		if(query !=null && query.containsKey("searchType")){
			for(String key : query.keySet()){
				if(!"searchType".equals(key)){
					_sb.append("&"+key+"="+URLEncoder.encode( query.get(key),"utf-8"));
				}
			}
			//删除第一个&
			if(_sb.length()>1){
				_sb.delete(0, 1);
			}
			return this.doGet(serverUrl+query.get("searchType")+"/?"+_sb.toString());
		}
		else{
			logger.error("query parameter is not valid");
			return null;
		}
	}

	@Override
	public String doGet(SearchBaseDO query) throws IOException {
		if(query instanceof SearchItemDO || query instanceof SearchShopItem){
			return this.doGet(this.getServerUrl()+"item/?"+query.getParametersValue());
		}
		else if(query instanceof SearchShopDO){
			return this.doGet(this.getServerUrl()+"shop/?"+query.getParametersValue());
		}else {
			logger.warn("query parameter key/value is not exist or query is invalid");
			return null;
		}
		/*
		if(query!=null){
			if(query.getType() == SearchBaseDO.SEARCH_TYPE_ITEM ||query.getType() == SearchBaseDO.SEARCH_TYPE_SHOP_ITEM ){
				return this.doGet(this.getServerUrl()+"item/?"+query.getParametersValue());
			}
			else if(query.getType() == SearchBaseDO.SEARCH_TYPE_SHOP){
				return this.doGet(this.getServerUrl()+"shop/?"+query.getParametersValue());
			}
			else{
				logger.warn("query is invalid");
			}
		}else{
			logger.warn("query parameter key/value is not exist");
			return null;
		}
		*/
	}

	@Override
	public String doGet(String url) throws IOException {
		logger.info(url);
		StringBuffer responseBody = new StringBuffer();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		InputStream in = null;
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				logger.error("HttpServiceImpl#doGet(String url)"
						+ "  Please check your provided http address! 发生致命的异常，可能是协议不对或者返回的内容有问题");
				throw new IOException(getMethod.getStatusLine().toString());
			}
			in = getMethod.getResponseBodyAsStream();
			BufferedInputStream bin = new BufferedInputStream(in);
			byte [] _b = new byte[1024*10];
			int length = 0;
			while((length = bin.read(_b))>0){
				responseBody.append(new String(_b,0,length,this.getEncode()));
			}
			/*
			reader = new BufferedReader(new InputStreamReader(in));
			while ((_str = reader.readLine()) != null) {
				responseBody.append(_str);
			}
			*/
		} finally {
			// 释放连接
			try {
				getMethod.releaseConnection();
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return responseBody.toString();
	}
	@Override
	public String doPost(Map<String, String> map) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	// default is utf-8
	public String getEncode() {
		return encode;
	}
	public MultiThreadedHttpConnectionManager getMultiThreadedHttpConnectionManager() {
		return multiThreadedHttpConnectionManager;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	
	public Integer getSoTimeout() {
		return soTimeout;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setMultiThreadedHttpConnectionManager(
			MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager) {
		this.multiThreadedHttpConnectionManager = multiThreadedHttpConnectionManager;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}
}
