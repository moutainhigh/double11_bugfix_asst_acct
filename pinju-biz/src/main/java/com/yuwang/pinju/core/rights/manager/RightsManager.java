package com.yuwang.pinju.core.rights.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

public interface RightsManager {
	
	/**
	 * Created on 2011-10-18 
	 * @desc <p>Discription:[获取买家或卖家维权记录数]</p>
	 * @param 
	 * @return int
	 * @author:[石兴]
	 * @update:[2011-10-18] [更改人姓名]
	 */
	public int getRightsCount(RightsQuery rightsQuery) throws ManagerException;
	
	/**
     * Created on 2011-06-29
     * <p>Discription:[插入维权记录]</p>
     * @param RightsDO
     * @return
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
	void insertRightsRecord(RightsDO rightsDO) throws ManagerException;

	/**
	 * Created on 2011-06-29
	 * <p>Discription:[查询维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsDO getApplyRightsDO(RightsDO rightsDO) throws ManagerException;
	
	/**
	 * Created on 2011-7-1 
	 * <p>Discription:[获取维权记录列表]</p>
	 * @param RightsDO
	 * @return List<RightsDO>
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<RightsDO> getApplyRightsDOs(RightsQuery rightsQuery) throws ManagerException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[更新维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void updateRightsRecord(RightsDO rightsDO) throws ManagerException;
	
	/**
	 * Created on 2011-06-30
	 * <p>Discription:[插入维权留言记录]</p>
	 * @param RightsMessageDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertMessageRecord(RightsMessageDO rightsMessageDO) throws ManagerException;
	
	/**
	 * Created on 2011-06-30
	 * <p>Discription:[插入维权物流记录]</p>
	 * @param RightsLogisticsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertLogisticsRecord(RightsLogisticsDO rightsLogisticsDO) throws ManagerException;
	
	/**
	 * Created on 2011-06-30
	 * <p>Discription:[查询维权留言记录]</p>
	 * @param voucherId 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<RightsMessageDO> getRightsMessageDOs(RightsMsgQuery rightsMsgQuery) throws ManagerException;
	
	/**
	 * Created on 2011-06-30
	 * <p>Discription:[查询维权物流记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsLogisticsDO getRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO) throws ManagerException;
	
	/**
	 * Created on 2011-06-30
	 * <p>Discription:[更新维权物流记录]</p>
	 * @param long voucherId
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateRightsLogisticsRecord(RightsLogisticsDO rightsLogisticsDO) throws ManagerException;
	
	/**
	 * Create on 2011-7-27
	 * <p>@Discription:[通过id查询RightsDO对象]</p>
	 * @param: Long id
	 * @return: RightsDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsDO getRightsDOById(Long id) throws ManagerException;
}
