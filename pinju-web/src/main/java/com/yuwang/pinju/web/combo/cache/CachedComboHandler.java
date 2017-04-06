package com.yuwang.pinju.web.combo.cache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.web.combo.ComboFile;
import com.yuwang.pinju.web.combo.ComboHandler;

public class CachedComboHandler implements ComboHandler {

	private final static Log log = LogFactory.getLog(CachedComboHandler.class.getPackage().getName());
	
	private ServletContext context;
	private String[] combos;

	// private Map<String, CacheValue> cache = new ConcurrentLRUMap<String, CacheValue>(50);

	public CachedComboHandler(ServletContext context, String[] combos) {
		this.context = context;
		this.combos = combos;
	}

	@Override
	public byte[] combo() {
		ComboFile[] files = new ComboFile[combos.length];
		int length = 0;
		for (int i = 0; i < combos.length; i++) {
			files[i] = ComboFile.createComboFile(context, combos[i]);
			length += files[i].getLength();
			if (i > 0) {
				length += 1;
			}
		}
		log.debug("combo total size: " + length);
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		for (int i = 0; i < files.length; i++) {
			try {
				out.write(getBytes(context, files[i]));
				out.write('\n');
			} catch (IOException e) {
				log.error("get bytes error, file: " + files[i].getPath(), e);
			}
		}
		return out.toByteArray();
	}

	public byte[] getBytes(ServletContext context, ComboFile file) {
		return readBytes(file);
	}

	/**
	private synchronized byte[] read(ComboFile file) {
		long t0, t1;
		t0 = System.nanoTime();
		byte[] hint = cache.get(file.getPath());
		if (hint != null) {
			return hint;
		}
		hint = readBytes(file);
		cache.put(file.getPath(), hint);
		t1 = System.nanoTime();
		if (log.isDebugEnabled()) {
			log.debug("not hint " + file.getPath() + ", read it, time: " + (t1 - t0) / 1000000D
					+ " ms, byte size: " + hint.length + ", file size: " + file.getLength());
		}
		return hint;
	}
	*/
	private byte[] readBytes(ComboFile file) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(file.getLength());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file.getFile()));
			byte[] bys = new byte[8192];
			for (int p = -1; (p = in.read(bys)) != -1;) {
				out.write(bys, 0, p);
			}
		} catch (IOException e) {
			log.error("read file error, file: " + file.getPath(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("close file error, file: " + file.getPath(), e);
				}
			}
		}
		return out.toByteArray();
	}
}
