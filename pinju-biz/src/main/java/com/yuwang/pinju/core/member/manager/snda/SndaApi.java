package com.yuwang.pinju.core.member.manager.snda;

import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_APP_KEY;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_APP_SECRET_KEY;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_INVOKE_TIMEOUT;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_INVOKE_URL;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_QUERY_USER_INFO;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_QUERY_USER_INFO_KEY;
import static com.yuwang.pinju.core.constant.system.PinjuConstant.SNDA_API_SYSTEM_TOKEN_URL;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.snda.apipool.oa.ApiPool;
import com.snda.apipool.oa.Response;
import com.snda.apipool.oauth.OAuthClient;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.SndaAccountDO;

/**
 * <p>盛大通行证开放 API 调用</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 下午02:03:03
 */
public class SndaApi {

	private final static Log log = LogFactory.getLog(SndaApi.class);

	private final static String REQUEST_OAUTH_VERSION = "2.0";

	private final static String REQUEST_CLIENT_ID = "client_id";
	private final static String REQUEST_CLIENT_SECRET = "client_secret";
	private final static String REQUEST_GRANT_TYPE = "grant_type";
	private final static String REQUEST_GRANT_TYPE_VALUE = "client_credentials";

	private final static String RESPONSE_ACCESS_TOKEN = "access_token";
	private final static String RESPONSE_EXPIRES_IN = "expires_in";

	private final static String REQUEST_QUERY_USER_INFO_SDID = "sdid";
	private final static String REQUEST_QUERY_USER_INFO_KEY = "key";
	private final static String REQUEST_API_VERSION = "1.0";

	private final static Integer RESPONSE_API_INVOKE_SUCCESS_CODE = 0;

	private final static String RESPONSE_JSON_KEYVALUE = "keyValue";

	/**
	 * <p>根据盛大通行证数字账号，通过盛大开放 API 查询用户的账号信息</p>
	 *
	 * @param sndaId  盛大通行证数字账号
	 * @return 盛大通行证账号信息，若无法获取时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 下午01:59:37
	 */
	public static SndaAccountDO invokeQueryUserInfo(String sndaId) {

		if(log.isInfoEnabled()) {
			log.info("invokeQueryUserInfo, sndaId: " + sndaId);
		}

		if (EmptyUtil.isBlank(sndaId)) {
			log.error("invokeQueryUserInfo, sndaId is empty or null, cannot execute api invoker");
			return null;
		}

		OAuthClient oauth = getSystemAuth();
		if(oauth == null) {
			log.error("invokeQueryUserInfo, cannot get OAuthClient object, cannot execute api invoker, sndaId: " + sndaId);
			return null;
		}

		JSONObject params = new JSONObject();

		try {

	    	params.put(REQUEST_QUERY_USER_INFO_SDID, sndaId);
	    	params.put(REQUEST_QUERY_USER_INFO_KEY, SNDA_API_QUERY_USER_INFO_KEY);

	        ApiPool apipool = new ApiPool();

	        apipool.setHttpRequestUrl(SNDA_API_INVOKE_URL);

	        // 调用 API 接口
	        Response response = apipool.request(oauth, SNDA_API_QUERY_USER_INFO, params, REQUEST_API_VERSION, SNDA_API_INVOKE_TIMEOUT);

	        if(response == null) {
	        	log.error("invokeQueryUserInfo, response result is null, params: " + params);
	        	return null;
	        }

	        if(!RESPONSE_API_INVOKE_SUCCESS_CODE.equals(response.getErrorCode())) {
	        	log.error("invokeQueryUserInfo, response result is not success, params: " + params + ", result code: "
	        			+ response.getErrorCode() + ", result info: " + response.getErrorInfo());
	        	return null;
	        }

	        String data = response.getData();

	        if(log.isInfoEnabled()) {
	        	log.info("invokeQueryUserInfo, response data: " + data);
	        }
	        return createSndaAccount(sndaId, data);
		} catch (Exception e) {
			log.error("invokeQueryUserInfo error, params: " + params, e);
			return null;
		}
	}

	/**
	 * <p>
	 * 获取 SNDA 开放 API 的 System Token 数据。文档地址：
	 * <a href="http://code.snda.com/index/oauth#systemtoken">http://code.snda.com/index/oauth#systemtoken</a>
	 * </p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 上午11:18:04
	 */
	public static OAuthClient getSystemAuth() {

		JSONObject params = new JSONObject();

		try {

			// 注册应用时获得的API Key
			params.put(REQUEST_CLIENT_ID, SNDA_API_APP_KEY);

			// 注册应用时获得的API Key密钥
			params.put(REQUEST_CLIENT_SECRET, SNDA_API_APP_SECRET_KEY);

			// 必须参数。固定值“client_credentials”
			params.put(REQUEST_GRANT_TYPE, REQUEST_GRANT_TYPE_VALUE);

			OAuthClient oauth = new OAuthClient();

			// 不需要用户授权来获取 TOKEN，即 SYSTEM TOKEN
			JSONObject json = oauth.GetSystemToken(SNDA_API_SYSTEM_TOKEN_URL, params);

			if (json == null) {
				log.error("cannot get system token, request params: " + params);
				return null;
			}

			if (!json.has(RESPONSE_ACCESS_TOKEN)) {
				log.error("getSystemAuth, response has not \"access_token\" key, request: " + params + ", response: "
						+ json);
				return null;
			}

			String systemToken = json.getString(RESPONSE_ACCESS_TOKEN);

			if(log.isInfoEnabled()) {
				if (json.has(RESPONSE_EXPIRES_IN)) {
					log.info("getSystemAuth, systemToken: " + systemToken + ", expiresIn: " + json.get(RESPONSE_EXPIRES_IN));
				} else {
					log.info("getSystemAuth, systemToken: " + systemToken + ", expiresIn: cannot find");
				}
			}

			oauth.setAppKey(SNDA_API_APP_KEY);
			oauth.setAppSecretKey(SNDA_API_APP_SECRET_KEY);
			oauth.setOAuthToken(systemToken);
			oauth.setVersion(REQUEST_OAUTH_VERSION);
			return oauth;
		} catch (Exception e) {
			log.error("getSystemAuth error, params" + params, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static SndaAccountDO createSndaAccount(String sndaId, String json) {
		SndaAccountDO sndaAccount = new SndaAccountDO(sndaId);

		try {

			JSONObject object = new JSONObject(json);

			if(!object.has(RESPONSE_JSON_KEYVALUE)) {
				log.warn("createSndaMemberInfo, json data cannot find \"keyValue\" key");
				return sndaAccount;
			}

			JSONArray keyValues = object.getJSONArray(RESPONSE_JSON_KEYVALUE);

			if(log.isDebugEnabled()) {
				log.debug("createSndaMemberInfo, json array: " + keyValues);
			}

			for(int i = 0, x = keyValues.length(); i < x; i++) {
				JSONObject jsonObj = keyValues.getJSONObject(i);
				for(Iterator<String> j = jsonObj.keys(); j.hasNext(); ) {
					String key = j.next();
					sndaAccount.addSndaAccount(key, jsonObj.getString(key));
				}
			}

		} catch (Exception e) {
			log.error("createSndaAccount, json parse error, snda id: " + sndaId + ", json: " + json);
			return sndaAccount;
		}

		if(log.isDebugEnabled()) {
			log.debug("createSndaAccountInfo, " + sndaAccount);
		}

		return sndaAccount;
	}
}
