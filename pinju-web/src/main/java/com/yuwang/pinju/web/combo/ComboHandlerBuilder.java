package com.yuwang.pinju.web.combo;

import javax.servlet.ServletContext;

import com.yuwang.pinju.web.combo.cache.CachedComboHandler;

public class ComboHandlerBuilder {

	private final static ComboHandlerBuilder builder = new ComboHandlerBuilder();

	private ComboHandlerBuilder() {
	}

	public static ComboHandlerBuilder getInstance() {
		return builder;
	}

	public ComboHandler build(ServletContext context, String[] combos) {
		return new CachedComboHandler(context, combos);
	}
}
