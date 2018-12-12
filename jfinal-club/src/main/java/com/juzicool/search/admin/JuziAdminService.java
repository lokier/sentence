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

import com.jfinal.plugin.activerecord.Db;
import com.juzicool.search.JuziObject;
import com.juzicool.search.plugin.SearchService;
import com.juzicool.search.util.JuziUtils;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.club.common.model.Document;
import com.jfinal.club.common.model.Juzi;
import com.jfinal.club.document.DocumentService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * document 管理业务
 */
public class JuziAdminService  {


	private Juzi dao = new Juzi().dao();
	//private ElasticSearch es =  ElasticSearch.get();
	private SearchService mSearchService = new SearchService();

	public Ret save(Document doc) {
		doc.setCreateAt(new Date());
		doc.setUpdateAt(new Date());
		doc.save();
		DocumentService.me.clearCache();    // 清缓存
		return Ret.ok();
	}
	
	
	/****
	 * 通过服务器导入
	 * @param excelFile
	 * @param accountId
	 * @return
	 */
	public boolean batchImportByExecelFile(File excelFile, int accountId) {
		
		try {
			JuziExcelReader reader  = new JuziExcelReader(excelFile);
			reader.prepare();
			
			final int batchSize = 20;
			while(true) {
				List<JuziObject> juziList = reader.nextJuzi(batchSize);
				if(juziList == null) {
					break;
				}
				
				ArrayList<Juzi> toSaveList = new ArrayList<>(juziList.size());
				
				for(JuziObject jOb: juziList) {
					Juzi juzi = JuziUtils.createJuzi(jOb);
					toSaveList.add(juzi);
				}
				
				//存储到数据库
				Db.batchSave(toSaveList, 20);
				//更新服务器的索引数据
				//TODO 如果服务器的数据与索引不同步怎么办
				mSearchService.updateSearchIndex(toSaveList);
				
			}
			return true;
		}catch (Exception e) {
			LogKit.warn(e.getMessage(),e);
		}
		
		return false;
		
	}
	


}
