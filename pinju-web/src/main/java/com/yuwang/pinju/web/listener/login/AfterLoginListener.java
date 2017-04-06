package com.yuwang.pinju.web.listener.login;

import java.util.EventListener;

/**
 * <p>登录后监听器</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 上午09:16:38
 */
public interface AfterLoginListener extends EventListener {

	boolean afterLogin(AfterLoginEvent event);
}
