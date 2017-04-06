package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public interface ShopModulePictureRotationManager {
	
	Object savePrictureRotationEditParam(ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;
	
	List<ShopUserModuleParamDO> queryPrictureRotationEditParam(ShopUserModuleParamDO shopUserModuleParamDO)throws ManagerException;
}
