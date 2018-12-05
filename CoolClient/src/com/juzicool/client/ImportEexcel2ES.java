package com.juzicool.client;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import com.alibaba.fastjson.JSON;
import com.juzicool.base.Juzi;
import com.juzicool.base.JuziExcelReader;

public class ImportEexcel2ES {
	private static final String INDEXS = "juzicool";
	private static final String INDEX_NAME_TYPE = "juzi";
	
	
	private static void initIndex(RestClient client)throws Exception {
		//创建索引
		Request request = new Request("DELETE",  "/"+INDEXS);
		try {
			Response r = client.performRequest(request);
		}catch(Exception ex) {
			
		}
	}
	
	/**
	 *  导入execl到es里面。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception {
		File execFile = new File("句子迷句集.xls");
		
		JuziExcelReader reader  = new JuziExcelReader(execFile);
		reader.prepare();
		
		RestClient restClient = RestClient.builder(
	            new HttpHost("localhost", 9200, "http")).build();
		
		
		//initIndex(restClient);
		
		int juziId = 0;
		while(true) {
			List<Juzi> list = reader.nextJuzi(30);
			if(list == null) {
				break;
			}
			
			CountDownLatch latch = new CountDownLatch(list.size());
			int i = 0;
			for(Juzi juzi: list) {
				String jsonString = JSON.toJSONString(juzi);
				
				juziId++;
				Request request = new Request("put",  "/"+INDEXS+"/"+INDEX_NAME_TYPE+"/"+juziId);

				request.setJsonEntity(jsonString);
				restClient.performRequestAsync(request, new ResponseListener() {
					
					@Override
					public void onSuccess(Response arg0) {
						latch.countDown();
					}
					
					@Override
					public void onFailure(Exception arg0) {
						latch.countDown();
				
					}
				});
				System.out.println("put indexs  : " + juziId +", json: " + jsonString);

			}

			latch.await();
			
		}
		
	/*	GET /juzicool/juzi/_search
		{
		  "query" : { "match" : { "remark" : "高考" }},
		  "size": 3
		}*/
		
		restClient.close();
		
		reader.close();

	}
	
	

}
