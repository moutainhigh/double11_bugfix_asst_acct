package com.yuwang.pinju.core.weibo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.weibo.json.JSONException;
import com.yuwang.pinju.core.weibo.json.JSONObject;

public class WeiboClient {
	
	private final static Log log = LogFactory.getLog(WeiboClient.class);

	private ClientConnection clientConnection;
	
	public WeiboClient() {
		this.clientConnection = new UrlConnection();
	}
	
	/**
	 * <p>根据返回的code值查询token值</p>
	 * @param code 
	 * @return AccessToken对象
	 * @throws WeiboException
	 */
	
	public AccessToken getAccessTokenByCode(String code) throws WeiboException {
		return new AccessToken(clientConnection.doPost(PinjuConstant.SINA_WEIBO_ACCESSTOKEN_URL,
				new RequestParameter[]{
				new RequestParameter("client_id", PinjuConstant.SINA_WEIBO_CLIENTID),
				new RequestParameter("client_secret", PinjuConstant.SINA_WEIBO_CLIENT_SERCRET),
				new RequestParameter("grant_type", "authorization_code"),
				new RequestParameter("code", code),
				new RequestParameter("redirect_uri",PinjuConstant.SINA_WEIBO_REDIRECT_URI)
		}));
	}
	
	/**
	 * <p>根据token值查询新浪微博会员的uid</p>
	 * @param accessToken token值
	 * @return uid
	 * @throws WeiboException
	 */
	
	public String getUid(String accessToken) throws WeiboException {
		Response response = clientConnection.doGet(PinjuConstant.SINA_WEIBO_GETUID_URL, new RequestHeader[]{
				new RequestHeader("Authorization", "OAuth2 " + accessToken)
		});
		try {
			JSONObject jsonObject = new JSONObject(response.getResponseAsString());
			return jsonObject.getString("uid");
		} catch (JSONException e) {
			log.error("json-getuid:"+ response.getResponseAsString(), e);
			throw new WeiboException(e);
		}
	}
	
	/**
	 * <p>根据会员uid值查询新浪微博会员相关信息</p>
	 * @param accessToken token值
	 * @param uid 微博会员编号
	 * @return uid or null
	 * @throws WeiboException
	 */
	
	public SinaUser getUser(String accessToken, String uid) throws WeiboException {
		Response response = clientConnection.doGet(PinjuConstant.SINA_WEIBO_GETUSER_URL,new RequestParameter[]{
				new RequestParameter("uid", uid)
		}, new RequestHeader[]{
				new RequestHeader("Authorization", "OAuth2 " + accessToken)
		});
		try {
			return new SinaUser(new JSONObject(response.getResponseAsString()));
		} catch (JSONException e) {
			log.error("json-getuser:"+ response.getResponseAsString(), e);
			throw new WeiboException(e);
		}
	}
	
	public String getUserJsonString(String accessToken, String uid) throws WeiboException {
		Response response = clientConnection.doGet(PinjuConstant.SINA_WEIBO_GETUSER_URL,new RequestParameter[]{
				new RequestParameter("uid", uid)
		}, new RequestHeader[]{
				new RequestHeader("Authorization", "OAuth2 " + accessToken)
		});
	    return response.getResponseAsString();
	}
}
