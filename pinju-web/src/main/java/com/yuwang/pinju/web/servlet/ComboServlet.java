package com.yuwang.pinju.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.combo.ComboHandler;
import com.yuwang.pinju.web.combo.ComboHandlerBuilder;

public class ComboServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String TIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

	private final static int CACHE_YEARS = 10;
	private final static long EXPIRES_MS = 365L * 24 * 3600 * 1000 * CACHE_YEARS;
	private final static String CACHE_CONTROL_VALUE = "public, max-age=" + (365L * 24 * 3600 * CACHE_YEARS);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getQueryString();
		if (EmptyUtil.isBlank(query) || !query.startsWith("@") || query.length() < 5) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		query = query.substring(1);
		String[] links = query.split("\\?");
		String[] combos = links[0].split(";");
		processResponseHeader(query, response);
		OutputStream out = response.getOutputStream();
		try {
			ComboHandler handler = ComboHandlerBuilder.getInstance().build(getServletContext(), combos);
			byte[] bys = handler.combo();
			out.write(bys);
		} finally {
			out.close();
		}
	}

	private void processResponseHeader(String query, HttpServletResponse response) {
		if (query.endsWith(".js") || query.indexOf(".js") > -1) {
			response.setHeader("Content-Type", "application/x-javascript");
		} else if (query.endsWith(".css") || query.indexOf(".css") > -1) {
			response.setHeader("Content-Type", "text/css");
		}
		response.setHeader("Expires", new SimpleDateFormat(TIME_PATTERN, Locale.US).format(new Date(System.currentTimeMillis() + EXPIRES_MS)));
		response.setHeader("Cache-Control", CACHE_CONTROL_VALUE);
	}
}
