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

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.kit.SensitiveWordsKit;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.common.model.Share;
import com.jfinal.club.common.pageview.PageViewInterceptor;
import com.jfinal.club.common.safe.RestTime;
import com.jfinal.club.my.favorite.FavoriteService;
import com.jfinal.club.my.like.LikeService;
import com.jfinal.club.my.share.MyShareService;
import com.jfinal.club.project.ProjectService;

/**
 * 分享控制器
 */
@Before(ShareSeo.class)
public class ShareController extends BaseController {

	ShareService srv = ShareService.me;

	/**
	 * 首页
	 */
	public void index() {
		setAttr("sharePage", srv.paginate(getParaToInt("p", 1)));
		setAttr("hotShare", srv.getHotShare());
		render("index.html");
	}

	/**
	 * 详情页
	 */
	@Before(PageViewInterceptor.class)
	public void detail() {
		Share share = srv.findById(getParaToInt());
		if (share != null) {
			setAttr("share", share);

			setAttr("project", ProjectService.me.findById(share.getProjectId(), "id, name"));
			setAttr("replyPage", srv.getReplyPage(share.getId(), getParaToInt("p", 1)));
			setAttr("hotShare", srv.getHotShare());
			render("detail.html");

            setLikeAndFavoriteStatus(share);
		} else {
			renderError(404);
		}
	}

    /**
     * 如果用户已登录，则需要显示当前 article 是否已经被该用户点赞、收藏了
     */
    private void setLikeAndFavoriteStatus(Share share) {
        Ret ret = Ret.create();
        LikeService.me.setLikeStatus(getLoginAccount(), "share", share, ret);
        FavoriteService.me.setFavoriteStatus(getLoginAccount(), "share", share, ret);
        setAttr("ret", ret);
    }

	/**
	 * 回复
	 */
	public void saveReply() {
		if (notLogin()) {
			renderJson(Ret.fail("msg", "登录后才可以评论"));
			return ;
		}
		String restTimeMsg = RestTime.checkRestTime(getLoginAccount());
		if (restTimeMsg != null) {
			renderJson(Ret.fail("msg", restTimeMsg));
			return ;
		}
		String replyContent = getPara("replyContent");
		if (StrKit.isBlank(replyContent)) {
			renderJson(Ret.fail("msg", "回复内容不能为空"));
			return ;
		}
		if (SensitiveWordsKit.checkSensitiveWord(replyContent) != null) {
			renderJson(Ret.fail("msg", "回复内容不能包含敏感词"));
			return ;
		}

		Ret ret = srv.saveReply(getParaToInt("articleId"), getLoginAccountId(), replyContent);

		// 注入 nickName 与 avatar 便于 renderToString 生成 replyItem html 片段
		Account loginAccount = getLoginAccount();
		ret.set("loginAccount", loginAccount);
		// 用模板引擎生成 HTML 片段 replyItem
		String replyItem = renderToString("_reply_item.html", ret);
		
		ret.set("replyItem", replyItem);
		renderJson(ret);
	}

	/**
	 * 删除回复
	 */
	public void deleteReply() {
		if (isLogin()) {
			int accountId = getLoginAccountId();
			int replyId = getParaToInt("id");
			MyShareService.me.deleteShareReplyById(accountId, replyId);
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail("msg", "未登录用户不会显示删除链接，请勿手工制造请求"));
		}
	}
}
