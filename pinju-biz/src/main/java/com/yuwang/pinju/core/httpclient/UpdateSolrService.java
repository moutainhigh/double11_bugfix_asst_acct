package com.yuwang.pinju.core.httpclient;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import com.yuwang.pinju.core.constant.search.SearchConstent;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.domain.search.index.ItemIndexDO;
import com.yuwang.pinju.domain.search.index.ShopIndexD0;
import org.apache.solr.common.SolrInputDocument;
/**
 * solr 更新索引服务类
 * @author zhouzhaohua
 */
public class UpdateSolrService {

	static Logger logger = Logger.getLogger(UpdateSolrService.class.getName());
	
	private CommonsHttpSolrServer itemUpdateServer = null;
	private CommonsHttpSolrServer shopUpdateServer = null;
	
	private SolrInputDocument buildItemDocument(Object obj){
		SolrInputDocument docs= new SolrInputDocument();
	/*  ItemIndexDO  _obj = (ItemIndexDO)obj;
		docs.addField("id", _obj.getId());
		docs.addField("catetoryId", _obj.getCatetoryId());
		docs.addField("catetoryName", _obj.getCatetoryName());
		docs.addField("spuId", _obj.getSpuId());
		docs.addField("title", _obj.getTitle());
		docs.addField("storeCategories", _obj.getStoreCategories());
		docs.addField("sellerId", _obj.getSellerId());
		docs.addField("sellerNick", _obj.getSellerNick());
		docs.addField("propertyValuePair",_obj.getPropertyValuePair());
		docs.addField("picUrl", _obj.getPicUrl());
		docs.addField("price", _obj.getPrice());
		docs.addField("provinces", _obj.getProvinces());
		docs.addField("city", _obj.getCity());
		docs.addField("mailCosts", _obj.getMailCosts());
		docs.addField("deliveryCosts", _obj.getDeliveryCosts());
		docs.addField("emsCosts", _obj.getEmsCosts());
		//docs.addField("freeTemplateId", item.getFreeTemplateId());
		docs.addField("startTime", _obj.getStartTime());
		docs.addField("endTime", _obj.getEndTime());
		docs.addField("salesNum", _obj.getSalesNum());
		docs.addField("evaluateNum", _obj.getEvaluateNum());
		docs.addField("itemDegree", _obj.getItemDegree());
		docs.addField("features", _obj.getFeatures());
		docs.addField("status", _obj.getStatus());
		docs.addField("curStock", _obj.getCurStock());
		docs.addField("gmtCreate", _obj.getGmtCreate());
		docs.addField("code", _obj.getCode());*/
		
		Field [] fields = obj.getClass().getDeclaredFields();
		Method method = null;
		for(Field f : fields){
			try {
				method = obj.getClass().getMethod("get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1,f.getName().length()));
				docs.addField(f.getName(),method.invoke(obj));
			} catch( java.lang.NoSuchMethodException ee){
				logger.warn("Class "+obj.getClass().getName()+" method "+ "get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1,f.getName().length())+" not found");
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		return docs; 
	}
	private UpdateResponse deleteById(Long id,CommonsHttpSolrServer server) throws SolrServerException, IOException{
		server.deleteById(Long.toString(id));
		return server.commit();
	}
	
	/**
	 * @param id 商品ID
	 * @return true删除成功，false删除失败
	 * @throws Exception
	 */
	public Boolean deleteItemById(Long id){
		try{
			this.initSolrServer();
			UpdateResponse up = deleteById(id,this.itemUpdateServer);
			logger.debug("成功删除ID为"+id+"商品记录  "+up);
			return true;
		}catch(Exception e){
			logger.error("未能删除ID为"+id+"商品记录",e);
			return false;
		}
	}
	/**
	 * @param id 店铺ID
	 * @return true删除成功，false删除失败
	 * @throws Exception
	 */
	
	public Boolean deleteShopById(Long id){
		try{
			this.initSolrServer();
			UpdateResponse up = deleteById(id,this.shopUpdateServer);
			logger.debug("成功删除ID为"+id+"店铺记录  "+up);
			return true;
		}catch(Exception e){
			logger.error("未能删除ID为"+id+"店铺记录",e);
			return false;
		}
	}
	
	private void initSolrServer() throws Exception {
		try {
			if (itemUpdateServer == null) {
				logger.info("item solr init ...");
				itemUpdateServer = new CommonsHttpSolrServer(PinjuConstant.UPDATE_SEARCH_SERVERURL+ SearchConstent.ITEM_SEARCH_PATH);
				itemUpdateServer.setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
				itemUpdateServer.setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				itemUpdateServer.setFollowRedirects(false);
				itemUpdateServer.setDefaultMaxConnectionsPerHost(PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				itemUpdateServer.setMaxRetries(0);
				itemUpdateServer.setAllowCompression(true);
				itemUpdateServer.setMaxTotalConnections(PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				logger.info("item solr init end ...");
			}
			if (shopUpdateServer == null) {
				logger.info("shop solr init ...");
				shopUpdateServer = new CommonsHttpSolrServer(PinjuConstant.UPDATE_SEARCH_SERVERURL+ SearchConstent.SHOP_SEARCH_PATH);
				shopUpdateServer.setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
				shopUpdateServer.setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				shopUpdateServer.setFollowRedirects(false);
				shopUpdateServer.setDefaultMaxConnectionsPerHost(PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				shopUpdateServer.setMaxRetries(0);
				shopUpdateServer.setAllowCompression(true);
				shopUpdateServer.setMaxTotalConnections(PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				logger.info("shop solr init end ...");
			}

		} catch (Exception e) {
			logger.error("配置solr错误！", e);
			throw e;
		}
	}
		
	/**
	 * @param obj one of indexDO instance or indexDo collect
	 * @param server
	 * @throws Exception
	 */
	private UpdateResponse solrInput(Object obj,CommonsHttpSolrServer server)throws Exception{
		try {
			if(obj instanceof Collection){
				@SuppressWarnings("rawtypes")
				Collection objc = (Collection)obj;
				List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
				for(Object _obj : objc){
					docs.add(this.buildItemDocument(_obj));
				}
				server.add(docs);
				return server.commit();
			}
			else{
				server.add(this.buildItemDocument(obj));
				return server.commit();
			}
		} catch (Exception e) {
			logger.error("UpdateSolrService#solrInput",e);
			throw e;
		}
	}
	
	/**
	 * 索引商品数据
	 * @param items
	 * @return true 更新/添加成功,false更新/添加失败
	 */
	
	public Boolean itemInput(ItemIndexDO item){
		try {
			this.initSolrServer();
			UpdateResponse up  = this.solrInput(item, itemUpdateServer);
			logger.debug("solr 添加/更新1条item数据成功 "+item.toString()+up);
			return true;
		} catch (Exception e) {
			logger.error("更新商品数据失败UpdateSolrService#solrItemInput(ItemIndexDO item)"+item.toString(),e);
			return false;
		}
	}
	
	/**
	 * 索引商品数据
	 * @param items
	 * @return true 更新/添加成功,false更新/添加失败
	 */
	public Boolean itemInput(List<ItemIndexDO> items){
		try {
			this.initSolrServer();
			UpdateResponse up  = this.solrInput(items, itemUpdateServer);
			logger.debug("solr 添加/更新"+items.size()+"条item数据成功 "+up);
			return true;
		} catch (Exception e) {
			logger.error("更新商品索引失败UpdateSolrService#solrItemInput(List<ItemIndexDO> items)",e);
			return false;
		}
	}
	
	/**
	 * 索引店铺数据
	 * @param shops
	  * @return true 更新/添加成功,false更新/添加失败
	 */
	
	public Boolean shopInput(List<ShopIndexD0> shops)  {
		try {
			this.initSolrServer();
			UpdateResponse up   = this.solrInput(shops, shopUpdateServer);
			logger.debug("solr 添加/更新"+shops.size()+"条shop数据成功 "+up);
			return true;
		} catch (Exception e) {
			logger.error("更新店铺数据失败UpdateSolrService#solrShopInput(List<ShopIndexD0> shops)",e);
			return false;
		}
	}
	
	/**
	 * 索引店铺数据
	 * @param shop
	 * @return true 更新/添加成功,false更新/添加失败
	 */
	public Boolean shopInput(ShopIndexD0 shop)  {
		try {
			this.initSolrServer();
			UpdateResponse up  = this.solrInput(shop, shopUpdateServer);
			logger.debug("solr 添加一条shop数据成功 "+shop.toString()+up);
			return true;
		} catch (Exception e) {
			logger.error("UpdateSolrService#solrShopInput(ShopIndexD0 shop)"+shop.toString(),e);
			return false;
		}
	}
}
