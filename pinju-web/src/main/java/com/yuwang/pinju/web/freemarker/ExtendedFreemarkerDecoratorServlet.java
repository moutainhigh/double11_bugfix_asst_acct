package com.yuwang.pinju.web.freemarker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet;
import com.yuwang.pinju.web.freemarker.share.MemberAuth;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

public class ExtendedFreemarkerDecoratorServlet extends FreemarkerDecoratorServlet {

	private static final long serialVersionUID = -2719273401027672954L;

	protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template,
			TemplateModel templateModel) throws ServletException, IOException {
		boolean superResult = super.preTemplateProcess(request, response, template, templateModel);
		SimpleHash hash = (SimpleHash)templateModel;
		hash.put("memberAuth", MemberAuth.getInstance());
		return superResult;
	}
}
