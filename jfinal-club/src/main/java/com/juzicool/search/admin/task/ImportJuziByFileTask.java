package com.juzicool.search.admin.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jfinal.club.common.model.Juzi;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.juzicool.search.JuziObject;
import com.juzicool.search.admin.JuziExcelReader;
import com.juzicool.search.plugin.SearchService;
import com.juzicool.search.util.JuziUtils;

public class ImportJuziByFileTask {
	
	private final static HashMap<Integer,ImportJuziByFileTask> gMap = new HashMap<>();
	
	public static synchronized ImportJuziByFileTask get(int accountId) {
		ImportJuziByFileTask task = gMap.get(accountId);
		if(task == null) {
			task = new ImportJuziByFileTask(accountId);
		}
		return task;
	}
	
	private static synchronized void release(ImportJuziByFileTask task) {
		gMap.remove(task.mAccountId);
	}
	
	private static synchronized void add(ImportJuziByFileTask task) {
		gMap.put( task.mAccountId, task);
	}
	
	private final int mAccountId;
	private  File mUploadFile;
	private boolean isRunning = false;
	
	private ImportJuziByFileTask(int accountId) {
		mAccountId = accountId;
		File dirFile = new File(PathKit.getWebRootPath()+"/upload/importjuzi/");
		if(!dirFile.exists() ||  !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		mUploadFile = new File(dirFile,accountId+".excel");
		mUploadFile = new File("E:\\git-hub\\sentence\\CoolClient\\句子迷句集.xls");
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public File getFile() {
		return mUploadFile;
	}
	
	
	public void start() {
		isRunning = true;
		final ImportJuziByFileTask task = this;
		add(task);
		new Thread() {
			
			@Override
			public void run() {
				
				try {
					JuziExcelReader reader  = new JuziExcelReader(mUploadFile);
					reader.prepare();
					final int batchSize = 20;
					 SearchService mSearchService = new SearchService();

					int count = 0;
					while(true) {
						List<JuziObject> juziList = reader.nextJuzi(batchSize);
						if(juziList == null) {
							break;
						}
						
						ArrayList<Juzi> toSaveList = new ArrayList<>(juziList.size());
						
						for(JuziObject jOb: juziList) {
							Juzi juzi = JuziUtils.createJuzi(jOb);
							Date createDate = new Date();
							juzi.setCreateAt(createDate);
							juzi.setUpdateAt(createDate);
							juzi.setAccountAt(mAccountId);
							toSaveList.add(juzi);
						}
						
						//存储到数据库
						Db.batchSave(toSaveList, 20);
						//更新服务器的索引数据
						//TODO 如果服务器的数据与索引不同步怎么办
						mSearchService.updateSearchIndex(toSaveList);
						count++;
						System.out.println(" import count :  " +  count );
					}
				}catch (Exception e) {
					LogKit.warn(e.getMessage(),e);
				}
				
				isRunning = false;
				release(task);
			}
		}.start();
	}
	
}
