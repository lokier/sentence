package com.juzicool.client;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

public class RestClientTest {
	
	public static RestClient createRestClient() {
		final String host = "localhost";
		final int port = 9200;
		final String name ="";
		final String password = "";
		final int timeout = 30000;

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

	        	
	
	    return builder.build();
	}

	public static void main(String[] args)throws Exception {
		
		
		 RestClient restClient = createRestClient();
		 Response response = restClient.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
        System.out.println(EntityUtils.toString(response.getEntity()));

        restClient.close();

		
	}

}
