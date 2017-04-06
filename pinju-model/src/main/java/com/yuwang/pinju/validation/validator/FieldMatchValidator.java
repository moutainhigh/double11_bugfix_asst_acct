package com.yuwang.pinju.validation.validator;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yuwang.pinju.validation.annotation.FieldMatch;


public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Object firstObj = getPropertyValue(value, firstFieldName);
			final Object secondObj = getPropertyValue(value, secondFieldName);
			return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
		return true;
	}

	private Object getPropertyValue(Object obj, String property) {
		try {
			BeanInfo bean = Introspector.getBeanInfo(obj.getClass(), Object.class);
			PropertyDescriptor[] pd = bean.getPropertyDescriptors();
			for (int i = 0; i < pd.length; i++) {
				if (pd[i].getName().equals(property)) {
					Method getMethod = pd[i].getReadMethod();
					return getMethod.invoke(obj);
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}