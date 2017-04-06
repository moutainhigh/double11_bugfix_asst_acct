/**
 *
 */
package com.yuwang.pinju.core.common;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @author yejingfeng
 * @since 2011-5-30
 *
 */
public class ResultSupport extends BaseDO implements Result {

	private static final long serialVersionUID = -5427837161273573297L;
	private boolean success = true;
	private String resultCode;
	private String subResultCode;
	private Map<String, Object> models = new HashMap<String, Object>(4);

	public Object getModel(String key) {
		return getModels().get(key);
	}

	public Map<String, Object> getModels() {
		return models;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getSubResultCode() {
		return this.subResultCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setModel(String key, Object model) {
		getModels().put(key, model);
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setSubResultCode(String code) {
		this.subResultCode = code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setModels(Map<String, Object> models) {
		this.models = models;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getModel(String key, Class<T> clazz) {
		return (T)getModel(key);
	}

	@Override
	public void setModel(Object model) {
		if (model == null) {
			return;
		}
		setModel(model.getClass().getName(), model);
	}

	@Override
	public <T> T getModel(Class<T> clazz) {
		return getModel(clazz.getName(), clazz);
	}
}
