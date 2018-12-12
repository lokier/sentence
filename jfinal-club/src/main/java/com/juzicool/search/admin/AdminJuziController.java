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

package com.juzicool.search.admin;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.jfinal.aop.Inject;
import com.jfinal.club._admin.index.IndexAdminService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Account;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.juzicool.search.admin.task.ImportJuziByFileTask;

/**
 * 后台管理首页
 */
public class AdminJuziController extends BaseController {

	@Inject
	JuziAdminService srv;

	public void index() {
		Account account = super.getLoginAccount();
		ImportJuziByFileTask task = ImportJuziByFileTask.get(account.getId());
		
		boolean doImport = getParaToBoolean("doImport", false);

			if(doImport &&! task.isRunning()) {
				task.start();
			}
			
			setAttr("accountId", account.getId());
			setAttr("task", task);
			render("index.html");
	
		
	}
	
	public void upload() {
		UploadFile uf = null;
		Account account = super.getLoginAccount();

		int accountId = account.getId();
		ImportJuziByFileTask task = ImportJuziByFileTask.get(accountId);
		
		if(task.isRunning()) {
			//renderJson(Ret.fail("msg", "已经上传新的文件"));
			return;
		}

		try {
			uf = getFile("excelFile", "/importJuzi/temp/", 10 * 1024 * 1024);
			if (uf == null) {
				renderJson(Ret.fail("msg", "请先选择上传文件"));
				return;
			}
		} catch (Exception e) {
			if (e instanceof com.jfinal.upload.ExceededSizeException) {
				renderJson(Ret.fail("msg", "文件大小超出范围10M"));
			} else {
				if (uf != null) {
					// 只有出现异常时才能删除，不能在 finally 中删，因为后面需要用到上传文件
					uf.getFile().delete();
				}
				renderJson(Ret.fail("msg", e.getMessage()));
			}
			return ;
		}finally {
			if(uf != null) {
				try {
					FileUtils.copyFile(uf.getFile(), task.getFile());
					uf.getFile().delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		redirect("/admin/juzi/");
	}
	
/*	public void batchImport() {
		int accountId = getParaToInt("accountId");
		ImportJuziByFileTask task = ImportJuziByFileTask.request(accountId);
		
		if(!task.isRunning()) {
			task.start();
		}
		
		try {
			setAttr("accountId", accountId);
			setAttr("task", task);
			render("index.html");
		}finally {
			ImportJuziByFileTask.release(task);
		}
		
	}*/
	
}
