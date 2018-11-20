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

package com.jfinal.club.feedback;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.club.common.safe.JsoupFilter;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.model.Feedback;
import com.jfinal.club.common.model.FeedbackReply;
import com.jfinal.club.my.newsfeed.NewsFeedService;
import com.jfinal.club.my.newsfeed.ReferMeKit;
import org.joda.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * FeedbackService
 */
public class FeedbackService {

	public static final FeedbackService me = new FeedbackService();

	private Feedback dao = new Feedback().dao();
	private FeedbackReply feedbackReplyDao = new FeedbackReply().dao();

	public Page<Feedback> paginate(int pageNumber) {
		SqlPara sqlPara = dao.getSqlPara("feedback.paginate", Feedback.REPORT_BLOCK_NUM);
		Page<Feedback> feedbackPage = dao.paginate(pageNumber, 15, sqlPara);
		// 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
		JsoupFilter.filterArticleList(feedbackPage.getList(), 50, 120);
		return feedbackPage;
	}

	public Feedback findById(int feedbackId) {
		SqlPara sqlPara = dao.getSqlPara("feedback.findById", feedbackId, Feedback.REPORT_BLOCK_NUM);
		return dao.findFirst(sqlPara);
	}

	public List<Feedback> getHotFeedback() {
		// return dao.findByCache("hotFeedback", "hotFeedback", "select id, title from feedback where report < ?  order by createAt asc limit 10", Feedback.REPORT_BLOCK_NUM);

		return CacheKit.get("hotFeedback", "hotFeedback", new IDataLoader() {
			public Object load() {
				Date hotPeriod = LocalDateTime.now().minusDays(7).toDate();          // 取最近 7 天的热门，后期内容多的时间可以取最近 3 天
				SqlPara sqlPara = dao.getSqlPara("feedback.getHotFeedback", hotPeriod, Feedback.REPORT_BLOCK_NUM);
				return dao.find(sqlPara);
			}
		});
	}

	public void clearHotFeedbackCache() {
		CacheKit.removeAll("hotFeedback");
	}

	/**
	 * 保存回复
	 */
	public Ret saveReply(Integer feedbackId, Integer accountId, String content) {
		FeedbackReply reply = new FeedbackReply();
		reply.setFeedbackId(feedbackId);
		reply.setAccountId(accountId);
		reply.setContent(content);
		reply.setCreateAt(new Date());
		List<Integer> referAccounts = ReferMeKit.buildAtMeLink(reply);
		reply.save();

		// 添加反馈回复动态消息
		NewsFeedService.me.createFeedbackReplyNewsFeed(accountId, reply, referAccounts);

		return Ret.ok("reply", reply);
	}

	/**
	 * select fr.*, a.nickName, a.avatar from feedback_reply fr inner join account a on fr.accountId = a.id where feedbackId = 13;
	 */
	public Page<FeedbackReply> getReplyPage(int feedbackId, int pageNumber) {
		SqlPara sqlPara = dao.getSqlPara("feedback.getReplyPage", feedbackId);
		Page<FeedbackReply> replyPage = feedbackReplyDao.paginate(pageNumber, 10, sqlPara);
		return replyPage;
	}
}
