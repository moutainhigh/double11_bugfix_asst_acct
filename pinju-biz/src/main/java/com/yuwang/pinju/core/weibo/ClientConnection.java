package com.yuwang.pinju.core.weibo;

public interface ClientConnection {

	/**
	 *<p> URL connection get 方式 </p>
	 *
	 * @param url 请求地址
	 * @param requestParameter 请求参数 数组(RequestParameter[name, value])
	 * @param headers 请求头信息(RequestHeader[name, value])
	 * @return 返回Response 对象
	 * @throws WeiboException 异常处理信息
	 * <ul>
	 *   <li>{@link WeiboException#statusCode statusCode}：错误码值</li>
	 *   <li>{@link WeiboException#errorCode errorCode}：新浪错误码值</li>
	 *   <li>{@link WeiboException#request request}：请求的URL</li>
	 *   <li>{@link WeiboException#error error}：错误原因</li>
	 * </ul>
	 * 
	 */
	Response doGet(String url, RequestParameter[] parameter, RequestHeader[] headers) throws WeiboException;
	 
    Response doGet(String url, RequestParameter[] requestParameter) throws WeiboException;
    
    Response doGet(String url, RequestHeader[] headers) throws WeiboException;
    
    /**
     * <p> URL connection post 方式 </p>
     * @param url 请求地址
     * @param requestParameter 请求参数 数组(RequestParameter[name, value])
     * @return 返回Response 对象
     * @throws WeiboException 异常处理信息
     */
	Response doPost(String url, RequestParameter[] requestParameter) throws WeiboException;
}
