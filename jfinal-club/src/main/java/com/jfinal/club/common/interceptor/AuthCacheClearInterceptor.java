package com.jfinal.club.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.login.LoginService;
import com.jfinal.plugin.activerecord.Db;

/**
 * 在 role 表中存在的 accountId 拥有清除前端 cache 的权限
 * 超级管理员符合上述条件
 */
public class AuthCacheClearInterceptor implements Interceptor {

	public static boolean isAdmin(Account loginAccount) {
		if (loginAccount == null || !loginAccount.isStatusOk()) {
			return false;
		}

		String admin = "select accountId from account_role where accountId = ? limit 1";
		Integer accountId = Db.queryInt(admin, loginAccount.getId());
		return accountId != null;
	}

	public void intercept(Invocation inv) {
		Account loginAccount = inv.getController().getAttr(LoginService.loginAccountCacheName);
		if (isAdmin(loginAccount)) {
			inv.invoke();
		} else {
			inv.getController().renderError(404);
		}
	}
}