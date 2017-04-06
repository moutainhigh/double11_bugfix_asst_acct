package com.yuwang.pinju.core.util.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseConfig {

	private static long expireTime = 60 * 1000L;

	private long lastCheckTime;

	private Properties m_props;

	private long lastModified;

	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	/**
	 * 构造方法 初始化 获得当前时间。获得配置文件的最后一次修改日期
	 * 
	 * @param
	 * 
	 * @exception
	 * 
	 * @return 无
	 */
	public BaseConfig() {
		this.lastCheckTime = System.currentTimeMillis();
		this.load();
	}

	/**
	 * 配置文件的最后一次修改日期
	 * 
	 * @param
	 * 
	 * @exception
	 * 
	 * @return 无
	 */
	public void load() {
		try {
			File file = new File(getFilepath());
			this.lastModified = file.lastModified();
			m_props = null;
			Properties props = new Properties();
			InputStream is = new java.io.FileInputStream(file);
			props.load(is);
			is.close();
			m_props = props;
		} catch (Exception ex) {
			m_props = new Properties();
		}
	}

	/**
	 * 存储配置文件信息
	 * 
	 * @param
	 * 
	 * @exception
	 * 
	 * @return 无
	 */
	public void store() {
		if (m_props.size() > 0) {
			try {
				File f = new File(getFilepath());
				File parent = new File(f.getParent());
				try {
					parent.mkdirs();
				} catch (Exception ex) {
					log.error("配置文件目录生成错误",ex);
				}

				if (!f.exists()) {
					f.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(f);
				m_props.store(out, this.getPorpFileName());
				out.close();
			} catch (Exception ex) {
				log.error("存储配置文件报错", ex);
			}
		}
	}

	public abstract String getPorpFileName();

	public String getProperty(String key, String defVal) {
		refresh();
		String result = m_props.getProperty(key, defVal);
		return result;
	}

	public int getProperty(String key, int defVal) {
		refresh();
		int res = defVal;
		try {
			res = Integer.parseInt(m_props.getProperty(key, ""));
		} catch (Exception ex) {
			log.error("转换为Integer报错",ex);
		}
		return res;
	}

	public String getProperty(String key) {
		return getProperty(key, "");
	}

	public Properties getProperties() {
		return m_props;
	}

	public void setProperty(String key, String value) {
		if (key != null && value != null) {
			m_props.setProperty(key, value);
		}
	}

	private void refresh() {
		long t1 = System.currentTimeMillis();
		if (t1 - this.lastCheckTime > expireTime) {
			// 如果超时，则检查文件内容是否变化

			File f = new File(getFilepath());
			if (f.lastModified() != lastModified) // 如果文件发生变化则重新读入内存
				load();
			this.lastCheckTime = System.currentTimeMillis();
		}
	}

	public String getFilepath() {
		return Thread.currentThread().getContextClassLoader().getResource(
				this.getPorpFileName()).getFile();
	}
}
