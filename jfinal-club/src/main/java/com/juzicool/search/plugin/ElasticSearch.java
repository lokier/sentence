package com.juzicool.search.plugin;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.juzicool.search.Juzi;

/*pacakge*/ class ElasticSearch {
	
	private static Prop p = PropKit.use("jfinal_club_config_dev.txt");
	
	RestClientBuilder mBuilder;
	
	ElasticSearch(){
		mBuilder = createRestClientBuilder();
	}
	
	private static RestClientBuilder createRestClientBuilder() {

		final String host = p.get("es_host", "localhost");
		final int port = Integer.parseInt(p.get("es_port", "9200"));
		final String name = p.get("es_name","");
		final String password = p.get("es_password","");
		final int timeout = Integer.parseInt(p.get("es_timeout","30000"));

		RestClientBuilder builder = RestClient.builder(
	            new HttpHost(host, port));
		
	     
	        
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
		RestClient restClient = mBuilder.build();
		return restClient;
	}

	
	public void releaseClient(RestClient client) {
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
