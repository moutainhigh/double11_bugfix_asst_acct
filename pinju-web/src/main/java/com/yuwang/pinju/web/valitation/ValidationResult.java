package com.yuwang.pinju.web.valitation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.BeanValidator;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>Bean Validation 验证</p>
 *
 * @author gaobaowen
 * 2011-6-10 09:13:08
 * 
 * @deprecated <p>使用 {@link ActionInvokeResult} 类取代，不再需要从 Action 中继承。</p>
 * <p>原为直接调用：</p>
 * <pre>
 * if (!validate(Object... obj)) {
 *     ...
 * }
 * </pre>
 * <p>现改为：</p>
 * <pre>
 * ActionInvokeResult air = new ActionInvokeResult(Object... obj);
 * if (!air.validate()) {
 *   ...
 * }
 * </pre>
 * 
 */
public class ValidationResult extends BaseAction {

	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(getClass());

	private Map<String, String> validator = new HashMap<String, String>();

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
	protected boolean validate(Object... obj) {
		for (int i = 0; i < obj.length; i++) {
			if(obj[i] == null) {
				log.debug("validate object is null ignore it, validate parameter No." + (i + 1));
				continue;
			}
			if (log.isDebugEnabled()) {
				log.debug("validate " + obj[i]);
			}
			Set<ConstraintViolation<Object>> set = BeanValidator.validate(obj[i]);
			addValidationResult(set);
		}
		return validateRresult();
	}

	protected void addMessage(String name, String message) {
		validator.put(name, message);
	}

	private <T> void addValidationResult(Set<ConstraintViolation<T>> set) {
		if (EmptyUtil.isBlank(set)) {
			log.debug("validate result is correct");
			return;
		}
		for (ConstraintViolation<?> result : set) {
			validator.put(result.getPropertyPath().toString(), result.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("validate result, bean: " + result.getRootBeanClass().getSimpleName() + ", property: " + result.getPropertyPath() + ", property value: "
						+ result.getInvalidValue() + ", validate message: " + result.getMessage());
			}
		}
	}

	/**
	 * <p>获取验证结果</p>
	 *
	 * @return 验证正确返回 true
	 *
	 * @author gaobaowen
	 */
	private boolean validateRresult() {
		return EmptyUtil.isBlank(validator);
	}

	public Map<String, String> getValidator() {
		return validator;
	}
}
