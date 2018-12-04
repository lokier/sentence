package com.juzicool.client;

import java.util.Collections;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class RestClientTest {

	public static void main(String[] args)throws Exception {
		RestClient restClient = RestClient.builder(
	            new HttpHost("localhost", 9200, "http")).build();
		Response response = restClient.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
        System.out.println(EntityUtils.toString(response.getEntity()));

        restClient.close();

		
	}

}
