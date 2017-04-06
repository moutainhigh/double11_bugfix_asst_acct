package com.yuwang.pinju.core.member.manager.impl;

import com.yuwang.pinju.core.member.manager.SndaManager;
import com.yuwang.pinju.core.member.manager.snda.SndaApi;
import com.yuwang.pinju.domain.member.SndaAccountDO;

public class SndaManagerImpl implements SndaManager {

	@Override
	public SndaAccountDO getSndaAccount(String sndaId) {
		return SndaApi.invokeQueryUserInfo(sndaId);
	}
}
