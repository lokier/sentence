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

package com.jfinal.club.share;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.jfinal.club.common.safe.JsoupFilter;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.model.Share;
import com.jfinal.club.common.model.ShareReply;
import com.jfinal.club.my.newsfeed.NewsFeedService;
import com.jfinal.club.my.newsfeed.ReferMeKit;
import org.joda.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * ShareService
 */
public class ShareService {

	public static final ShareService me = new ShareService();

	private Share dao = new Share().dao();
	private ShareReply shareReplyDao = new ShareReply().dao();

	public Page<Share> paginate(int pageNumber) {
		SqlPara sqlPara = dao.getSqlPara("share.paginate", Share.REPORT_BLOCK_NUM);
		Page<Share> sharePage = dao.paginate(pageNumber, 15, sqlPara);
		// 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
		JsoupFilter.filterArticleList(sharePage.getList(), 50, 120);
		return sharePage;
	}

	public Share findById(int shareId) {
		SqlPara sqlPara = dao.getSqlPara("share.findById", shareId, Share.REPORT_BLOCK_NUM);
		return dao.findFirst(sqlPara);
	}

	public List<Share> getHotShare() {
		// return dao.findByCache("hotShare", "hotShare", "select id, title from share where report < ? order by createAt asc limit 10", Share.REPORT_BLOCK_NUM);

		return CacheKit.get("hotShare", "hotShare", new IDataLoader() {
			public Object load() {
				Date hotPeriod = LocalDateTime.now().minusDays(7).toDate();          // 取最近 7 天的热门，后期内容多的时间可以取最近 3 天
				SqlPara sqlPara = dao.getSqlPara("share.getHotShare", hotPeriod, Share.REPORT_BLOCK_NUM);
				return dao.find(sqlPara);
			}
		});
	}

	public void clearHotShareCache() {
		CacheKit.removeAll("hotShare");
	}

	/**
	 * 保存回复
	 */
	public Ret saveReply(Integer shareId, Integer accountId, String content) {
		ShareReply reply = new ShareReply();
		reply.setShareId(shareId);
		reply.setAccountId(accountId);
		reply.setContent(content);
		reply.setCreateAt(new Date());
		List<Integer> referAccounts = ReferMeKit.buildAtMeLink(reply);
		reply.save();

		// 添加分享回复动态消息
		NewsFeedService.me.createShareReplyNewsFeed(accountId, reply, referAccounts);

		return Ret.ok("reply", reply);
	}

	/**
	 * select sr.*, a.nickName, a.avatar from share_reply sr inner join account a on sr.accountId = a.id where shareId = 13;
	 */
	public Page<ShareReply> getReplyPage(int shareId, int pageNumber) {
		SqlPara sqlPara = dao.getSqlPara("share.getReplyPage", shareId);
		Page<ShareReply> replyPage = shareReplyDao.paginate(pageNumber, 10, sqlPara);
		return replyPage;
	}
}
