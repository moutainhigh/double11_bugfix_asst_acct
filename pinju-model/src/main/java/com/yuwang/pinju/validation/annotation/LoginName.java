package com.yuwang.pinju.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.yuwang.pinju.validation.validator.LoginNameValidator;

/**
 * <p>Validate that byte length of the string is between min and max included.</p>
 *
 * @author gaobaowen
 * @since 2011-8-4 下午01:06:27
 */
@Documented
@Constraint(validatedBy = LoginNameValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface LoginName {
	int min() default LoginNameValidator.DEFAULT_MIN_BYTE_LENGTH;
	int max() default LoginNameValidator.DEFAULT_MAX_BYTE_LENGTH;
	String message() default "{org.hibernate.validator.constraints.loginname}";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}

