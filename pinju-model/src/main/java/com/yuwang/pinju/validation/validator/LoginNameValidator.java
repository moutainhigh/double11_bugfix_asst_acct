package com.yuwang.pinju.validation.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.validation.annotation.LoginName;

public class LoginNameValidator implements ConstraintValidator<LoginName, String> {

	public final static int DEFAULT_MIN_BYTE_LENGTH = 4;
	public final static int DEFAULT_MAX_BYTE_LENGTH = 100;
	
	private final static Log log = LogFactory.getLog(LoginNameValidator.class);
	private final static String SUCCESS = "success";

	private static int min = DEFAULT_MIN_BYTE_LENGTH;
	private static int max = DEFAULT_MAX_BYTE_LENGTH;

	private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	public final static java.util.regex.Pattern mail = java.util.regex.Pattern.compile(
			"^" + ATOM + "+(\\." + ATOM + "+)*@"
					+ DOMAIN
					+ "|"
					+ IP_DOMAIN
					+ ")$",
			java.util.regex.Pattern.CASE_INSENSITIVE
	);

	private final static Pattern mobile = Pattern.compile("1[3458][0-9]{9}");
	private final static Pattern namePattern = Pattern.compile("[\u4e00-\u9faf_0-9a-zA-Z-]+");
	private final static Pattern namePattern2 = Pattern.compile("[\u4e00-\u9faf0-9a-zA-Z].*");

	public static boolean isEmail(String loginName) {
		return mail.matcher(loginName).matches();
	}

	public static boolean isMobile(String loginName) {
		return mobile.matcher(loginName).matches();
	}

	@Override
	public void initialize(LoginName parameters) {
		min = parameters.min();
		max = parameters.max();
		validateParameters();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String result = validateLoginName(value);
		if (SUCCESS.equals(result)) {
			return true;
		}
		return error(result, context);
	}

	public static boolean isLoginName(String loginName) {
		String result = validateLoginName(loginName);
		if (!SUCCESS.equals( result )) {
			log.warn("validate login name, result: " + result);
			return false;
		}
		return true;
	}

	private static String validateLoginName(String value) {
		if (value == null || value.trim().length() == 0) {
			return "{memberRegister.loginName.notempty}";
		}
		if( isEmail(value) ) {
			if (log.isDebugEnabled()) {
				log.debug("login name is email address, value: " + value);
			}
			return SUCCESS;
		}
		if ( isMobile(value) ) {
			if (log.isDebugEnabled()) {
				log.debug("login name is mobile, value: " + value);
			}
			return SUCCESS;
		}
		if (log.isDebugEnabled()) {
			log.debug("login name is not mobile and not email address, value: " + value);
		}
		if ( !byteLength(value) ) {
			return "{memberRegister.loginName.length}";
		}
		if ( !namePattern.matcher(value).matches() ) {
			return "{memberLogin.loginName.pattern}";
		}
		if ( !namePattern2.matcher(value).matches() ) {
			return "{memberRegister.loginName.pattern2}";
		}
		return SUCCESS;
	}

    private static boolean byteLength(String value) {
    	int length = 0;
		char[] chs = value.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			length += chs[i] < 0xff ? 1 : 2;
		}
		return length >= min && length <= max;
    }

	private boolean error(String template, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate( template ).addConstraintViolation();
		return false;
	}

	private void validateParameters() {
		if ( min < 0 ) {
			throw new IllegalArgumentException( "The min parameter cannot be negative." );
		}
		if ( max < 0 ) {
			throw new IllegalArgumentException( "The max parameter cannot be negative." );
		}
		if ( max < min ) {
			throw new IllegalArgumentException( "The length cannot be negative." );
		}
	}
}
