package com.yuwang.pinju.web.valitation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.common.BeanValidator;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.validation.annotation.FieldMatch;
import com.yuwang.pinju.web.message.Message;



/**
 * <p>Bean Validation 验证</p>
 *
 * @author gaobaowen
 * @since 2011-6-29 下午03:19:17
 */
public class ActionInvokeResult {

	public final static String ACTION_INVOKE_MESSAGE = "invokeMessage";
	private final static String VALIDATION_RESULT_NAME = "validator";

	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(getClass());

	private Object[] objs;

	public ActionInvokeResult(Object... objs) {
		this.objs = objs;
		initValidator();
	}

	public static void initValidator() {
		Object obj = ActionContext.getContext().get(VALIDATION_RESULT_NAME);
		if(obj == null) {
			ActionContext.getContext().put(VALIDATION_RESULT_NAME, new HashMap<String, String>());
		}
	}

	public static void clear() {
		ActionContext.getContext().put(VALIDATION_RESULT_NAME, null);
		ActionContext.getContext().put(ACTION_INVOKE_MESSAGE, null);
	}

	public boolean getError() {
		return !validateRresult();
	}

	/**
	 * <p>验证对象</p>
	 *
	 * @param obj
	 * @return  验证正确返回  true，否则返回若失败时返回 false
	 *
	 * @author gaobaowen
	 */
	public boolean validate() {
		if(objs == null || objs.length == 0) {
			// 如果没有待检查对象时，置为验证通过
			return true;
		}
		for (int i = 0; i < objs.length; i++) {
			if(objs[i] == null) {
				log.debug("validate object is null ignore it, validate parameter No." + (i + 1));
				continue;
			}
			if (log.isDebugEnabled()) {
				log.debug("validate " + objs[i]);
			}
			Set<ConstraintViolation<Object>> set = BeanValidator.validate(objs[i]);
			addValidationResult(set);
		}
		return validateRresult();
	}

	/**
	 * <p>验证对象中的属性</p>
	 * 
	 * @param propertyName 属性名称
	 * @return 验证正确返回  true，否则返回若失败时返回 false
	 */
	public boolean validate(String... propertyName) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		if (propertyName == null || propertyName.length == 0) {
			return true;
		}
		for (int i = 0; i < propertyName.length; i++) {
			Set<ConstraintViolation<Object>> set = BeanValidator.validateProperty(objs[0], propertyName[i]);
			addValidationResult(set);
		}
		return validateRresult();
	}
	/**
	 * <p>在对象属性上添加错误信息</p>
	 *
	 * @param property  属性名
	 * @param message  错误信息
	 *
	 * @author gaobaowen
	 * @since 2011-8-11 13:20:26
	 */
	@SuppressWarnings("unchecked")
	public void addMessage(String property, String message) {
		Map<String, String> validator = (Map<String, String>)ActionContext.getContext().get(VALIDATION_RESULT_NAME);
		validator.put(property, message);
	}

	/**
	 * <p>在对象属性上添加错误信息的 key，key 位于 message.properties 文件中</p>
	 *
	 * @param property 属性名
	 * @param messageKey 消息的 key
	 * @param args 消息 key 的参数
	 *
	 * @author gaobaowen
	 * @since 2011-8-11 13:20:58
	 */
	public void addMessageKey(String property, String messageKey, Object... args) {
		addMessage(property, Message.getMessage(messageKey, args));
	}

	/**
	 * <p>将指定 key 值配置的消息放入到 Struts 2 名为 {@link #ACTION_INVOKE_MESSAGE} 的值栈上下文中。
	 * 在 freemarker 中可使用 ${invokeMessage} 获得该消息。</p>
	 *
	 * @param messageKey
	 * @param args
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午04:37:13
	 */
	public static String setInvokeMessageKey(String messageKey, Object... args) {
		return setInvokeMessage(Message.getMessage(messageKey, args));
	}

	public static String setInvokeMessage(String message) {
		ActionContext.getContext().put(ACTION_INVOKE_MESSAGE, message);
		return message;
	}

	private <T> void addValidationResult(Set<ConstraintViolation<T>> set) {
		if (EmptyUtil.isBlank(set)) {
			log.debug("validate result is correct");
			return;
		}
		for (ConstraintViolation<?> result : set) {
			String property = processProperty(result);
			addMessage(property, result.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("validate result, bean: " + result.getRootBeanClass().getSimpleName() + ", property: " + property + ", property value: "
						+ result.getInvalidValue() + ", validate message: " + result.getMessage());
			}
		}
	}

	private String processProperty(ConstraintViolation<?> result) {
		String property = result.getPropertyPath().toString();
		if (!EmptyUtil.isBlank(property)) {
			return property;
		}
		Object obj = result.getConstraintDescriptor().getAnnotation();
		if (obj instanceof FieldMatch) {
			FieldMatch fm = (FieldMatch)obj;
			return fm.second();
		}
		return property;
	}

	/**
	 * <p>获取验证结果</p>
	 *
	 * @return 验证正确返回 true
	 *
	 * @author gaobaowen
	 */
	private boolean validateRresult() {
		Map<String, String> validator = getValidator();
		if(validator == null) {
			return true;
		}
		return EmptyUtil.isBlank(validator);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getValidator() {
		return (Map<String, String>)ActionContext.getContext().get(VALIDATION_RESULT_NAME);
	}
}
