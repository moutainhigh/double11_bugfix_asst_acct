package com.yuwang.pinju.core.margin.service;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * @Project: pinju-biz
 * @Description: WebService抽象类
 * @author 石兴 shixing@zba.com
 * @date 2011-8-3 上午11:31:41
 * @update 2011-8-3 上午11:31:41
 * @version V1.0
 */
public abstract class MarginWebService {
	
	Log log = LogFactory.getLog(this.getClass().getName());
	
	private Service  service = new Service();
	
	protected String namespaces ;
	
	private URL endPoint;
	
	protected Call call;
	
	public MarginWebService(){
		initWebService();
	}
	
	/**
	 * Created on 2011-8-2 
	 * <p>Discription:[初始化WebService]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void initWebService() {
		/*try {
			 this.namespaces = PinjuConstant.PINJU_MARGIN_SERVICE_NAMESPACE;
			 this.endPoint = new URL(PinjuConstant.PINJU_MARGIN_SERVICE_URL);
			 this.call = (Call)service.createCall();
		     call.setTargetEndpointAddress(endPoint);
		     call.setOperationName(new QName(namespaces, "queryMargin"));
		} catch (Exception e) {
			log.error("Event=[MarginServiceImpl#initWebService"+e.getMessage());
		}*/
	}
}
