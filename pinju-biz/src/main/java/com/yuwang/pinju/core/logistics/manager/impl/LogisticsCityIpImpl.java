package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsAreaCarriageDAO;
import com.yuwang.pinju.core.logistics.dao.LogisticsCarriageDAO;
import com.yuwang.pinju.core.logistics.dao.LogisticsCityIpDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;

public class LogisticsCityIpImpl implements LogisticsCityIpManager{
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private static Map<String, String> regionAll = new HashMap<String, String>();
	static {
		regionAll.put("11", "北京");
		regionAll.put("12", "天津");
		regionAll.put("13", "河北");
		regionAll.put("14", "山西");
		regionAll.put("15", "内蒙");
		regionAll.put("21", "辽宁");
		regionAll.put("22", "吉林");
		regionAll.put("23", "黑龙江");
		regionAll.put("31", "上海");
		regionAll.put("32", "江苏");
		regionAll.put("33", "浙江");
		regionAll.put("34", "安徽");
		regionAll.put("35", "福建");
		regionAll.put("36", "江西");
		regionAll.put("37", "山东");
		regionAll.put("41", "河南");
		regionAll.put("42", "湖北");
		regionAll.put("43", "湖南");
		regionAll.put("44", "广东");
		regionAll.put("45", "广西");
		regionAll.put("46", "海南");
		regionAll.put("50", "重庆");
		regionAll.put("51", "四川");
		regionAll.put("52", "贵州");
		regionAll.put("53", "云南");
		regionAll.put("54", "西藏");
		regionAll.put("61", "陕西");
		regionAll.put("62", "甘肃");
		regionAll.put("63", "青海");
		regionAll.put("64", "宁夏");
		regionAll.put("65", "新疆");
		regionAll.put("71", "台湾");
		regionAll.put("81", "香港");
		regionAll.put("82", "澳门");
		regionAll.put("99", "海外");
	}
	
	private LogisticsCityIpDAO logisticsCityIpDAO;
	
	private LogisticsAreaCarriageDAO logisticsAreaCarriageDAO;
	
	private LogisticsCarriageDAO logisticsCarriageDAO;
	
	private MemcachedClient ipMemcachedClient;
	
	private MemcachedClient logisticsMemcachedClient;
	
	
	/** 为缓存添加前缀防止重复 */
	private final static String IP_MEMCACHE_SUFFIX = "ip";
	private final static String LGA_LIST_MEMCACHE_SUFFIX = "lga";
	private final static String LGD_LIST_MEMCACHE_SUFFIX = "lgd";
	/** 缓存时间  */
	private final static int LG_LIST_MEMCACHE_CACHETIME = 60*60*12;
	private final static int IP_MEMCACHE_CACHETIME = 60*60*24*15;  
	
	
	/**
	 * 初始化memcache配置
	 */
	public void init() {
	}
	
	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 当前地区IP地址
	 * @author heyong
	 * @return   省份IP段
	 * 
	 *    !!!注意： IP转化方法：普通IP：A.B.C.D => A*256*256*256 + B*256*256 + C*256 + D
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(String cityIp)throws ManagerException {
		LogisticsCityIpDO logisticsCityDO = null;
		
		if (cityIp != null && StringUtil.split(cityIp,".").length == 4) {
			String[] ipStr = StringUtil.split(cityIp,".");
			long ipA       = Long.parseLong(ipStr[0]);
			long ipB       = Long.parseLong(ipStr[1]);
			long ipC       = Long.parseLong(ipStr[2]);
			long ipD       = Long.parseLong(ipStr[3]);
			
			long ipLong    = (ipA * 256 * 256 * 256) + (ipB * 256 * 256) + (ipC * 256)	+ ipD;
			
			try {
				//缓存中获取ip数据
				logisticsCityDO = (LogisticsCityIpDO) ipMemcachedClient.get(IP_MEMCACHE_SUFFIX+ipLong);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]缓存服务器MemcachedException,[read]", e);
			}
			
			if (logisticsCityDO == null) {
				try {
					//缓存中没有则查询数据库获取
					logisticsCityDO = logisticsCityIpDAO.loadLogisticsCityIpByIp(ipLong);
					if (logisticsCityDO != null) {
						ipMemcachedClient.set(IP_MEMCACHE_SUFFIX+ipLong, IP_MEMCACHE_CACHETIME, logisticsCityDO);
					}
				} catch (DaoException e) {
					throw new ManagerException("Event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]根据转换后的IP得到省份信息", e);
				}  catch (TimeoutException e) {
					log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]连接缓存服务器超时,[read]", e);
				} catch (InterruptedException e) {
					log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]缓存服务器线程中断,[read]", e);
				} catch (MemcachedException e) {
					log.error("event=[LogisticsCityIpImpl#loadLogisticsCityIpByIp]缓存服务器MemcachedException,[read]", e);
				}
			}
		}
		return logisticsCityDO;
	}
	
	private Map<String, String> getRegion(String province) throws ManagerException {
		Map<String, String> regionCode = new HashMap<String, String>();
		for (Entry<String, String> entry : regionAll.entrySet()) {
			if(province != null && province.indexOf(entry.getValue()) > -1) {
				regionCode.put(entry.getKey(), entry.getValue());
			}
		}
		if (regionCode.size() < 1) {
			regionCode.put("99", "海外");
		}
		return regionCode;
	}

	public String getRegionCode(String cityIp) throws ManagerException  {
		String regionCode = null;
		LogisticsCityIpDO cityDo = this.loadLogisticsCityIpByIp(cityIp);
		if (cityDo != null) {
			Map<String, String> region  = this.getRegion(cityDo.getProvince());
			for (Entry<String, String> entry : region.entrySet()) {
				regionCode = entry.getKey();
			}
		}
		return regionCode;
	}
	
	@Override
	public Map<String, String> getDefaultRegionCarriage(Long logisticsTemplateId, String cityIp) throws ManagerException {
		int regionCode = 0;
		
		Map<String, String> defaultRegionCarriage = new HashMap<String, String>();
		defaultRegionCarriage.put("1", null);
		defaultRegionCarriage.put("2", null);
		defaultRegionCarriage.put("3", null);
		defaultRegionCarriage.put("cityName","全部");
		
		List<LogisticsAreaCarriageDO> areaCarriageList = new ArrayList<LogisticsAreaCarriageDO>();
		List<LogisticsCarriageDO> carriageList = new ArrayList<LogisticsCarriageDO>();
		
		LogisticsCityIpDO cityDo = this.loadLogisticsCityIpByIp(cityIp);
		
		if (cityDo != null) {
			Map<String, String> region  = this.getRegion(cityDo.getProvince());
			for (Entry<String, String> entry : region.entrySet()) {
				regionCode = Integer.parseInt(entry.getKey());
				defaultRegionCarriage.put("cityName", entry.getValue());
			}
			
			try {
				//获取memcache值
				areaCarriageList = (List<LogisticsAreaCarriageDO>) logisticsMemcachedClient.get(LGA_LIST_MEMCACHE_SUFFIX+logisticsTemplateId+"|"+regionCode);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			
			try {
				if (areaCarriageList == null && regionCode != 0) {
					//查询获取区域运费
					LogisticsAreaCarriageDO areaCarriageDO = new LogisticsAreaCarriageDO();
					areaCarriageDO.setTemplateId(logisticsTemplateId);
					areaCarriageDO.setAreaId(regionCode);
					areaCarriageList = logisticsAreaCarriageDAO.selectLogisticsAreaCarriageList(areaCarriageDO);
					
					if (areaCarriageList != null && areaCarriageList.size() > 0) {
						logisticsMemcachedClient.set(LGA_LIST_MEMCACHE_SUFFIX+logisticsTemplateId+"|"+regionCode, LG_LIST_MEMCACHE_CACHETIME, areaCarriageList);
					}
				}
			} catch (DaoException e) {
				throw new ManagerException("Event=[LogisticsCityIpImpl#getDefaultRegionCarriage]根据IP获得区域运费错误", e);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			if (areaCarriageList != null) {
				for (LogisticsAreaCarriageDO lado : areaCarriageList) {
					defaultRegionCarriage.put(lado.getLogisticsTypeId().toString(), lado.getAreaCarriage().toString());
				}
			}
		}
			
		if (areaCarriageList == null || (areaCarriageList != null && areaCarriageList.size() < 3)) {
			try {
				//如果有区域运费不存在则继续从memcache获取默认运费
				carriageList = (List<LogisticsCarriageDO>) logisticsMemcachedClient.get(LGD_LIST_MEMCACHE_SUFFIX+logisticsTemplateId);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			
			try {
				if (carriageList == null) {
					//如果默认运费缓存中不存在则查数据库
					carriageList = logisticsCarriageDAO.getLogisticsCarriageList(logisticsTemplateId);
					if (carriageList != null && carriageList.size() > 0) {
						logisticsMemcachedClient.set(LGD_LIST_MEMCACHE_SUFFIX+logisticsTemplateId, LG_LIST_MEMCACHE_CACHETIME, carriageList);
					}
				}
				if (carriageList != null) {
					for (LogisticsCarriageDO lcdo : carriageList) {
						if (defaultRegionCarriage.get(lcdo.getLogisticsTypeId().toString()) == null) {
							defaultRegionCarriage.put(lcdo.getLogisticsTypeId().toString(), lcdo.getDefaultCarriage().toString());
						}
					}
				}
			} catch (DaoException e) {
				throw new ManagerException("Event=[LogisticsCityIpImpl#getDefaultRegionCarriage]根据IP获得区域运费错误", e);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
		}
		
		return defaultRegionCarriage;
	}

	@Override
	public Map<String, String> getRegionCarriage(Long logisticsTemplateId, String cityName) throws ManagerException {
		Map<String, String> regionCarriage = new HashMap<String, String>();
		regionCarriage.put("1", null);
		regionCarriage.put("2", null);
		regionCarriage.put("3", null);
		
		String regionCode = null;
		List<LogisticsAreaCarriageDO> areaCarriageList = new ArrayList<LogisticsAreaCarriageDO>();
		List<LogisticsCarriageDO> carriageList = new ArrayList<LogisticsCarriageDO>();
		
		for (Entry<String, String> entry : regionAll.entrySet()) {
			if (cityName != null && cityName.indexOf(entry.getValue()) > -1) {
				regionCode = entry.getKey();
			}
		}
		
		if (regionCode != null) {
			try {
				//获取memcache值
				areaCarriageList = (List<LogisticsAreaCarriageDO>) logisticsMemcachedClient.get(LGA_LIST_MEMCACHE_SUFFIX+logisticsTemplateId+"|"+regionCode);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			
			try {
				if (areaCarriageList == null) {
					//查询获取区域运费
					LogisticsAreaCarriageDO areaCarriageDO = new LogisticsAreaCarriageDO();
					areaCarriageDO.setTemplateId(logisticsTemplateId);
					areaCarriageDO.setAreaId(Integer.parseInt(regionCode));
					areaCarriageList = logisticsAreaCarriageDAO.selectLogisticsAreaCarriageList(areaCarriageDO);
				
					if (areaCarriageList != null && areaCarriageList.size() > 0) {
						logisticsMemcachedClient.set(LGA_LIST_MEMCACHE_SUFFIX+logisticsTemplateId+"|"+regionCode, LG_LIST_MEMCACHE_CACHETIME, areaCarriageList);
					}
				}
			} catch (DaoException e) {
				throw new ManagerException("Event=[LogisticsCityIpImpl#getRegionCarriage]根据IP获得默认运费错误", e);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			if (areaCarriageList != null) {
				for (LogisticsAreaCarriageDO lado : areaCarriageList) {
					regionCarriage.put(lado.getLogisticsTypeId().toString(), lado.getAreaCarriage().toString());
				}
			}
		}
		
		
		if (areaCarriageList == null || (areaCarriageList != null && areaCarriageList.size() < 3)) {
			try {
				//如果有区域运费不存在则继续从memcache获取默认运费
				carriageList = (List<LogisticsCarriageDO>) logisticsMemcachedClient.get(LGD_LIST_MEMCACHE_SUFFIX+logisticsTemplateId);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getDefaultRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
			
			try {
				if (carriageList == null) {
					//如果默认运费缓存中不存在则查数据库
					carriageList = logisticsCarriageDAO.getLogisticsCarriageList(logisticsTemplateId);
					if (carriageList != null && carriageList.size() > 0) {
						logisticsMemcachedClient.set(LGD_LIST_MEMCACHE_SUFFIX+logisticsTemplateId, LG_LIST_MEMCACHE_CACHETIME, carriageList);
					}
				}
				if (carriageList != null) {
					for (LogisticsCarriageDO lcdo : carriageList) {
						if (regionCarriage.get(lcdo.getLogisticsTypeId().toString()) == null) {
							regionCarriage.put(lcdo.getLogisticsTypeId().toString(), lcdo.getDefaultCarriage().toString());
						}
					}
				}
			} catch (DaoException e) {
				throw new ManagerException("Event=[LogisticsCityIpImpl#getDefaultRegionCarriage]根据IP获得默认运费错误", e);
			} catch (TimeoutException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]连接缓存服务器超时,[read]", e);
			} catch (InterruptedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器线程中断,[read]", e);
			} catch (MemcachedException e) {
				log.error("event=[LogisticsCityIpImpl#getRegionCarriage]缓存服务器MemcachedException,[read]", e);
			}
		}
		
		return regionCarriage;
	}
	
	public void setLogisticsAreaCarriageDAO(
			LogisticsAreaCarriageDAO logisticsAreaCarriageDAO) {
		this.logisticsAreaCarriageDAO = logisticsAreaCarriageDAO;
	}
	
	public void setLogisticsCarriageDAO(LogisticsCarriageDAO logisticsCarriageDAO) {
		this.logisticsCarriageDAO = logisticsCarriageDAO;
	}

	public void setLogisticsCityIpDAO(LogisticsCityIpDAO logisticsCityIpDAO) {
		this.logisticsCityIpDAO = logisticsCityIpDAO;
	}

	public void setLogisticsMemcachedClient(MemcachedClient logisticsMemcachedClient) {
		this.logisticsMemcachedClient = logisticsMemcachedClient;
	}

	public void setIpMemcachedClient(MemcachedClient ipMemcachedClient) {
		this.ipMemcachedClient = ipMemcachedClient;
	}

	@Override
	public boolean deleteMemcachedByKey(Long logisticsTemplateId) {
		boolean areaflag = false;
		boolean defflag = false;
		try {
			for (Entry<String, String> entry : regionAll.entrySet()) {
				String regionCode = entry.getKey();
				boolean flag = logisticsMemcachedClient.delete(LGA_LIST_MEMCACHE_SUFFIX+logisticsTemplateId+"|"+regionCode);
				if (flag) {
					areaflag = true;
				}
			}
			
			defflag = logisticsMemcachedClient.delete(LGD_LIST_MEMCACHE_SUFFIX+logisticsTemplateId);
		} catch (TimeoutException e) {
			log.error("event=[LogisticsCityIpImpl#deleteMemcachedByKey]连接缓存服务器超时,[read]", e);
		} catch (InterruptedException e) {
			log.error("event=[LogisticsCityIpImpl#deleteMemcachedByKey]缓存服务器线程中断,[read]", e);
		} catch (MemcachedException e) {
			log.error("event=[LogisticsCityIpImpl#deleteMemcachedByKey]缓存服务器MemcachedException,[read]", e);
		}
		
		return areaflag && defflag;
	}
}
