package com.yuwang.pinju.core.member.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.member.dao.MemberLoginPageImgDAO;
import com.yuwang.pinju.core.member.manager.LoginPageManager;
import com.yuwang.pinju.domain.member.login.LoginPageImgVO;
import com.yuwang.pinju.domain.member.login.MemberLoginPageImgDO;

public class LoginPageManagerImpl implements LoginPageManager {

	private final static String LOGIN_PAGE_IMG_KEY = "imgs";
	private final static Log log = LogFactory.getLog(LoginPageManagerImpl.class);

	private MemberLoginPageImgDAO memberLoginPageImgDAO;
	private MemcachedManager loginPageMemcachedManager;

	public void setMemberLoginPageImgDAO(MemberLoginPageImgDAO memberLoginPageImgDAO) {
		this.memberLoginPageImgDAO = memberLoginPageImgDAO;
	}

	public void setLoginPageMemcachedManager(MemcachedManager loginPageMemcachedManager) {
		this.loginPageMemcachedManager = loginPageMemcachedManager;
	}

	@Override
	public LoginPageImgVO getLoginPageImg(boolean disabledCache) {
		LoginPageImgVO imgs = null;
		if (!disabledCache) {
			imgs = loginPageMemcachedManager.getCacheObject(LOGIN_PAGE_IMG_KEY, LoginPageImgVO.class);
		}
		if (imgs == null) {
			log.warn("[getLoginPageImg] login page images was not found, get images from database, disable cache: [" + disabledCache + "]");
			imgs = readLoginPage();
		} else if (log.isDebugEnabled()) {
			log.debug("[getLoginPageImg] login page images was found in cache, disable cache: [" + disabledCache + "]");
		}
		return imgs;
	}

	private LoginPageImgVO readLoginPage() {
		try {
			// 先从主库中读取数据，从主库中读取时会将数据存入缓存
			return successLoginImage( memberLoginPageImgDAO.getValidPageImgs() );
		} catch (Exception e) {
			log.error("[getLoginPageImg] from write database cause exception, attempt select it from read database", e);
			try {
				// 主库读取失败时从备库中读取，从备库中读取时会将数据存入缓存
				return successLoginImage( memberLoginPageImgDAO.getValidPageImgsFromRead() );
			} catch (Exception e1) {
				log.error("[getLoginPageImg] from read database cause exception, using DEFAULT IMAGES", e);
				// 备库读取失败时将使用默认数据，使用默认数据将不走缓存
				return LoginPageImgVO.DEFAULT_LOGIN_PAGE_IMG;
			}
		}		
	}

	private LoginPageImgVO successLoginImage(List<MemberLoginPageImgDO> img) {
		LoginPageImgVO loginImg = new LoginPageImgVO(img);
		loginPageMemcachedManager.setCacheObject(LOGIN_PAGE_IMG_KEY, loginImg);
		return loginImg;
	}
}
