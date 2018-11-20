/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.jfinal.club._admin.auth;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.login.LoginService;
import com.jfinal.kit.Ret;

/**
 * 后台管理员授权拦截器
 */
public class AdminAuthInterceptor implements Interceptor {

	@Inject
	AdminAuthService srv;
	
	/**
	 * 用于 sharedObject、sharedMethod 扩展中使用
	 */
	private static final ThreadLocal<Account> threadLocal = new ThreadLocal<Account>();
	
	public static Account getThreadLocalAccount() {
		return threadLocal.get();
	}

	public void intercept(Invocation inv) {
		Account loginAccount = inv.getController().getAttr(LoginService.loginAccountCacheName);
		if (loginAccount != null && loginAccount.isStatusOk()) {
			// 传递给 sharedObject、sharedMethod 扩展使用
			threadLocal.set(loginAccount);
			
			// 如果是超级管理员或者拥有对当前 action 的访问权限则放行
			if (	srv.isSuperAdmin(loginAccount.getId()) ||
					srv.hasPermission(loginAccount.getId(), inv.getActionKey())) {
				inv.invoke();
				return ;
			}
		}

		// renderError(404) 避免暴露后台管理 url，增加安全性
		if (loginAccount == null || inv.getActionKey().equals("/admin")) {
			inv.getController().renderError(404);
		}
		// renderJson 提示没有操作权限，提升用户体验
		else {
			inv.getController().renderJson(Ret.fail("msg", "没有操作权限"));
		}
	}
}

