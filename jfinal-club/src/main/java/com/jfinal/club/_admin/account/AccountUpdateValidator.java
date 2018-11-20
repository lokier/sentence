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

package com.jfinal.club._admin.account;

import com.jfinal.club.common.kit.SensitiveWordsKit;
import com.jfinal.club.reg.RegValidator;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

/**
 * AccountUpdateValidator 验证账号修改功能表单
 */
public class AccountUpdateValidator extends Validator {
	
	protected void validate(Controller c) {
		setShortCircuit(true);

		/**
		 * 验证 nickName
 		 */
		if (SensitiveWordsKit.checkSensitiveWord(c.getPara("account.nickName")) != null) {
			addError("msg", "昵称不能包含敏感词");
		}
		validateRequired("account.nickName", "msg", "昵称不能为空");
		validateString("account.nickName", 1, 19, "msg", "昵称不能超过19个字");

		String nickName = c.getPara("account.nickName").trim();
		if (nickName.contains("@") || nickName.contains("＠")) { // 全角半角都要判断
			addError("msg", "昵称不能包含 \"@\" 字符");
		}
		if (nickName.contains(" ") || nickName.contains("　")) {
			addError("msg", "昵称不能包含空格");
		}
		Ret ret = RegValidator.validateNickName(nickName);
		if (ret.isFail()) {
			addError("msg", ret.getStr("msg"));
		}

		/**
		 * 验证 userName
		 */
		validateRequired("account.userName", "msg", "邮箱不能为空");
		validateEmail("account.userName", "msg", "邮箱格式不正确");
	}

	protected void handleError(Controller c) {
		c.setAttr("state", "fail");
		c.renderJson();
	}
}

