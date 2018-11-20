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

package com.jfinal.club.common.controller;

import com.jfinal.core.Controller;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.login.LoginService;
import com.jfinal.core.NotAction;

/**
 * 基础控制器，方便获取登录信息
 * 
 * 警告：由于 BaseController 中声明了 Account loginAccount 属性，
 *      所以不能使用 FastControllerFactory 这类回收 Controller 的用法
 *      在 jfinal 3.5 发布以后，可以通过继承 _clear_() 方法来清除属性中的值
 *      才能使用 FastControllerFactory
 *      用户自己的 Controller 也是同样的道理
 *
 * 注意：
 * 需要 LoginSessionInterceptor 配合，该拦截器使用
 * setAttr("loginAccount", ...) 事先注入了登录账户
 * 否则即便已经登录，该控制器也会认为没有登录
 *
 */
public class BaseController extends Controller {

	/**
	 * 警告：由于这个属性的存在，不能直接使用 FastControllerFactory，除非使用 jfinal 3.5
	 *      并覆盖父类中的 _clear_() 方法清除本类中与父类中的属性值，详情见本类中的
	 *      protected void _clear_() 方法
	 *
	 *      原因是 FastControllerFactory 是回收使用 controller 对象的，所以要在 _clear()_
	 *      中清除上次使用时的属性值
	 */
	private Account loginAccount = null;

	protected void _clear_() {
		this.loginAccount = null;
		super._clear_();
	}

	@NotAction
	public Account getLoginAccount() {
		if (loginAccount == null) {
			loginAccount = getAttr(LoginService.loginAccountCacheName);
			if (loginAccount != null && ! loginAccount.isStatusOk()) {
				throw new IllegalStateException("当前用户状态不允许登录，status = " + loginAccount.getStatus());
			}
		}
		return loginAccount;
	}

	@NotAction
	public boolean isLogin() {
		return getLoginAccount() != null;
	}

	@NotAction
	public boolean notLogin() {
		return !isLogin();
	}

	/**
	 * 获取登录账户id
	 * 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
	 * 也即确定已经是在登录后才可调用
	 */
	@NotAction
	public int getLoginAccountId() {
		return getLoginAccount().getId();
	}

	@NotAction
	public boolean isPjaxRequest() {
		return "true".equalsIgnoreCase(getHeader("X-PJAX"));
	}

	@NotAction
	public boolean isAjaxRequest() {
		return "XMLHttpRequest".equalsIgnoreCase(getHeader("X-Requested-With"));
	}
}


