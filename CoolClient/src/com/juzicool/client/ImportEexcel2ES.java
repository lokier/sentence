package com.juzicool.client;

import java.io.File;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.juzicool.base.Juzi;
import com.juzicool.base.JuziExcelReader;

public class ImportEexcel2ES {

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

		//创建索引
		
		
		while(true) {
			List<Juzi> list = reader.nextJuzi(30);
			if(list == null) {
				break;
			}
			
			for(Juzi juzi: list) {
				System.out.println(juzi.toString());
			}
			
		}
		
		restClient.close();
		
		reader.close();

	}

}
