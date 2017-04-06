package com.yuwang.pinju.web.combo;

import java.io.File;

import javax.servlet.ServletContext;

public class ComboFile {

	private File file;
	private String path;
	private int length;

	private ComboFile(ServletContext context, String filename) {
		String path = context.getRealPath(clearSlash(filename));
		this.file = new File(path);
		this.path = this.file.getAbsolutePath();
		this.length = (int)this.file.length();
	}

	public static ComboFile createComboFile(ServletContext context, String filename) {
		if (filename == null) {
			return null;
		}
		if (filename.endsWith(".js")) {
			return new JavaScriptComboFile(context, filename);
		}
		if (filename.endsWith(".css")) {
			return new CssComboFile(context, filename);
		}
		throw new UnsupportedOperationException("unsupported " + filename + " combo");
	}

	public File getFile() {
		return file;
	}
	public String getPath() {
		return path;
	}
	public int getLength() {
		return length;
	}

	public static String clearSlash(String name) {
		if (name == null) {
			return name;
		}
		char[] chs = name.trim().toCharArray();
		int offset = 0;
		while(offset < chs.length) {
			if (chs[offset] != '/' && chs[offset] != '\\') {
				break;
			}
			offset++;
		}
		return new String(chs, offset, name.length() - offset);
	}

	private static class JavaScriptComboFile extends ComboFile {
		private final static String DIR = "default/js/";
		public JavaScriptComboFile(ServletContext context, String filename) {
			super(context, DIR + filename);
		}
	}

	private static class CssComboFile extends ComboFile {
		private final static String DIR = "default/css/";
		public CssComboFile(ServletContext context, String filename) {
			super(context, DIR + filename);
		}
	}
}
