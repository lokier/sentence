package com.jfinal.club._admin.auth;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * 后台管理员授权业务
 */
public class AdminAuthService {

	/**
	 * 是否为超级管理员，role.id 值为 1 的为超级管理员
	 */
	public boolean isSuperAdmin(int accountId) {
		SqlPara sp = Db.getSqlPara("admin.auth.isSuperAdmin", accountId);
		Integer ret = Db.queryInt(sp.getSql(), sp.getPara());
		return ret != null;
	}

	/**
	 * 当前账号是否拥有某些角色
	 */
	public boolean hasRole(int accountId, String[] roleNameArray) {
		if (roleNameArray == null || roleNameArray.length == 0) {
			return false;
		}

		Kv data = Kv.by("accountId", accountId).set("roleNameArray", roleNameArray);
		SqlPara sp = Db.getSqlPara("admin.auth.hasRole", data);
		Integer ret = Db.queryInt(sp.getSql(), sp.getPara());
		return ret != null;
	}

	/**
	 * 是否拥有具体某个权限
	 */
	public boolean hasPermission(int accountId, String actionKey) {
		if (StrKit.isBlank(actionKey)) {
			return false;
		}

		SqlPara sp = Db.getSqlPara("admin.auth.hasPermission", actionKey, accountId);
		Integer ret = Db.queryInt(sp.getSql(), sp.getPara());
		return ret != null;
	}
}

