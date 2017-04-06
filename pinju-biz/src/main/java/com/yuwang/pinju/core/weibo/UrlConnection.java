package com.yuwang.pinju.core.weibo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;

public class UrlConnection extends AbstaractConnection implements ClientConnection {

	private final static Log log = LogFactory.getLog(UrlConnection.class);
	
	private HttpURLConnection con = null;
	
	@Override
	public Response doGet(String url, RequestParameter[] parameter, RequestHeader[] headers) throws WeiboException {
		Response response = new Response();
		int statusCode = -1;
		BufferedReader reader = null;
		try {
			if (StringUtil.isBlank(url)) {
				throw new WeiboException("UrlConnection.doGet url is null");
			}
			URL getUrl = generateURL(url, parameter);
			con = (HttpURLConnection) getUrl.openConnection();
			con.setConnectTimeout(PinjuConstant.SINA_WEIBO_CONNECTTIMEOUT);
			con.setReadTimeout(PinjuConstant.SINA_WEIBO_READTIMEOUT);
			con.addRequestProperty("Connection", "close");
			if (headers != null && headers.length > 0) {
				for (int i = 0; i < headers.length; i++) {
					con.setRequestProperty(headers[i].getName(), headers[i].getValue());
				}
			}
			statusCode = con.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
		         throw new WeiboException(WeiboException.getCause(statusCode), statusCode);
		    }
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	        StringBuilder cacheBuffer = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	cacheBuffer.append(line);
	        }
	        log.warn("response content:" + cacheBuffer.toString());
			response.setResponseAsString(cacheBuffer.toString());
		} catch (Exception e) {
			throw new WeiboException("doGet is error", e, statusCode);
		} finally {
			try {
				if (reader != null)
				  reader.close();
			} catch (IOException e) {
				throw new WeiboException("close inputstream reader error");
			}
			if (con != null)
			    con.disconnect();
		}
		return response;
	}

	@Override
	public Response doPost(String url, RequestParameter[] parameter) throws WeiboException {
		DataOutputStream out = null;
		BufferedReader reader = null;
		Response response = new Response();
		int statusCode = -1;
		try {
			URL postUrl = new URL(url);
			con = (HttpURLConnection) postUrl.openConnection();
			con.setConnectTimeout(PinjuConstant.SINA_WEIBO_CONNECTTIMEOUT);
			con.setReadTimeout(PinjuConstant.SINA_WEIBO_READTIMEOUT);
			con.addRequestProperty("Connection", "close");
			con.setDoOutput(true);
	        con.setDoInput(true);
	        con.setRequestMethod("POST");
	        con.setUseCaches(false);
	        con.connect();
	        out = new DataOutputStream(con.getOutputStream());
	        String reqContent = getPostContent(parameter);
	        out.writeBytes(reqContent);
	        out.flush();
	        out.close();
	        statusCode = con.getResponseCode();
	        if (statusCode != HttpURLConnection.HTTP_OK) {
	        	throw new WeiboException(WeiboException.getCause(statusCode), statusCode);
	        }
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	        StringBuilder cacheBuffer = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	cacheBuffer.append(line);
	        }
	        reader.close();
			response.setResponseAsString(cacheBuffer.toString());
		} catch (Exception e) {
			throw new WeiboException("doPost is error", e, statusCode);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return response;
	}

	@Override
	public Response doGet(String url, RequestParameter[] requestParameter)
			throws WeiboException {
		return doGet(url, requestParameter, null);
	}

	@Override
	public Response doGet(String url, RequestHeader[] headers)
			throws WeiboException {
		return doGet(url, null, headers);
	}
}
