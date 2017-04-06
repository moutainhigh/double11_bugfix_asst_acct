package com.yuwang.pinju.core.image.manager.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.image.dao.ImagesCategoryDAO;
import com.yuwang.pinju.core.image.dao.ImagesCategoryDAOOracleDAO;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.filter.PropFilter;
/**
 * 图片分类管理Manager实现类
 * @author 杨昭
 * @since 2011-9-21
 */
public class ImagesCategoryManagerImpl implements ImagesCategoryManager {
	private ImagesCategoryDAO imagesCategoryDAO;
	private ImagesCategoryDAOOracleDAO imagesCategoryDAOOracleDAO;
	private StorageFileInfoManager storageFileInfoManager;
	private MemberManager memberManager;
	public MemberManager getMemberManager() {
		return memberManager;
	}
	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}
	public StorageFileInfoManager getStorageFileInfoManager() {
		return storageFileInfoManager;
	}
	public void setStorageFileInfoManager(StorageFileInfoManager storageFileInfoManager) {
		this.storageFileInfoManager = storageFileInfoManager;
	}
	public ImagesCategoryDAOOracleDAO getImagesCategoryDAOOracleDAO() {
		return imagesCategoryDAOOracleDAO;
	}
	public void setImagesCategoryDAOOracleDAO(ImagesCategoryDAOOracleDAO imagesCategoryDAOOracleDAO) {
		this.imagesCategoryDAOOracleDAO = imagesCategoryDAOOracleDAO;
	}
	public ImagesCategoryDAO getImagesCategoryDAO() {
		return imagesCategoryDAO;
	}
	public void setImagesCategoryDAO(ImagesCategoryDAO imagesCategoryDAO) {
		this.imagesCategoryDAO = imagesCategoryDAO;
	}
	/**
	 * 添加图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO 
	 * @return boolean
	 */
	@Override
	public boolean insertImagesCategory(ImagesCategoryDO imagesCategoryDO)throws ManagerException{
		try {
			ImagesCategoryDO imagesCategoryDOs = repairShopCategoryId(imagesCategoryDO);
			Long id = imagesCategoryDAO.insertImagesCategory(imagesCategoryDOs);
			if(id==null){
				return false;
			}
			return true; 
		} catch (DaoException e) {
			throw new ManagerException("图片分类管理Manager实现类"+e);
		}
	}
	//处理一级分类二级分类
	//一级数据:id@!@名称,id@!@名称1,id@!@名称2
	//二级数据:id@!@名称=470@!@NOKIA,471@!@APPLE,472@!@HTC
	//		   id@!@名称=470@!@NOKIA,471@!@APPLE,472@!@HTC
	public ImagesCategoryDO repairShopCategoryId(ImagesCategoryDO imagesCategoryDO) throws ManagerException{
		try {
			String firstCategoryStr = imagesCategoryDO.getFirstCategory();
			String secondCategoryStr = imagesCategoryDO.getSecondCategory();
			secondCategoryStr = PropFilter.doFilter(secondCategoryStr);
			Properties prop = new Properties();
			prop.load(new StringReader(secondCategoryStr));
			StringBuffer firstsb = new StringBuffer();
			StringBuffer secondsb = new StringBuffer();
			if(firstCategoryStr!=null){
				String[] firstCategorys = firstCategoryStr.split(ShopConstants.SHOP_VALUE_SPLIT);
				for(String firstCategory:firstCategorys){
					if(firstCategory.equals("")) continue;
					String[] id_name = firstCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
					if(id_name[0].equals("0")||id_name[0].equals("null")){
						id_name[0] = imagesCategoryDAOOracleDAO.getImageCategorySeqId()+"";
					}
					firstsb.append(",").append(id_name[0]).append(ShopConstants.SHOP_CATEGORY_SPLIT).append(id_name[1].trim());
					secondsb.append(id_name[0]+ShopConstants.SHOP_CATEGORY_SPLIT+id_name[1].trim()).append("=");
					StringBuffer secondsbtemp = new StringBuffer();
					String temp="";
					if(prop.getProperty(firstCategory)!=null&&!prop.getProperty(firstCategory).equals("")){
						String[] secondCategorys = prop.getProperty(firstCategory).split(ShopConstants.SHOP_VALUE_SPLIT);
						for(String secondCategory:secondCategorys){
							if(secondCategory.equals("")) continue;
							String[] id_name2 = secondCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							if(id_name2[0].equals("0")||id_name2[0].equals("null")){
								id_name2[0] = imagesCategoryDAOOracleDAO.getImageCategorySeqId()+"";
							}
							secondsbtemp.append(",").append(id_name2[0]).append(ShopConstants.SHOP_CATEGORY_SPLIT).append(id_name2[1].trim());
						}
						if(secondsbtemp.toString().length()>0)
							temp = secondsbtemp.substring(1);
					}
					secondsb.append(temp);
					secondsb.append(ShopConstants.SHOP_NEWLINE);
				}
			}
			if(firstsb.toString().length()>0)
				imagesCategoryDO.setFirstCategory(firstsb.toString().substring(1));
			if(secondsb.toString().length()>0)
				imagesCategoryDO.setSecondCategory(secondsb.toString());
			return imagesCategoryDO;
		} catch (DaoException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		} catch (IOException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		}
	}
	//封装一级分类和二级分类
	private List<ImagesCategoryDO> splitFirstCategory(String firstCategory,String secondCategory){
		List<ImagesCategoryDO> list = new ArrayList<ImagesCategoryDO>();
		try {
			String[] firstCategorys = firstCategory.split(",");
			for (String str : firstCategorys) {
				ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
				String[] strs = str.split(ShopConstants.SHOP_CATEGORY_SPLIT);
				//一级分类ID
				imagesCategoryDO.setFirstCategoryId(strs[0]);
				//一级分类名称
				imagesCategoryDO.setFirstCategoryName(strs[1]);
				//二级分类
				imagesCategoryDO.setSecondCategory(secondCategory);
				//通过一级分类"1@!@名称"作为Key读取属性文件方式获取二级分类字符串
				String[] seconds = imagesCategoryDO.getCategoryConfig(str).split(",");
				List<String[]> subList = new ArrayList<String[]>();
				for(String second : seconds){
					String[] subStrs = second.split(ShopConstants.SHOP_CATEGORY_SPLIT);
					if(subStrs.length==2){
						subList.add(subStrs);
					}
				}
				imagesCategoryDO.setSecondCategoryList(subList);
				list.add(imagesCategoryDO);
			}
		} catch (Exception e) {
		}
		return  list;
	}
	/**
	 * 查询图片分类
	 * @author 杨昭
	 * @param memberId	 会员ID
	 * @param memberName  昵称
	 * @param isType 0返回List<ImagesCategoryDO>(只限一级分类和二级分类) 1返回List<ImagesCategoryDO> get(0)获取单个ImagesCategoryDO对象
	 * @return ImagesCategoryDO
	 */
	@Override
	public List<ImagesCategoryDO> getImagesCategory(Long memberId,String memberName,String isType)throws ManagerException {
		List<ImagesCategoryDO> list  = null;
		try {
			ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
			imagesCategoryDO.setMemberId(memberId);
			ImagesCategoryDO img = imagesCategoryDAO.getImagesCategory(imagesCategoryDO);
			if(isType.equals("0")){
				if(img==null){
					ImagesCategoryDO insertImagesCategoryDO = new ImagesCategoryDO();
					insertImagesCategoryDO.setMemberId(memberId);
					insertImagesCategoryDO.setMemberName(memberName);
					Long one = imagesCategoryDAOOracleDAO.getImageCategorySeqId();
				    Long two =imagesCategoryDAOOracleDAO.getImageCategorySeqId();
				    Long three=imagesCategoryDAOOracleDAO.getImageCategorySeqId();
					insertImagesCategoryDO.setFirstCategory(
							one+"@!@"+ImagesCategoryConstant.MOREN_ONE+","
							+two+"@!@"+ImagesCategoryConstant.MOREN_TWO+","
							+three+"@!@"+ImagesCategoryConstant.MOREN_THREE);
					
					StringBuffer sbf = new StringBuffer();
					sbf.append(one+"@!@"+ImagesCategoryConstant.MOREN_ONE+"=\n");
					sbf.append(two+"@!@"+ImagesCategoryConstant.MOREN_TWO+"=\n");
					sbf.append(three+"@!@"+ImagesCategoryConstant.MOREN_THREE+"=\n");
					insertImagesCategoryDO.setSecondCategory(sbf.toString());
					insertImagesCategoryDO.setUserSize(0L);
					//添加默认分类
					Long number = imagesCategoryDAO.insertImagesCategory(insertImagesCategoryDO);
					if(number>0){
						ImagesCategoryDO images = imagesCategoryDAO.getImagesCategory(imagesCategoryDO);
						list = splitFirstCategory(images.getFirstCategory(),images.getSecondCategory());
					}
				}else{
					list = splitFirstCategory(img.getFirstCategory(),img.getSecondCategory());
				}
			}else if(isType.equals("1")){
				list = new ArrayList<ImagesCategoryDO>();
				if(img==null){
					ImagesCategoryDO insertImagesCategoryDO = new ImagesCategoryDO();
					insertImagesCategoryDO.setMemberId(memberId);
					insertImagesCategoryDO.setMemberName(memberName);
					Long one = imagesCategoryDAOOracleDAO.getImageCategorySeqId();
				    Long two =imagesCategoryDAOOracleDAO.getImageCategorySeqId();
				    Long three=imagesCategoryDAOOracleDAO.getImageCategorySeqId();
				    insertImagesCategoryDO.setFirstCategory(
							one+"@!@"+ImagesCategoryConstant.MOREN_ONE+","
							+two+"@!@"+ImagesCategoryConstant.MOREN_TWO+","
							+three+"@!@"+ImagesCategoryConstant.MOREN_THREE);
					
					StringBuffer sbf = new StringBuffer();
					sbf.append(one+"@!@"+ImagesCategoryConstant.MOREN_ONE+"=\n");
					sbf.append(two+"@!@"+ImagesCategoryConstant.MOREN_TWO+"=\n");
					sbf.append(three+"@!@"+ImagesCategoryConstant.MOREN_THREE+"=\n");
					insertImagesCategoryDO.setSecondCategory(sbf.toString());
					insertImagesCategoryDO.setUserSize(0L);
					//添加默认分类
					Long number = imagesCategoryDAO.insertImagesCategory(insertImagesCategoryDO);
					if(number>0){
						list.add(imagesCategoryDAO.getImagesCategory(imagesCategoryDO));
					}
				}else{
					list.add(img);
				}
			}
		} catch (DaoException e) {
			throw new ManagerException("查询图片分类:"+e);
		}
		return list;
	}
	/**
	 *获取Sequences 
	 * @author 杨昭
	 * @return Long
	 */
	@Override
	public Long getImageCategorySeqId() throws ManagerException {
		try {
			return imagesCategoryDAOOracleDAO.getImageCategorySeqId();
		} catch (DaoException e) {
			throw new ManagerException("图片分类获取Sequences:"+e);
		}
	}
	/**
	 * 修改图片分类
	 * @author 杨昭
	 * @param imagesCategoryDO
	 * @return Long
	 */
	@Override
	public Long updateImageCategory(ImagesCategoryDO imagesCategoryDO) throws ManagerException{
		boolean bool = true;
		Long number = null;
		try {
			//修改图片的分类
			List<ImagesCategoryDO> list = repairRemoveShopCategoryId(imagesCategoryDO);
			//需要删除图片的一级分类ID
			List<Long> oneList = new ArrayList<Long>();
			for(int j=0;j<list.size();j++){
				if(list.get(j).getRemoveFirstCategoryId() !=null){
					oneList.add(Long.valueOf(list.get(j).getRemoveFirstCategoryId()));
				}
			}
			//将需要删除的二级分类图片修改到所属的一级分类中
				for(int i=0;i<list.size();i++){
					ImagesCategoryDO updateImages = list.get(i);
					List<Long> subList = new ArrayList<Long>();
					if(updateImages.getSecondCategoryList()!= null){
						for(String[] str : updateImages.getSecondCategoryList())
						{
							subList.add(Long.valueOf(str[0]));
						}
					}
					if(updateImages.getFirstCategoryId()!=null&&subList.size()!=0){
						//将二级分类的图片放到一级分类中
						bool = storageFileInfoManager.updateStorageFileInfo(Long.valueOf(updateImages.getFirstCategoryId()), subList, imagesCategoryDO.getMemberId());
					}
				}
				//处理一级分类
				if(bool!=false){
					if(oneList.size()!=0){
						//删除一级分类中的图片
						bool = storageFileInfoManager.deleteFileByCategoryId(oneList,imagesCategoryDO.getMemberId());
					}
					if(bool!=false){
						//修改图片空间分类信息
						ImagesCategoryDO imagesCategoryDOs = repairShopCategoryId(imagesCategoryDO);
						number = imagesCategoryDAO.updateImageCategory(imagesCategoryDOs);
					}
				}
			
		} catch (Exception e) {
			throw new ManagerException("修改图片分类:"+e);
		}
		return number;
	}
	
	/**
	 * 计算空间大小
	 * @author 杨昭
	 * @param memberId
	 * @param size 计算所需要值  单位(字节)
	 * @param isType 0减1加
	 * @return 
	 */
	@Override
	public Long updateUserSize(Long memberId, Long size, String isType) throws ManagerException {
		try {
			ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
			imagesCategoryDO.setMemberId(memberId);
			imagesCategoryDO.setUserSize(size);
			imagesCategoryDO.setIsType(isType);
			return imagesCategoryDAO.updateUserSize(imagesCategoryDO);
		} catch (DaoException e) {
			throw new ManagerException("加减空间大小:"+e);
		}
	}
	/**
	 * @author 杨昭
	 * @param memberId
	 * @return ImagesCategoryDO
	 */
	@Override
	public ImagesCategoryDO getImagesCategoryObject(Long memberId) throws ManagerException {
		try {
			ImagesCategoryDO imagesCategoryDO = new ImagesCategoryDO();
			imagesCategoryDO.setMemberId(memberId);
			ImagesCategoryDO img =  imagesCategoryDAO.getImagesCategory(imagesCategoryDO);
			if(img==null){
				ImagesCategoryDO insertImagesCategoryDO = new ImagesCategoryDO();
				insertImagesCategoryDO.setMemberId(memberId);
				String memberName  = memberManager.findMember(memberId).getNickname();
				insertImagesCategoryDO.setMemberName(memberName);
				Long one = imagesCategoryDAOOracleDAO.getImageCategorySeqId();
			    Long two =imagesCategoryDAOOracleDAO.getImageCategorySeqId();
			    Long three=imagesCategoryDAOOracleDAO.getImageCategorySeqId();
			    insertImagesCategoryDO.setFirstCategory(
						one+"@!@"+ImagesCategoryConstant.MOREN_ONE+","
						+two+"@!@"+ImagesCategoryConstant.MOREN_TWO+","
						+three+"@!@"+ImagesCategoryConstant.MOREN_THREE);
				StringBuffer sbf = new StringBuffer();
				sbf.append(one+"@!@"+ImagesCategoryConstant.MOREN_ONE+"=\n");
				sbf.append(two+"@!@"+ImagesCategoryConstant.MOREN_TWO+"=\n");
				sbf.append(three+"@!@"+ImagesCategoryConstant.MOREN_THREE+"=\n");
				insertImagesCategoryDO.setSecondCategory(sbf.toString());
				insertImagesCategoryDO.setUserSize(0L);
				//添加默认分类
				Long number = imagesCategoryDAO.insertImagesCategory(insertImagesCategoryDO);
				if(number>0){
					return  imagesCategoryDAO.getImagesCategory(imagesCategoryDO);
				}
			}else if(img!=null){
				return img;
			}
		} catch (Exception e) {
			throw new ManagerException("查询单个分类信息:"+e);
		}
		return null;
	}
	/**
	 * 获取已删除的分类ID，并发送给商品接口
	 * @param newshopCategoryDO
	 * @throws ManagerException
	 */
	public List<ImagesCategoryDO> repairRemoveShopCategoryId(ImagesCategoryDO newImagesCategoryDO) throws ManagerException{
		try {
			List<ImagesCategoryDO> list = new ArrayList<ImagesCategoryDO>();
			ImagesCategoryDO oldImagesCategoryDO = getImagesCategoryObject(newImagesCategoryDO.getMemberId());
			String firstCategoryStr = oldImagesCategoryDO.getFirstCategory();
			String secondCategoryStr = oldImagesCategoryDO.getSecondCategory();
			secondCategoryStr = PropFilter.doFilter(secondCategoryStr);
			Properties prop = new Properties();
			if(secondCategoryStr!=null)
				prop.load(new StringReader(secondCategoryStr));
			if(firstCategoryStr!=null){
				String[] firstCategorys = firstCategoryStr.split(ShopConstants.SHOP_VALUE_SPLIT);
				for(String firstCategory:firstCategorys){
					ImagesCategoryDO updateImage = new ImagesCategoryDO();
					String[] id_name = firstCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
					if(id_name[0].equals("0")||id_name[0].equals("null")){
						id_name[0] = imagesCategoryDAOOracleDAO.getImageCategorySeqId()+"";
					}
					//所属的一级分类
					/*updateImage.setFirstCategoryId(id_name[0]);*/
					if(newImagesCategoryDO.getFirstCategory().indexOf(id_name[0]+ShopConstants.SHOP_CATEGORY_SPLIT)<0){
						//需要移除的一级分类ID
						updateImage.setRemoveFirstCategoryId(id_name[0]);
					}
					if(prop.getProperty(firstCategory)!=null&&!prop.getProperty(firstCategory).equals("")){
						String[] secondCategorys = prop.getProperty(firstCategory).split(ShopConstants.SHOP_VALUE_SPLIT);
						List<String[]> subList = new ArrayList<String[]>();
						for(String secondCategory:secondCategorys){
							String[] id_name2 = secondCategory.split(ShopConstants.SHOP_CATEGORY_SPLIT);
							if(id_name2[0].equals("0")||id_name2[0].equals("null")){
								id_name2[0] = imagesCategoryDAOOracleDAO.getImageCategorySeqId()+"";
							}
							if(newImagesCategoryDO.getSecondCategory().indexOf(id_name2[0]+ShopConstants.SHOP_CATEGORY_SPLIT)<0){
								subList.add(id_name2);
								updateImage.setFirstCategoryId(id_name[0]);
								updateImage.setSecondCategoryList(subList);
							}
						}
					}
					list.add(updateImage);
				}
			}
			return list;
		} catch (DaoException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		} catch (IOException e) {
			throw new ManagerException("更新店铺商品分类错误",e);
		}
	}
}
