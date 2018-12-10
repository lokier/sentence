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

package com.juzicool.search.controller;
import java.util.ArrayList;
import java.util.List;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.interceptor.AuthCacheClearInterceptor;
import com.jfinal.plugin.activerecord.Page;
import com.juzicool.search.Juzi;
import com.juzicool.search.plugin.SearchService;


/**
 * 首页控制器
 */
public class SearchController extends BaseController {

	SearchService srv = SearchService.me;

	public void index() {

		render("index.html");
	}
	
	/***
	 * 分页查找从1开始找。
	 */
	public void search() {
		//setAttr("q", value);
		String query = super.getPara("q", "");
		int currentPage = super.getParaToInt("page", 1); //从1开始
		int pageSize = super.getParaToInt("size", 0);
		
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		if(pageSize >20) {
			pageSize = 20;
		}
		if(pageSize < 1) {
			pageSize = 10;
		}
		if(query.isEmpty()) {
			 render("index.html");
			 return;
		}
		
		Page<Juzi> pageResult = srv.query(query,currentPage,pageSize);
		
		if(pageResult == null) {
			this.renderError(404);
			return;
		}
		
		int totalPage = pageResult.getTotalPage() > 100 ?100:pageResult.getTotalPage();
	
		this.setAttr("page", pageResult);
		this.setAttr("currentPage", currentPage);
		this.setAttr("totalPage", totalPage);
		this.setAttr("linkPage", "./search?q="+query+"&size="+pageSize);
		this.setAttr("q", query);

		render("serach_list.html");
	}
	
	public void coopration() {
		this.render("coopration.html");
	}

	@Before(AuthCacheClearInterceptor.class)
	public void clear() {
		//srv.clearCache();
		redirect("/");
	}
}
