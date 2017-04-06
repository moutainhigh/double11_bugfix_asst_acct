package com.yuwang.pinju.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yuwang.pinju.validation.annotation.ByteLength;

/**
 * <p>Check that a string's byte length is between min and max.</p>
 *
 * @author gaobaowen
 * @since 2011-8-4 下午01:07:06
 */
public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {
	
	private int min;
	private int max;

	@Override
	public void initialize(ByteLength parameters) {
		min = parameters.min();
		max = parameters.max();
		validateParameters();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		int length = 0;
		char[] chs = value.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			length += chs[i] < 0xff ? 1 : 2;
		}
		return length >= min && length <= max;
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
