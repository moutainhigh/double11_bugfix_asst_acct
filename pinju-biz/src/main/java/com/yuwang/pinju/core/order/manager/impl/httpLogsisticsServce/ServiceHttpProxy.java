package com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;

/** <p>Description: 快递宝 物流查询	  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-18
 */
public class ServiceHttpProxy extends BaseManager implements ServiceHttpLogistics{
	private String key;
	private String webserviceURL;
	
	@Override
	public String getTrackBybillcode(String billcode, String express,int resulttype) throws Exception {
		// TODO Auto-generated method stub
		String xml = "";
		// 通信对象
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setCharset(CHARSET);
		httpClient.setTimeOut(15);
		String postUrl = this.createPostUrl(express, billcode, resulttype);
		if(log.isInfoEnabled()){
			log.info("Logistics send post url is -->"+ postUrl);
		}
		httpClient.setReqContent(postUrl);
		if(httpClient.call()){
			xml = httpClient.getResContent();
		}else{
			xml = "";
			log.error("Event=[ServiceHttpProxy.getTrackBybillcode()]#获取物流信息超时");
		}
		return xml;
	}

	 @SuppressWarnings("unchecked")
	public <R> R xmlToBean(String xml,Class<R> beanClass){
			try {
				String tagName;
				String tagValue;
				List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
				
				StringReader read = new StringReader(xml);
				InputSource source = new InputSource(read);

				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(source);

				Element foo = doc.getRootElement();
				List<Element> allChildren = foo.getChildren();
				Element em=null;
				R beanObj = beanClass.newInstance();
				for(int i=0; i<allChildren.size(); i++) {
					em = (Element)allChildren.get(i);
					List<Element> children = em.getChildren();
					if(children.size()>0){
						for(int ii=0; ii<children.size(); ii++){
							HashMap<String,String> map = new HashMap<String,String>();
							Element m = (Element)children.get(ii);
							List<Element> _children = m.getChildren();
							for(int j=0 ;j<_children.size(); j++){
								Element _m = (Element)_children.get(j);
								tagName = _m.getName();
								tagValue = _m.getText();
								map.put(tagName, tagValue);
							}
							list.add(map);
							continue;
						}
					}
					tagName = em.getName();
					tagValue = em.getText();
					if (isExistField(tagName, beanClass)) {
						String methodName = toSetName(tagName);
						Method method = beanClass.getMethod(methodName, String.class);
						// 调用set方法放入数据
						method.invoke(beanObj, tagValue);
					}
				}
				if (isExistField("step", beanClass)) {
					String methodName = toSetName("step");
					Method method = beanClass.getMethod(methodName, List.class);
					// 调用set方法放入数据
					method.invoke(beanObj, list);
				}
				return beanObj;
			} catch (Exception e) {
				log.error("Event=[ServiceHttpProxy.xmlToBean()]analysis xml error",e);
			}
			return null;
		}
		
		/**
		 * 判断类中是否有该属性
		 */
		@SuppressWarnings("rawtypes")
		private boolean isExistField(String fieldName,Class c) {
			try {
				c.getDeclaredField(fieldName);
			} catch (SecurityException e) {
				return false;
			} catch (NoSuchFieldException e) {
				return false;
			}
			return true;
		}
		
		private String toSetName(String fieldName) {
			fieldName = fieldName.replaceFirst("^" + fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			return "set" + fieldName;
		}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWebserviceURL() {
		return webserviceURL;
	}

	public void setWebserviceURL(String webserviceURL) {
		this.webserviceURL = webserviceURL;
	}
	
	private String createPostUrl(String express,String billcode,int resulttype){
		String postUrl ="";
		try {
			String enc = CHARSET;
			StringBuffer bf = new StringBuffer("");
			bf.append("key=").append(URLEncoder.encode(key, enc));
			bf.append("&express=").append(URLEncoder.encode(express.trim(), enc));
			bf.append("&resulttype=").append(URLEncoder.encode(resulttype+"", enc));
			bf.append("&billcode=").append(URLEncoder.encode(billcode.trim(), enc));
			postUrl = webserviceURL.concat("?").concat(bf.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("Event=[ServiceHttpProxy.createPostUrl]##创建参数失败]", e);
		}
		return postUrl;
		}
}


