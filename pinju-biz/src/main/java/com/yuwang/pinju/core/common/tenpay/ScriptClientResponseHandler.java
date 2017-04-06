package com.yuwang.pinju.core.common.tenpay;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 返回HTML内容后台应答类
 * @author mayuanchao@pinju.com
 * @Date 2011-09-22
 */
public class ScriptClientResponseHandler extends ClientResponseHandler {

	protected void doParse() throws UnsupportedEncodingException {

		Pattern pattern = Pattern.compile("window.location.href.*=.*(http://.*|https://.*)['|\"]");
		Matcher matcher = pattern.matcher(this.getContent());
		
		if(matcher.find()) {
			//获取url
			String url = matcher.group(1);
			
			String queryString = HttpClientUtil.getQueryString(url);
			Map m = HttpClientUtil.queryString2Map(queryString);
			if(null != m && !m.isEmpty()) {
				String charset = this.getCharset();
				Iterator it = m.keySet().iterator();
				while(it.hasNext()) {
					String k = (String) it.next();
					String v = (String) m.get(k);
					this.setParameter(k, URLDecoder.decode(v, charset));
				}
			}
			
			return;
		}
	}
	
}
