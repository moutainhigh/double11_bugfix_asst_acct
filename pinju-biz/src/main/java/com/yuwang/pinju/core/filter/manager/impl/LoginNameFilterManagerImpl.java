package com.yuwang.pinju.core.filter.manager.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.filter.FilterResult;
import com.yuwang.pinju.core.util.ChineseUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.FileUtil;

/**
 * <p>品聚会员名过滤器</p>
 *
 * @author gaobaowen
 * @since 2011-8-8 14:58:25
 */
public class LoginNameFilterManagerImpl extends AbstractFilterManager {

	private final static Log log = LogFactory.getLog(LoginNameFilterManagerImpl.class);
	private final static String FILTER_FILENAME = "loginname-filter.txt";
	private final static String FULL_MARK = "@ ";
	private final static Pattern PUNCATION = Pattern.compile("\\pP");

	private String[] fullFilterWords;
	private String[] partFilterWords;

	public LoginNameFilterManagerImpl() {
	}

	public void init() {
		log.debug("load " + FILTER_FILENAME + " from classpath");
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new InputStreamReader(FileUtil.loadStream(FILTER_FILENAME), PinjuConstant.DEFAULT_CHARSET));
			loadWords(reader);
			if (log.isInfoEnabled()) {
				log.info("load full filter " + fullFilterWords.length +  " word(s), part filter " + partFilterWords.length + " word(s)");
			}
		} catch (IOException e) {
			log.error("init load " + FILTER_FILENAME + " resource error", e);
		} finally {
			FileUtil.close(reader, log);
		}
	}

	@Override
	public FilterResult doFilter(String statement, boolean ignorePunctuation) {
		if (log.isDebugEnabled()) {
			log.debug("doFilter, statement: " + statement + ", ignorePunctuation: " + ignorePunctuation);
		}
		FilterResult filterResult = new FilterResult(statement);
		if (EmptyUtil.isBlank(statement)) {
			log.warn("doFilter, statement is null or empty need not check");
			return filterResult;
		}
		if (ignorePunctuation) {
			statement = PUNCATION.matcher(statement).replaceAll("");
			if (log.isDebugEnabled()) {
				log.debug("doFilter, remove statement punctuation, [" + statement + "]");
			}
		}
		statement = statement.toLowerCase();
		statement = ChineseUtil.toSimple(statement);
		for (int i = 0; i < fullFilterWords.length; i++) {
			if (statement.equals(fullFilterWords[i])) {				
				filterResult.addError(0, fullFilterWords[i]);
				log.warn("doFilter, statement has full filter words: [" + fullFilterWords[i] + "]");
				return filterResult;
			}
		}
		for (int i = 0; i < partFilterWords.length; i++) {
			int offset = statement.indexOf(partFilterWords[i]);
			if (offset > -1) {
				filterResult.addError(offset, partFilterWords[i]);
				log.warn("doFilter, statement has part filter words: [" + partFilterWords[i] + "]");
				return filterResult;
			}
		}
		return filterResult;
	}

	private void loadWords(LineNumberReader reader) throws IOException {
		Set<String> full = new TreeSet<String>();
		Set<String> part = new TreeSet<String>();
		for (String str = null; (str = reader.readLine()) != null; ) {
			str = clearComment(str);
			if (EmptyUtil.isBlank(str)) {
				continue;
			}
			if (str.startsWith(FULL_MARK)) {
				String p = str.substring(2).toLowerCase();
				full.add(p);
				if (log.isDebugEnabled()) {
					log.debug("load full word: " + str + ", put: " + p + ", line: " + reader.getLineNumber());
				}
				continue;
			}
			String c = ChineseUtil.toSimple(str);
			part.add(c);
			if (log.isDebugEnabled()) {
				log.debug("load part word: " + str + ", put: " + c + ", line: " + reader.getLineNumber());
			}
		}
		fullFilterWords = full.toArray(new String[full.size()]);
		partFilterWords = part.toArray(new String[part.size()]);
	}

	private String clearComment(String line) {
		int offset = line.indexOf('#');
		if (offset < 0) {
			return line.trim();
		}
		return line.substring(0, offset).trim();
	}
}
