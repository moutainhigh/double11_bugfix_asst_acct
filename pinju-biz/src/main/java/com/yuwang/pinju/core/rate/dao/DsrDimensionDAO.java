package com.yuwang.pinju.core.rate.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;

/**
 * <p>DSR 维度数据库操作（RATE_DSR_DIMENSION）</p>
 * @author gaobaowen
 * 2011-6-15 下午01:14:34
 */
public interface DsrDimensionDAO {

	List<DsrDimensionDO> getValidDsrDimensions() throws DaoException;
}
