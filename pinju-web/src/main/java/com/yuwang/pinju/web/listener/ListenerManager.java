package com.yuwang.pinju.web.listener;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.listener.login.AfterLoginEvent;
import com.yuwang.pinju.web.listener.login.AfterLoginListener;
import com.yuwang.pinju.web.listener.login.impl.AsstLoginListener;
import com.yuwang.pinju.web.listener.login.impl.LogLoginListener;

public class ListenerManager {

	private final static Log log = LogFactory.getLog(ListenerManager.class);
	private List<AfterLoginListener> afterLoginListeners;

	private static ListenerManager instance = new ListenerManager();

	private ListenerManager() {
		initAfterLoginListeners();
	}

	public static ListenerManager getInstance() {
		return instance;
	}

	public void fireAfterLoginListener(AfterLoginEvent event) {
		if (EmptyUtil.isBlank(afterLoginListeners)) {
			log.debug("fireAfterLoginListener, afterLoginListeners is empty, need not fire the listener");
			return;
		}
		if (event == null) {
			log.error("fireAfterLoginListener, parameter event is null, can not fire the listener");
			return;
		}
		if (event.getMember() == null || event.getMemberAO() == null) {
			log.error("fireAfterLoginListener, member and member ao in parameter event has null object, member: "
					+ event.getMember() + ", member ao: " + event.getMemberAO());
		}
		for (AfterLoginListener listener : afterLoginListeners) {
			try {
				boolean result = listener.afterLogin(event);
				if (log.isDebugEnabled()) {
					log.debug("fireAfterLoginListener [" + listener.getClass().getSimpleName() + "] execute result: "
							+ result);
				}
			} catch (Throwable e) {
				log.error("fireAfterLoginListener [" + listener.getClass().getSimpleName()
						+ "] cause exception, member: " + event.getMember(), e);
			}
		}
	}

	private void initAfterLoginListeners() {
		afterLoginListeners = new LinkedList<AfterLoginListener>();
		afterLoginListeners.add(new LogLoginListener());
		afterLoginListeners.add(new AsstLoginListener());
		// 盛大通行证登录已经取消
		// afterLoginListeners.add(new SndaAccountListener());
	}
}
