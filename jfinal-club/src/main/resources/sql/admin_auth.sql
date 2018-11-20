### 验证是否为超级管理员, 超级管理员的 roleId 值为固定为 1
#sql("isSuperAdmin")
	select accountId from account_role
	where accountId = #para(0) and roleId = 1
#end


### 验证是否拥有某个 role
#sql("hasRole")
	select ar.accountId from account_role ar
	inner join role r on ar.roleId = r.id
	where ar.accountId = #para(accountId)
	and (
		#for (x : roleNameArray)
			#(for.first ? "" : "or") r.name = #para(x.trim())
		#end
	)
#end


### 验证是否拥有某个 permission
#sql("hasPermission")
	select ar.accountId from (
		select rp.roleId from role_permission rp
		inner join permission p on rp.permissionId = p.id
		where p.actionKey = #para(0)
	)
	as t inner join account_role ar on t.roleId = ar.roleId
	where ar.accountId = #para(1)
#end

