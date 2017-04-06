package com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;

/** <p>Description: 	快递100 物流查询  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-11-2
 */
public class ServiceHundredHttpProxy extends BaseManager implements ServiceHttpLogistics{

	private String key ;
	private String webserviceURL;
	private String order= "asc";
	@Override
	public String getTrackBybillcode(String billcode, String express,
			 int show) throws Exception {
		// TODO Auto-generated method stub
		String xml = "";
		// 通信对象
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setCharset(CHARSET);
		String postUrl = this.createPostUrl(express, billcode, show+"");
		if(log.isInfoEnabled()){
			log.info("ServiceHundredHttpProxy send post url is -->"+ postUrl);
		}
		httpClient.setReqContent(postUrl);
		if(httpClient.call()){
			xml = httpClient.getResContent();
		}else{
			xml ="";
			log.error("Event=[ServiceHundredHttpProxy.getTrackBybillcode()]#获取物流信息超时");
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
						HashMap<String,String> map = new HashMap<String,String>();
						for(int ii=0; ii<children.size(); ii++){
							Element m = (Element)children.get(ii);
							tagName = m.getName();
							tagValue = m.getText();
							map.put(tagName, tagValue);
						}
						list.add(map);
						continue;
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
				log.error("Event=[ServiceHundredHttpProxy.xmlToBean() error]",e);
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
	
	/**
	 * <p>Description:
	 * id   身份key，为16位字母和数字的组合    key= 3d945b49b6d7be0b
	 * com   查询公司代码，不支持中文，对应的公司代码如表2 
	 * nu    查询快递的单号，请勿带特殊符号 
	 * valicode  查询快递的电话号码，目前只有佳吉物流需要这个参数 
	 * show   返回类型。0：表示返回json字符串，1：表示返回xml对象，2：表示返回html对象，3：表示返回text文本。如果不填的话，默认返回json字符串 
	 * muti   示格式。本网站提供显示一行还是多行的选择。返回结果全部按快递最近的状态进行排序，您可以选择一行或者多行记录。 
	 * order   排序。本网站提供倒序和顺序显示。如果不填的话，默认返回倒序。DESC：按时间由新到旧排列；ASC：按时间由旧到新排列。（大小不敏感） 
	 * </p>
	 * @param express   物流公司代码
	 * @param billcode  快递单号
	 * @param show    	返回类型  
	 * @return  postUrl
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-24
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String createPostUrl(String express,String billcode,String show){
		String postUrl ="";
		try {
			String enc = CHARSET;
			StringBuffer bf = new StringBuffer("");
			bf.append("id=").append(URLEncoder.encode(key, enc));
			bf.append("&com=").append(URLEncoder.encode(express.trim(), enc));
			bf.append("&nu=").append(URLEncoder.encode(billcode.trim(), enc));
			bf.append("&show=").append(URLEncoder.encode(show, enc));
			bf.append("&order=").append(order);
			postUrl = webserviceURL.concat("?").concat(bf.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("Event=[ServiceHundredHttpProxy.createPostUrl()]##创建参数失败]", e);
		}
		return postUrl;
	}
}


