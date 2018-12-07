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

package com.juzicool.search.service;

import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.club.common.safe.JsoupFilter;
import com.jfinal.club.common.model.Feedback;
import com.jfinal.club.common.model.Project;
import com.jfinal.club.common.model.Share;
import java.util.List;

/**
 * 首页业务，主要为了方便做缓存，以及排序逻辑
 */
public class SearchService {

	public static final SearchService me = new SearchService();
	private final String indexCacheName = "index";
	private Project projectDao = new Project().dao();
	private Share shareDao = new Share().dao();
	private Feedback feedbackDao = new Feedback().dao();

	public List<Project> getProjectList() {
		SqlPara sqlPara = projectDao.getSqlPara("index.getProjectList", Project.REPORT_BLOCK_NUM);
		List<Project> projectList =  projectDao.findByCache(indexCacheName, "projectList", sqlPara.getSql(), sqlPara.getPara());

		// 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
		JsoupFilter.filterArticleList(projectList, 50, 120);
		return projectList;
	}

	public List<Share> getShareList() {
		SqlPara sqlPara = shareDao.getSqlPara("index.getShareList", Share.REPORT_BLOCK_NUM);
		List<Share> shareList = shareDao.findByCache(indexCacheName, "shareList", sqlPara.getSql(), sqlPara.getPara());

		// 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
		JsoupFilter.filterArticleList(shareList, 50, 120);
		return shareList;
	}

	public List<Feedback> getFeedbackList() {
		SqlPara sqlPara = feedbackDao.getSqlPara("index.getFeedbackList", Feedback.REPORT_BLOCK_NUM);
		List<Feedback> feedbackList = feedbackDao.findByCache(indexCacheName, "feedbackList", sqlPara.getSql(), sqlPara.getPara());

		// 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
		JsoupFilter.filterArticleList(feedbackList, 50, 120);
		return feedbackList;
	}

	public void clearCache() {
		CacheKit.removeAll(indexCacheName);
	}
}



