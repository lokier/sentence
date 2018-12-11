package com.juzicool.search.plugin;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class ElasticSearch {
	
	private static Prop p = PropKit.use("jfinal_club_config_dev.txt");
    private static Logger log = Logger.getLogger(ElasticSearch.class);

	private static final int MAX_INSTANCE_COUNT = 20;
	private static ElasticSearch gES = new ElasticSearch();
	
	private int maxRequst = MAX_INSTANCE_COUNT;
	private int currentRequestCount = 0;
	
	private  LinkedList<RestClient> mClientsLinks = new LinkedList<>();
	
	public static ElasticSearch get() {
		return gES;
	}
	
	RestClientBuilder mBuilder;
	
	private ElasticSearch(){
		mBuilder = createRestClientBuilder();
	}
	
	private static RestClientBuilder createRestClientBuilder() {

		final String host = p.get("es_host", "localhost");
		final int port = Integer.parseInt(p.get("es_port", "9200"));
		final String name = p.get("es_name","");
		final String password = p.get("es_password","");
		final int timeout = Integer.parseInt(p.get("es_timeout","30000"));

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
	        
	        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
	            @Override
	            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
	                return requestConfigBuilder.setConnectTimeout(timeout)
	                        .setSocketTimeout(timeout);
	            }
	        })
	        .setMaxRetryTimeoutMillis(timeout);
	     
	        if(!StringUtils.isEmpty(name)) {
	        	 final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	 	        credentialsProvider.setCredentials(AuthScope.ANY,
	 	                new UsernamePasswordCredentials(name, password));
	            builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
		               @Override
		               public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
		                        //httpClientBuilder.disableAuthCaching(); 
		               return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
		                }
		      });
	        }
	   
	
	        		return builder;
	}
	
	public RestClient requestClient() {
		final RestClient client;
		synchronized (this) {
			currentRequestCount++;
			 client = mClientsLinks.pollFirst();
		}
		
		if(currentRequestCount > maxRequst) {
			log.warn("requestClient: 请求的ES实例超过池中的数量：maxRequst = " + maxRequst);
			maxRequst = currentRequestCount;
		}
		
		if(client!= null) {
			return client;
		}
		RestClient restClient = mBuilder.build();
		return restClient;
	}

	
	public void releaseClient(RestClient client) {
		
		synchronized (this) {
			currentRequestCount --;
			if(mClientsLinks.size() < MAX_INSTANCE_COUNT) {
				mClientsLinks.push(client);
				return;
			}
		}
		try {
			client.close();
		} catch (IOException e) {
			log.warn("releaseClient:", e);
		}
	}

}
