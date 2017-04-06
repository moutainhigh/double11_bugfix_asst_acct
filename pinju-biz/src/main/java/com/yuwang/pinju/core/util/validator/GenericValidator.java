package com.yuwang.pinju.core.util.validator;

import org.apache.oro.text.perl.Perl5Util;

/**
 * This class contains basic methods for performing validations.
 */
public class GenericValidator {

	/**
	 * <p>
	 * Checks if the field isn't null and length of the field is greater than
	 * zero not including whitespace.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if blank or null.
	 */
	public static boolean isBlankOrNull(String value) {
		return ((value == null) || (value.trim().length() == 0));
	}

	/**
	 * <p>
	 * Checks if the value matches the regular expression.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param regexp The regular expression.
	 * @return true if matches the regular expression.
	 */
	public static boolean matchRegexp(String value, String regexp) {
		if (regexp == null || regexp.length() <= 0) {
			return false;
		}

		Perl5Util matcher = new Perl5Util();
		return matcher.match("/" + regexp + "/", value);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a byte primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to a Byte.
	 */
	public static boolean isByte(String value) {
		return (GenericTypeValidator.formatByte(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a short primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to a Short.
	 */
	public static boolean isShort(String value) {
		return (GenericTypeValidator.formatShort(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a int primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to an Integer.
	 */
	public static boolean isInt(String value) {
		return (GenericTypeValidator.formatInt(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a long primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to a Long.
	 */
	public static boolean isLong(String value) {
		return (GenericTypeValidator.formatLong(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a float primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to a Float.
	 */
	public static boolean isFloat(String value) {
		return (GenericTypeValidator.formatFloat(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a double primitive.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @return true if the value can be converted to a Double.
	 */
	public static boolean isDouble(String value) {
		return (GenericTypeValidator.formatDouble(value) != null);
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(byte value, byte min, byte max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(int value, int min, int max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(float value, float min, float max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(short value, short min, short max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(long value, long min, long max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 * @return true if the value is in the specified range.
	 */
	public static boolean isInRange(double value, double min, double max) {
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if the value's length is less than or equal to the max.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param max The maximum length.
	 * @return true if the value's length is less than the specified maximum.
	 */
	public static boolean maxLength(String value, int max) {
		return (value.length() <= max);
	}

	/**
	 * <p>
	 * Checks if the value's length is greater than or equal to the min.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum length.
	 * @return true if the value's length is more than the specified minimum.
	 */
	public static boolean minLength(String value, int min) {
		return (value.length() >= min);
	}

	// See http://issues.apache.org/bugzilla/show_bug.cgi?id=29015 WRT the
	// "value" methods

	/**
	 * <p>
	 * Checks if the value is greater than or equal to the min.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum numeric value.
	 * @return true if the value is &gt;= the specified minimum.
	 */
	public static boolean minValue(int value, int min) {
		return (value >= min);
	}

	/**
	 * <p>
	 * Checks if the value is greater than or equal to the min.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum numeric value.
	 * @return true if the value is &gt;= the specified minimum.
	 */
	public static boolean minValue(long value, long min) {
		return (value >= min);
	}

	/**
	 * <p>
	 * Checks if the value is greater than or equal to the min.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum numeric value.
	 * @return true if the value is &gt;= the specified minimum.
	 */
	public static boolean minValue(double value, double min) {
		return (value >= min);
	}

	/**
	 * <p>
	 * Checks if the value is greater than or equal to the min.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param min The minimum numeric value.
	 * @return true if the value is &gt;= the specified minimum.
	 */
	public static boolean minValue(float value, float min) {
		return (value >= min);
	}

	/**
	 * <p>
	 * Checks if the value is less than or equal to the max.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param max The maximum numeric value.
	 * @return true if the value is &lt;= the specified maximum.
	 */
	public static boolean maxValue(int value, int max) {
		return (value <= max);
	}

	/**
	 * <p>
	 * Checks if the value is less than or equal to the max.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param max The maximum numeric value.
	 * @return true if the value is &lt;= the specified maximum.
	 */
	public static boolean maxValue(long value, long max) {
		return (value <= max);
	}

	/**
	 * <p>
	 * Checks if the value is less than or equal to the max.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param max The maximum numeric value.
	 * @return true if the value is &lt;= the specified maximum.
	 */
	public static boolean maxValue(double value, double max) {
		return (value <= max);
	}

	/**
	 * <p>
	 * Checks if the value is less than or equal to the max.
	 * </p>
	 * 
	 * @param value The value validation is being performed on.
	 * @param max The maximum numeric value.
	 * @return true if the value is &lt;= the specified maximum.
	 */
	public static boolean maxValue(float value, float max) {
		return (value <= max);
	}

}
