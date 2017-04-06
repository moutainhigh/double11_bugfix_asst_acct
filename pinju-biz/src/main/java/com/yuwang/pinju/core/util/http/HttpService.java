package com.yuwang.pinju.core.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpService {

	private final static Log log = LogFactory.getLog(HttpService.class);

	private String requestUrl;
	private String charset;
	private Map<String, String> params;

	public HttpService(String requestUrl) {
		this(requestUrl, "UTF-8");
	}

	public HttpService(String requestUrl, String charset) {
		this.requestUrl = requestUrl;
		this.charset = charset;
	}

	public void addParameter(String name, String value) {
		if(params == null) {
			params = new LinkedHashMap<String, String>();
		}
		params.put(name, encode(value));
	}

	public HttpResponse doGet() throws IOException {
		return doGet(charset);
	}

	public HttpResponse doGet(String responseCharset) throws IOException {
		HttpResponse response = new HttpResponse();
		HttpURLConnection conn = null;
		try {
			conn = createHttpURLConnection(true);
			response.setData(readInputStream(conn));
			response.setCharset(conn.getHeaderField("Content-Type"), responseCharset);
		} catch (IOException e) {
			log.error("request url " + conn.getURL() + " failed", e);
			throw e;
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}

	private HttpURLConnection createHttpURLConnection(boolean isGet) throws IOException {
		URL url = new URL(isGet ? doGetUrl() : requestUrl);
		if(log.isDebugEnabled()) {
			log.debug("validate url: " + url);
		}
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.addRequestProperty("Connection", "close");
		return conn;
	}

	private byte[] readInputStream(HttpURLConnection conn) throws IOException {
		log.info("request validate url response code: " + conn.getResponseCode());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = conn.getInputStream();
		int r = 0;
		byte[] bys = new byte[8192];
		for(int p = -1; (p = in.read(bys)) != -1; ) {
			baos.write(bys, 0, p);
			r += p;
		}
		if(log.isDebugEnabled()) {
			log.debug("request validate url, response " + r + " byte(s), Content Length: " + conn.getHeaderField("Content-Length"));
		}
		return baos.toByteArray();
	}

	private String doGetUrl() {
		if(params == null || params.size() == 0) {
			return requestUrl;
		}
		StringBuilder sb = new StringBuilder(requestUrl);
		sb.append('?');
		return doParameters(sb).toString();
	}

	private StringBuilder doParameters(StringBuilder sb) {
		int k = 0;
		for(Map.Entry<String, String> entry : params.entrySet()) {
			if(k++ > 0) {
				sb.append('&');
			}
			sb.append(entry.getKey()).append('=').append(entry.getValue());
		}
		return sb;
	}

	private String encode(String value) {
		try {
			return URLEncoder.encode(value, charset);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static class HttpResponse {

		private static Pattern CHARSET_HEADER = Pattern.compile("charset=([^\\s]+)");

		private byte[] data;
		private String charset;

		public byte[] getData() {
			return data;
		}
		void setData(byte[] data) {
			this.data = data;
		}
		public String getCharset() {
			return charset;
		}
		void setCharset(String contentType, String defaultCharset) {
			if(contentType == null || contentType.length() == 0) {
				this.charset = defaultCharset;
				return;
			}
			Matcher matcher = CHARSET_HEADER.matcher(contentType);
			while(matcher.find()) {
				this.charset = matcher.group(1);
				if(log.isDebugEnabled()) {
					log.debug("find http response header content type, value: " + contentType + ", using response charset: " + this.charset);
				}
				return;
			}
			if(log.isDebugEnabled()) {
				log.debug("cannot find charset from response header content type [" + contentType + "]");
			}
			this.charset = defaultCharset;
		}

		public String getStringData() {
			if(data == null) {
				return null;
			}
			try {
				return new String(data, charset);
			} catch (UnsupportedEncodingException e) {
				log.error("getStringData error", e);
				return null;
			}
		}
	}
}
