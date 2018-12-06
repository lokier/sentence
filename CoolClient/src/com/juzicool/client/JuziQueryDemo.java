package com.juzicool.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

public class JuziQueryDemo {
	
	
	
	public static void main(String[] args)throws Exception {
		RestClient restClient = RestClient.builder(
	            new HttpHost("localhost", 9200, "http")).build();
		/**
		 * GET /juzicool/juzi/_search?explain=false
{
     "query": {
        "multi_match": {
            "query":  " 哭 高兴",
            "type":   "most_fields", 
            "fields": [ "tags","remark","applyDesc","content","category","author" ,"from"],
            "analyzer": "ik_smart"
          
        }
    },
    "size":50,
    "from":0
}
		 */
		Request r = new Request("GET", "/juzicool/juzi/_search");
		
		String keyword = "哭 高兴";
		int offset = 0;
		int size = 10;
		
		String json = "{" + 
				"     \"query\": {" + 
				"        \"multi_match\": {" + 
				"            \"query\":  \""+keyword+"\"," + 
				"            \"type\":   \"most_fields\", " + 
				"            \"fields\": [ \"tags\",\"remark\",\"applyDesc\",\"content\",\"category\",\"author\" ,\"from\"]," + 
				"            \"analyzer\": \"ik_smart\"" + 
				"          " + 
				"        }" + 
				"    }," + 
				"    \"size\":"+size+"," + 
				"    \"from\":"+offset+"" + 
				"}";
		r.setJsonEntity(json);
		
		CountDownLatch latch = new CountDownLatch(1);
		
		restClient.performRequestAsync(r, new ResponseListener() {
			
			@Override
			public void onSuccess(Response repose) {
				HttpEntity entity = repose.getEntity();
				InputStream in = null;
				try {
					in = entity.getContent();
					List<String>  lines = IOUtils.readLines(in, "utf8");
					for(String line: lines) {
						System.out.println(line);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					IOUtils.closeQuietly(in);
				}
				
				latch.countDown();
			}
			
			@Override
			public void onFailure(Exception arg0) {
				latch.countDown();
			}
		});
		
		latch.await();

        restClient.close();

		
	}
}
