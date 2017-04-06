package com.yuwang.pinju.core.rights.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

/**  
* @Project: pinju-biz
* @Description: TODO
* @author 石兴 shixing@zba.com
* @date 2011-6-29 上午09:58:42
* @update 2011-6-29 上午09:58:42
* @version V1.0  
*/
public interface RightsDAO {

    /**
     * Created on 2011-06-29
     * <p>Discription:[插入维权记录]</p>
     * @param RightsDO
     * @return
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
	void insertRightsRecord(RightsDO rightsDO) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[插入维权留言记录]</p>
	 * @param RightsMessageDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertMessageRecord(RightsMessageDO rightsMessageDO) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[插入维权物流记录]</p>
	 * @param RightsLogisticsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertLogisticsRecord(RightsLogisticsDO rightsLogisticsDO) throws DaoException;

	/**
	 * Created on 2011-06-29
	 * <p>Discription:[查询维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsDO getApplyRightsDO(RightsDO rightsDO) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[查询维权记录列表]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<RightsDO> getRightsDOs(RightsQuery rightsQuery) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[查询维权留言记录]</p>
	 * @param voucherId 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<RightsMessageDO> getRightsMessageDOs(RightsMsgQuery rightsMsgQuery) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[查询维权物流记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsLogisticsDO getRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[更新维权记录]</p>
	 * @param RightsDO
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateRightsRecord(RightsDO rightsDO) throws DaoException;
	
	/**
	 * Created on 2011-06-29
	 * <p>Discription:[更新维权物流记录]</p>
	 * @param long voucherId
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateRightsLogisticsRecord(RightsLogisticsDO rightsLogisticsDO) throws DaoException;
	
	/**
	 * Create on 2011-7-27
	 * <p>@Discription:[通过id查询RgihtsDO对象]</p>
	 * @param: Long id
	 * @return: RightsDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	RightsDO getRightsDOById(Long id) throws DaoException;

	/**
	 * Created on 2011-10-18 
	 * @desc <p>Discription:[获取买家或卖家维权记录数]</p>
	 * @param 
	 * @return int
	 * @author:[石兴]
	 * @update:[2011-10-18] [更改人姓名]
	 */
	int getRightsCount(RightsQuery rightsQuery) throws DaoException;
}
