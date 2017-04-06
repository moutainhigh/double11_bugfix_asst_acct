package com.yuwang.pinju.core.shop.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.yuwang.pinju.Constant.ShopDecorationConstant;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.manager.ShopModulePictureRotationManager;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;


/**
 * 图片轮播
 * @author Administrator
 *
 * @since 2011-7-4
 */
public class ShopModulePictureRotationManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopModulePictureRotationManager {

	
	/**
	 * 保存图片轮播参数
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object savePrictureRotationEditParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			List<ShopUserModuleParamDO> resultList = shopUserModuleParamDao
					.queryShopUserModuleParam(shopUserModuleParamDO);
			if (resultList != null && resultList.size() > 0) {
				return shopUserModuleParamDao
						.updateShopUserModuleParam(shopUserModuleParamDO);
			}
			return shopUserModuleParamDao
					.insertShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("保存图片轮播参数出错", e);
		}
	}
	
	
	/**
	 * 获取模块信息用来显示展示页
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		List<String> pictureRotationHeightList = ShopDecorationConstant.PICTURE_ROTATION_HEGHT_SIZE_LIST;
		List<String> pictureRotationEffectList = ShopDecorationConstant.PICTURE_ROTATION_EFFECT_LIST;
		properties.put("pictureRotationHeightList", pictureRotationHeightList);
		properties.put("pictureRotationEffectList", pictureRotationEffectList);
		properties.put("pageId", shopUserModuleParamDO.getUserPageId());
		properties.put("moduleId", shopUserModuleParamDO.getModuleId());
		properties.put("fileServer",PinjuConstant.VIEW_IMAGE_SERVER);

		if (properties.getProperty("isShowTitle") != null
				&& properties.getProperty("isShowTitle").length() > 0) {
			properties.put("isShowTitle", properties.getProperty("isShowTitle"));
		}
		if (properties.getProperty("moduleheight") != null
				&& properties.getProperty("moduleheight").length() > 0) {
			properties.put("moduleheight", properties.getProperty("moduleheight"));
		}
		if (properties.getProperty("effect") != null
				&& properties.getProperty("effect").length() > 0) {
			properties.put("effect", properties.getProperty("effect"));
		}
		if (properties.getProperty("moduleTitle") != null
				&& properties.getProperty("moduleTitle").length() > 0) {
			properties.put("moduleTitle", properties.getProperty("moduleTitle"));
		}
		
		if (properties.getProperty("picSize") != null
				&& properties.getProperty("picSize").length() > 0) {
			properties.put("picSize", properties.getProperty("picSize"));
		}
		
		if(properties.getProperty("urlResultValue")!=null && properties.getProperty("urlResultValue").length()>0){
			String urlString[] = properties.getProperty("urlResultValue").split(",");
//			List<String> urList = new ArrayList<String>();
//			for(int i=0;i<urlString.length;i++){
//				urList.add(urlString[i]);
//			}
			properties.put("urlString", urlString);
		}else{
			String urlString[] = new String[]{""};
			properties.put("urlString", urlString);
		}
		
		Iterator it=properties.entrySet().iterator();
		String temp[] = new String[properties.entrySet().size()];
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    Object key = entry.getKey();
		    if(key.toString().indexOf("myfile") != -1){
		    	Object value = entry.getValue();
		    	temp[Integer.parseInt(key.toString().substring(key.toString().length()-1 , key.toString().length()))-1] = value.toString();
//		    	files.add(Integer.parseInt(key.toString().substring(key.toString().length()-1 , key.toString().length()))-1,value.toString());
		    }
		    
		} 
		
		
		List files2 = new ArrayList();
		for(int i=0;i<temp.length;i++){
			if(temp[i] == null){
				continue;
			}
			files2.add(temp[i]);
		}
		if(files2!=null && files2.size()>0){
			properties.put("files", files2);
		}
	}
	
	/**
	 * 获取模块信息用来显示编辑页
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		List<String> pictureRotationHeightList = ShopDecorationConstant.PICTURE_ROTATION_HEGHT_SIZE_LIST;
		List<String> pictureRotationEffectList = ShopDecorationConstant.PICTURE_ROTATION_EFFECT_LIST;
		properties.put("pictureRotationHeightList", pictureRotationHeightList);
		properties.put("pictureRotationEffectList", pictureRotationEffectList);
		properties.put("pageId", shopUserModuleParamDO.getUserPageId());
		properties.put("moduleId", shopUserModuleParamDO.getModuleId());
		properties.put("userId", shopUserModuleParamDO.getUserId());
		properties.put("fileServer",PinjuConstant.VIEW_IMAGE_SERVER);

		if (properties.getProperty("isShowTitle") != null
				&& properties.getProperty("isShowTitle").length() > 0) {
			properties.put("isShowTitle", properties.getProperty("isShowTitle"));
		}
		if (properties.getProperty("moduleheight") != null
				&& properties.getProperty("moduleheight").length() > 0) {
			properties.put("moduleheight", properties.getProperty("moduleheight"));
		}
		if (properties.getProperty("effect") != null
				&& properties.getProperty("effect").length() > 0) {
			properties.put("effect", properties.getProperty("effect"));
		}
		if (properties.getProperty("moduleTitle") != null
				&& properties.getProperty("moduleTitle").length() > 0) {
			properties.put("moduleTitle", properties.getProperty("moduleTitle"));
		}
		if(properties.getProperty("urlResultValue")!=null && properties.getProperty("urlResultValue").length()>0){
			String urlString[] = properties.getProperty("urlResultValue").split(",");
//			List<String> urList = new ArrayList<String>();
//			for(int i=0;i<urlString.length;i++){
//				urList.add(urlString[i]);
//			}
			properties.put("urlString", urlString);
		}else{
			String urlString[] = new String[]{""};
			properties.put("urlString", urlString);
		}
//		else{
//			String urlString[] =
//		}
		
		Iterator it=properties.entrySet().iterator();
		String temp[] = new String[properties.entrySet().size()];
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    Object key = entry.getKey();
		    if(key.toString().indexOf("myfile") != -1){
		    	Object value = entry.getValue();
//		    	files.add(value.toString());
		    	temp[Integer.parseInt(key.toString().substring(key.toString().length()-1 , key.toString().length()))-1] = value.toString();
//		    	files.add(Integer.parseInt(key.toString().substring(key.toString().length()-1 , key.toString().length()))-1,value.toString());
		    }
		    
		} 
		List files2 = new ArrayList();
		for(int i=0;i<temp.length;i++){
			if(temp[i] == null){
				continue;
			}
			files2.add(temp[i]);
		}
		if(files2!=null && files2.size()>0){
			properties.put("files", files2);
		}
	}
	
	/**
	 * 获取图片轮播参数
	 * @param shopUserModuleParamDO
	 * @return List<ShopUserModuleParamDO>
	 * @throws ManagerException
	 */
	@Override
	public List<ShopUserModuleParamDO> queryPrictureRotationEditParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			List<ShopUserModuleParamDO> result = shopUserModuleParamDao
					.queryShopUserModuleParam(shopUserModuleParamDO);
			return result;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("获取图片轮播参数出错", e);
		}
	}

}
