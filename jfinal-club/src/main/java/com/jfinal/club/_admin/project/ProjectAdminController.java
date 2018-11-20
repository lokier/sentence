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

package com.jfinal.club._admin.project;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.club.my.project.MyProjectValidator;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.model.Project;
import com.jfinal.club.index.IndexService;
import com.jfinal.club.project.ProjectService;

/**
 * 项目管理控制器
 */
public class ProjectAdminController extends BaseController {

	@Inject
	ProjectAdminService srv;

	public void index() {
		Page<Project> projectPage = srv.paginate(getParaToInt("p", 1));
		setAttr("projectPage", projectPage);
		render("index.html");
	}

	/**
	 * 创建
	 */
	public void add() {
		render("add_edit.html");
	}

	/**
	 * 提交创建
	 */
	@Before(MyProjectValidator.class)
	public void save() {
		Project project = getBean(Project.class);
		Ret ret = srv.save(getLoginAccountId(), project);
		renderJson(ret);
	}

	/**
	 * 修改
	 */
	public void edit() {
		keepPara("p");	// 保持住分页的页号，便于在 ajax 提交后跳转到当前数据所在的页
		setAttr("project", srv.edit(getParaToInt("id")));
		render("add_edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(MyProjectValidator.class)
	public void update() {
		Project project = getBean(Project.class);
		Ret ret = srv.update(project);
		renderJson(ret);
	}

	/**
	 * 锁定
	 */
	public void lock() {
		Ret ret = srv.lock(getParaToInt("id"));

		ProjectService.me.clearHotProjectCache();
		IndexService.me.clearCache();
		renderJson(ret);
	}

	/**
	 * 解除锁定
	 */
	public void unlock() {
		Ret ret = srv.unlock(getParaToInt("id"));

		ProjectService.me.clearHotProjectCache();
		IndexService.me.clearCache();
		renderJson(ret);
	}

	/**
	 * 删除 project
	 */
	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}
}


