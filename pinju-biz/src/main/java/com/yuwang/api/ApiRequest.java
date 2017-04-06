/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.api.util.ApiUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.StringUtil;

/**
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public class ApiRequest {
	
	private final Log logger = LogFactory.getLog("open-api");
	private final Log log = LogFactory.getLog(this.getClass().getName());

	/**
	 * 应用编号
	 */
	protected String apiId;
	
	/**
	 * 应用密钥
	 */
	protected String appSecret;

	/**
	 * 临时授权
	 */
	protected String sessionKey;

	/**
	 * 会员编号（有sessionKey的请求）
	 */
	protected Long memberId;
	
	/**
	 * 会员昵称（有sessionKey的请求）
	 */
	protected String nickName;

	/**
	 * 调用API方法
	 */
	protected String apiMethodName;

	/**
	 * 返回结果XML
	 */
	protected String format;
	
	/**
	 * 请求签名
	 */
	protected String sign;
	
	/**
	 * 客户端IP地址
	 */
	protected String clientIp;

	/**
	 * 当前调用的方法
	 */
	protected OpenApiMethodDO currentMethod;

	/**
	 * 应用级参数
	 */
	protected Map<String, String> textParams;

	/**
	 * 上传文件列表
	 */
	protected Map<String, File> uploadParams;

	public Map<String, String> getTextParams() {
		return textParams;
	}

	public void setTextParams(Map<String, String> textParams) {
		this.textParams = textParams;
	}

	public Map<String, File> getUploadParams() {
		return uploadParams;
	}

	public void setUploadParams(Map<String, File> uploadParams) {
		this.uploadParams = uploadParams;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getApiMethodName() {
		return apiMethodName;
	}

	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}

	public boolean check() throws ApiException {
		if (StringUtil.isEmpty(this.apiId)) {
			return false;
		}
		return true;
	}

	public OpenApiMethodDO getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(OpenApiMethodDO currentMethod) {
		this.currentMethod = currentMethod;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * 生成相应业务的实体对象
	 * 
	 * @return
	 * @throws ApiException
	 */
	@SuppressWarnings("unchecked")
	public Object createDomain() throws ApiException {
		long startTime = System.currentTimeMillis();
		try {
			Class objClazz = Class.forName(currentMethod.getDomainClass());
			Object domain = objClazz.newInstance();
			for (String key : textParams.keySet()) {
				Class type = ApiUtil.getPropertyType(objClazz, key);
				if (type != null) {
					try {
						BeanUtils.copyProperty(domain, key, ApiUtil.createObject(type, textParams.get(key)));
					} catch (ApiException e) {
						throw e;
					} catch (Exception e) {
						// ignore
						log.warn(e);
						continue;
					}
				}
			}
			return domain;
		} catch (ClassNotFoundException e) {
			// ignore
			log.warn(e);
		} catch (InstantiationException e) {
			// ignore
			log.warn(e);
		} catch (IllegalAccessException e) {
			// ignore
			log.warn(e);
		} finally {
			logger.debug("##############create Domain Object time:" + (System.currentTimeMillis() - startTime) + "ms.");
		}
		return null;
	}

	/**
	 * 文件上传完毕清除服务器端临时文件
	 */
	public void clearTempFile() {
		if (uploadParams != null && uploadParams.size() > 0) {
			for (File file : uploadParams.values()) {
				if(!file.isDirectory())
					file.delete();
			}
		}
	}
	
	public String getSignData() {
		StringBuilder sbf = new StringBuilder();
		sbf.append(apiId).append("|").append(apiMethodName);
		if (!EmptyUtil.isBlank(sessionKey))
			sbf.append("|").append(sessionKey);
		if (!EmptyUtil.isBlank(format))
			sbf.append("|").append(format);
		sbf.append("|").append(appSecret);
		return sbf.toString();
	}
	
	public String toString() {
		StringBuilder sbd = new StringBuilder();
		if (!EmptyUtil.isBlank(apiId))
			sbd.append("{\"system param\":{\"appKey\":\"").append(apiId);
		if (!EmptyUtil.isBlank(apiMethodName))
			sbd.append("\", \"methodName\":\"").append(apiMethodName);
		if (!EmptyUtil.isBlank(sessionKey))
			sbd.append("\", \"session\":\"").append(sessionKey);
		if (!EmptyUtil.isBlank(format))
			sbd.append("\", \"format\":\"").append(format);
		if (!EmptyUtil.isBlank(sign))
			sbd.append("\", \"sign\":\"").append(sign);
		if (!EmptyUtil.isBlank(clientIp))
			sbd.append("\", \"clientIp\":\"").append(clientIp).append("\"}");
		sbd.append(",\"application param\":{");
		int i = 0;
		if (textParams != null && textParams.size() > 0) {
			for (String key : textParams.keySet()) {
				if (i > 0) {
					sbd.append(", ");
				} else
					i++;
				sbd.append("\"").append(key).append("\":\"").append(
						textParams.get(key)).append("\"");
			}
		}
		sbd.append("}");
		if (uploadParams != null && uploadParams.size() > 0) {
			i = 0;
			sbd.append(",\"upload param\":[");
			for (String fileName : uploadParams.keySet()) {
				if (i > 0)
					sbd.append(", ");
				else
					i++;
				sbd.append("\"").append(fileName).append("\"");
			}
			sbd.append("]");
		}
		sbd.append("}");
		return sbd.toString();
	}
}
