package com.yuwang.pinju.core.util.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("unchecked")
public class FreemarkerUtil {
	public static Configuration cfg;

	public FreemarkerUtil() throws IOException {
		init();
	}

	/**
	 * 初始化方法 初始化Configuration
	 * 
	 * @param
	 * @exception 找不到文件
	 * @return 无返回值
	 */
	private static void init() throws IOException {
		cfg = new Configuration();
		cfg.setNumberFormat("#");
		cfg.setDirectoryForTemplateLoading(new File(Thread.currentThread()
				.getContextClassLoader().getResource("/").getFile()));
		cfg.setDefaultEncoding("UTF-8");
	}

	/**
	 * 实例化 Configuration对象
	 * 
	 * @param
	 * @exception 找不到文件
	 * @return 无返Configuration 的一个实例化对象
	 */
	private static Configuration getConfiguration() throws IOException {
		if (cfg == null)
			init();
		return (cfg);
	}

	/**
	 * 通过模板生成页面
	 * 
	 * @param map
	 *            模板中需要的数据
	 * @param TemplatePath
	 *            模板路径
	 * @exception
	 * @return 返回生成的页面中的字符串
	 */
	public static String process(Map map, String templatePath) throws Exception {
		return process("UTF-8", map, templatePath);
	}

	/**
	 * 通过模板生成页面
	 * 
	 * @param map
	 *            模板中需要的数据
	 * @param TemplatePath
	 *            模板路径
	 * @exception
	 * @return 返回生成的页面中的字符串
	 */
	public static String process(String encoding, Map map, String templatePath)
			throws Exception {
		Configuration conf = getConfiguration();
		Template temp = conf.getTemplate(templatePath, encoding);
		StringWriter sw = new StringWriter();
		temp.process(map, sw);
		return (sw.toString());
	}

	public static void process(Map map, String templatePath, String savePath)
			throws Exception {
		Configuration conf = getConfiguration();
		Template temp = conf.getTemplate(templatePath);
		File file = new File(savePath);
		file.mkdirs();
		file.delete();
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
				file), "UTF-8");
		try {
			temp.process(map, osw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
