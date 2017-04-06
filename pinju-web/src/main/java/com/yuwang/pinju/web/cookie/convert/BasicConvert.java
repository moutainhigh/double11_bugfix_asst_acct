package com.yuwang.pinju.web.cookie.convert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.convert.CookieConverter;

public abstract class BasicConvert<E> implements CookieConverter<E> {

	protected final Log log = LogFactory.getLog(getClass());
}
