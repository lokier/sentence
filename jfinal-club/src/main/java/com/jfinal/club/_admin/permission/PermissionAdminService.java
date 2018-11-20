package com.jfinal.club._admin.permission;

import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Permission;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限管理业务
 */
public class PermissionAdminService {

	private Permission dao = new Permission().dao();

	// 用于排除掉 BaseController 中的几个成为了 action 的方法
	private Set<String> excludedMethodName = buildExcludedMethodName();

	/**
	 * TODO 升级到 jfinal 3.5 以后，将 BaseController 中的 @Before(NotAction.class)
	 * 		改成 @NotAction 以后，可以删除本方法
	 */
	private Set<String> buildExcludedMethodName() {
		Set<String> excludedMethodName = new HashSet<String>();
		Method[] methods = BaseController.class.getMethods();
		for (Method m : methods) {
			if (m.getParameterTypes().length == 0)
				excludedMethodName.add(m.getName());
		}
		return excludedMethodName;
	}

	public Page<Permission> paginate(int pageNum) {
		return dao.paginate(pageNum, 10, "select *", "from permission order by actionKey asc");
	}

	/**
	 * 在已被移除的 permission 中 put 进去一个 removed 值为 true 的标记，便于在界面显示不同的样式
	 * @return 存在被删除的 actionKey 时返回 true，否则返回 false
	 */
	public boolean markRemovedActionKey(Page<Permission> permissionPage) {
		boolean ret = false;

		for (Permission p : permissionPage.getList()) {
			String actionKey = p.getActionKey();

			String[] urlPara = new String[1];
			Action action = JFinal.me().getAction(actionKey, urlPara);
			if (action == null || ! actionKey.equals(action.getActionKey())) {
				p.put("removed", true);
				ret = true;
			}
		}

		return ret;
	}

	/**
	 * 替换控制器前缀，界面显示时更加美观
	 *
	 * 例子：
	 * replaceControllerPrefix(permissionPage, "com.jfinal.club._admin.", "...");
	 * 以上例子将 "com.jfinal.club._admin." 这一长串前缀替换成 "..."，显示更美观
	 */
	public void replaceControllerPrefix(Page<Permission> permissionPage, String replaceTarget, String replacement) {
		for (Permission p : permissionPage.getList()) {
			String c = p.getController().replace(replaceTarget, replacement);
			p.setController(c);
		}
	}

	/**
	 * 同步 permission
	 * 获取后台管理所有 actionKey 以及 controller，将数据自动写入 permission 表
	 * 随着开发过程的前行，可以动态进行同步添加新的 permission 数据
	 */
	public Ret sync() {
		int counter = 0;
		List<String> allActionKeys = JFinal.me().getAllActionKeys();
		for (String actionKey : allActionKeys) {
			// 只处理后台管理 action，其它跳过
			if ( !actionKey.startsWith("/admin") ) {
				continue ;
			}

			String[] urlPara = new String[1];
			Action action = JFinal.me().getAction(actionKey, urlPara);
			if (action == null || excludedMethodName.contains(action.getMethodName())) {
				continue ;
			}

			String controller = action.getControllerClass().getName();

			String sql = "select * from permission where actionKey=? and controller = ? limit 1";
			Permission permission = dao.findFirst(sql, actionKey, controller);

			if (permission == null) {
				permission = new Permission();
				permission.setActionKey(actionKey);
				permission.setController(controller);
				setRemarkValue(permission, action);
				permission.save();
				counter++;
			} else {
				// 如果 remark 字段是空值，才去尝试使用 @Remark 注解中的值
				if (StrKit.isBlank(permission.getRemark())) {
					setRemarkValue(permission, action);
					if (permission.update()) {
						counter++;
					}
				}
			}
		}

		if (counter == 0) {
			return Ret.ok("msg", "权限已经是最新状态，无需更新");
		} else {
			return Ret.ok("msg", "权限更新成功，共更新权限数 : " + counter);
		}
	}

	private void setRemarkValue(Permission permission, Action action) {
		Remark remark = action.getMethod().getAnnotation(Remark.class);
		if (remark != null && StrKit.notBlank(remark.value())) {
			permission.setRemark(remark.value());
		}
	}

	public Permission findById(int id) {
		return dao.findById(id);
	}

	public List<Permission> getAllPermissions() {
		return dao.find("select * from permission order by controller asc");
	}

	public Ret update(Permission permission) {
		permission.keep("id", "remark");	// 暂时只允许更新 remark
		permission.update();
		return Ret.ok("msg", "更新成功");
	}

	/**
	 * 如果某个 action 已经删掉，或者改了名称，可以使用该方法删除
	 */
	public Ret delete(final int permissionId) {
		Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				Db.delete("delete from role_permission where permissionId = ?", permissionId);
				dao.deleteById(permissionId);
				return true;
			  }
		});

		return Ret.ok("msg", "权限删除成功");
	}
}