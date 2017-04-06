/**
 *
 */
package com.yuwang.pinju.core.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yejingfeng
 * @since 2011-5-30
 * AO层的返回结果
 */
public interface Result extends Serializable {
	/**
	 * 请求是否成功。
	 *
	 * @return 如果成功，则返回<code>true</code>
	 */
	boolean isSuccess();

	/**
	 * 设置请求成功标志。
	 *
	 * @param success
	 *            成功标志
	 */
	void setSuccess(boolean success);

	/**
	 * 获取返回码
	 *
	 * @return 返回码
	 */
	String getResultCode();

	/**
	 * 返回子的返回码
	 *
	 * @return
	 */
	String getSubResultCode();

	/**
	 * 设置返回码
	 *
	 * @param code
	 */
	void setResultCode(String code);

	/**
	 * 设置子返回码
	 *
	 * @param code
	 */
	void setSubResultCode(String code);

	/**
	 * 取得model对象
	 *
	 * @param key
	 *            字符串key
	 * @return model对象
	 */
	Object getModel(String key);

	/**
	 * 设置model对象。
	 *
	 * @param key
	 *            字符串key
	 * @param model
	 *            model对象
	 */
	void setModel(String key, Object model);

	/**
	 * 取得所有model对象。
	 *
	 * @return model对象表
	 */
	Map<String, Object> getModels();

	/**
	 * <p>获取特定类型的 model 对象</p>
	 *
	 * @param <T>
	 * @param key   字符串 key
	 * @param clazz  数据类型
	 * @return
	 *
	 * @author gaobaowen
	 */
	<T> T getModel(String key, Class<T> clazz);

	/**
	 * <p>设置 model 对象。key 置为对象 class 类名</p>
	 *
	 * @param model
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 10:54:21
	 */
	void setModel(Object model);

	/**
	 * <p>从 models 中获取指定类的 model 对象</p>
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 上午10:54:47
	 */
	<T> T getModel(Class<T> clazz);
}
