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

package com.jfinal.club._admin.feedback;

import com.jfinal.club.common.model.Feedback;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.club.common.model.FeedbackReply;
import com.jfinal.club.my.feedback.MyFeedbackService;
import com.jfinal.plugin.activerecord.Page;
import java.util.Date;
import java.util.List;

/**
 * feedback 管理业务
 */
public class FeedbackAdminService {

	private Feedback dao = new Feedback().dao();
	private FeedbackReply feedbackReplyDao = new FeedbackReply().dao();

	/**
	 * feedback 分页
	 */
	public Page<Feedback> paginate(int pageNum) {
		return dao.paginate(pageNum, 10, "select *", "from feedback order by id desc");
	}

	/**
	 * 创建反馈
	 */
	public Ret save(int accountId, Feedback feedback) {
		feedback.setAccountId(accountId);
		feedback.setTitle(feedback.getTitle().trim());
		feedback.setCreateAt(new Date());
		feedback.save();
		return Ret.ok("msg", "创建成功");
	}

	public Feedback edit(int id) {
		return dao.findById(id);
	}

	public Ret update(Feedback feedback) {
		feedback.update();
		return Ret.ok("msg", "修改成功");
	}

	public Ret lock(int id) {
		Db.update("update feedback set report = report + ? where id=?", Feedback.REPORT_BLOCK_NUM, id);
		return Ret.ok("msg", "锁定成功");
	}

	public Ret unlock(int id) {
		Db.update("update feedback set report = 0 where id=?", id);
		return Ret.ok("msg", "解除锁定成功");
	}

	/**
	 * 删除 feedback
	 */
	public Ret delete(int feedbackId) {
		Integer accountId = Db.queryInt("select accountId from feedback where id=? limit 1", feedbackId);
		if (accountId != null) {
			MyFeedbackService.me.delete(accountId, feedbackId);
			return Ret.ok("msg", "feedback 删除成功");
		} else {
			return Ret.fail("msg", "feedback 删除失败");
		}
	}

	public Feedback getById(int feedbackId) {
		return dao.findById(feedbackId);
	}

	/**
	 * 获取 reply list
	 */
	public List<FeedbackReply> getReplyList(int feedbackId) {
		String sql = "select id, accountId, createAt, substring(content, 1, 30) as content from feedback_reply where feedbackId=? order by id desc";
		return feedbackReplyDao.find(sql, feedbackId);
	}

	public Ret getReply(int replyId) {
		FeedbackReply reply = feedbackReplyDao.findById(replyId);
		return Ret.ok("reply", reply);
	}

	/**
	 * 删除 feedback reply
	 */
	public Ret deleteReply(int feedbackReplyId) {
		Integer accountId = Db.queryInt("select accountId from feedback_reply where id=? limit 1", feedbackReplyId);
		if (accountId != null) {
			MyFeedbackService.me.deleteFeedbackReplyById(accountId, feedbackReplyId);
			return Ret.ok("msg", "feedback reply 删除成功");
		} else {
			return Ret.fail("msg", "feedback reply 删除失败");
		}
	}
}
