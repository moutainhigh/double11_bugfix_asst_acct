package com.yuwang.pinju.core.httpclient;

import java.io.IOException;
import java.util.Map;
import com.yuwang.pinju.domain.search.SearchBaseDO;
/**
 * @author zhouzhaohua
 * * @deprecated SDO 遗留的和盛大搜索引擎通信接口
 */
public interface HttpService {
	/**
	 * 
	 * @param url 请求的全路径包含请求参数
	 * @return 返回json格式数据
	 */
	public String doGet(String url)throws IOException;
	/**
	 * @param map 请求参数key/value 对
	 * @return 返回json格式数据
	 */
	public String doGet(Map<String,String> map)throws IOException;
	/**
	 * 
	 * @param obj 查询参数对象 
	 * @return 返回json格式数据
	 */
	public String doGet(SearchBaseDO query) throws IOException;
	
	/**
	 * @param map 请求参数key/value 对
	 * @return 返回json格式数据
	 */
	public String doPost(Map<String,String> map)throws IOException;
}
