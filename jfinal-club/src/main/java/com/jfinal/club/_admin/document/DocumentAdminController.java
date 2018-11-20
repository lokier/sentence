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

package com.jfinal.club._admin.document;

import com.jfinal.aop.Inject;
import com.jfinal.club._admin.permission.Remark;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.model.Document;
import java.util.List;

/**
 * 文档管理控制器
 * 暂不支持主菜单 doc 的显示，主菜单 doc 现在仅用于我自己来 todolist 和大纲
 */
public class DocumentAdminController extends BaseController {

	@Inject
	DocumentAdminService srv;

	@Remark("文档管理首页")
	public void index() {
		List<Document> docList = srv.getDocList();
		setAttr("docList", docList);
		render("index.html");
	}

	@Remark("创建文档")
	public void add() {
		List<Document> docList = srv.getDocList();
		setAttr("docList", docList);
		render("add_edit.html");
	}

	@Remark("创建文档提交")
	public void save() {
		Document doc = getBean(Document.class, "doc");
		Ret ret = srv.save(doc);
		renderJson(ret);
	}

	@Remark("修改文档")
	public void edit() {
		Document doc = srv.getById(getParaToInt("mainMenu"), getParaToInt("subMenu"));
		if (doc == null) {
			renderError(404);
		}
		setAttr("doc", doc);
		render("add_edit.html");
	}

	@Remark("修改文档提交")
	public void update() {
		Document doc = getBean(Document.class, "doc");
		Ret ret = srv.update(getParaToInt("oldMainMenu"), getParaToInt("oldSubMenu"), doc);
		renderJson(ret);
	}

	@Remark("删除文档")
	public void delete() {
		Ret ret = srv.delete(getParaToInt("mainMenu"), getParaToInt("subMenu"));
		renderJson(ret);
	}

	/**
	 * 发布
	 */
	@Remark("发布文档")
	public void publish() {
		Ret ret = srv.publish(getParaToInt("mainMenu"), getParaToInt("subMenu"));
		renderJson(ret);
	}

	/**
	 * 取消发布，成为草稿
	 */
	@Remark("取消发布文档")
	public void unpublish() {
		Ret ret = srv.unpublish(getParaToInt("mainMenu"), getParaToInt("subMenu"));
		renderJson(ret);
	}
}




