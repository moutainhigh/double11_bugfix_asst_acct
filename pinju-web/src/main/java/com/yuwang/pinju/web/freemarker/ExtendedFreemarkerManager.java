package com.yuwang.pinju.web.freemarker;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.web.freemarker.share.MemberAuth;
import com.yuwang.pinju.web.freemarker.share.PinjuEncoder;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * <p>Struts 2 freemarker 扩展管理。根据 Struts 2 规定，必须继承 {@link FreemarkerManager} 类，
 * 并将 Struts 2 的 struts.freemarker.manager.classname 参数值配置成为该类的类名。</p>
 *
 * @author gaobaowen
 * 2011-6-22 上午09:48:17
 */
public class ExtendedFreemarkerManager extends FreemarkerManager {

	@Override
	protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
		Configuration configuration = super.createConfiguration(servletContext);
		// 添加 Member ID 编码的 freemarker 全局变量，在页面上直接使用 ${encoder.encodeMemberId(xxxxx)}
        configuration.setSharedVariable("encoder", PinjuEncoder.getInstance());
        configuration.setSharedVariable("fileServer", PinjuConstant.FILE_SERVER);
        configuration.setSharedVariable("imageServer", PinjuConstant.VIEW_IMAGE_SERVER);
        configuration.setSharedVariable("staticServer", PinjuConstant.STATIC_SERVER);
        configuration.setSharedVariable("searchServer", PinjuConstant.SEARCH_SERVER);

        configuration.setSharedVariable("memberAuth", MemberAuth.getInstance());
        return configuration;
	}
}
