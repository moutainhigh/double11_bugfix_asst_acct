package com.yuwang.pinju.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidator {

	private static Validator validator = null;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	}

	public static <T> Set<ConstraintViolation<T>> validate(T bean) {
		return validator.validate(bean);
	}
	
	public static <T> Set<ConstraintViolation<T>> validateProperty(T bean, String propertyName) {
		return validator.validateProperty(bean, propertyName);
	}
}
