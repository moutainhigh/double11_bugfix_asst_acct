package com.yuwang.pinju.core.common.tenpay;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.jdom.JDOMException;

/**
 * 返回XML内容后台应答类
 * @ref shixing@zba.com
 * @date 2011.09.15
 */
public class XMLClientResponseHandler extends ClientResponseHandler {

	protected void doParse() throws JDOMException, IOException {
		String xmlContent = this.getContent();
		
		//解析xml,得到map
		Map m = XMLUtil.doXMLParse(xmlContent);
		
		//设置参数
		Iterator it = m.keySet().iterator();
		while(it.hasNext()) {
			String k = (String) it.next();
			String v = (String) m.get(k);
			this.setParameter(k, v);
		}
		
	}
	
}
