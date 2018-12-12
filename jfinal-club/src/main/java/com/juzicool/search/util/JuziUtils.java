package com.juzicool.search.util;

import com.alibaba.fastjson.JSON;
import com.jfinal.club.common.model.Juzi;
import com.juzicool.search.JuziObject;

public class JuziUtils {
	
	public static String toJson(JuziObject juzi) {
		 String jsonString = JSON.toJSONString(juzi);
		 return jsonString;
	}
	
	public static Juzi createJuzi(JuziObject object) {
		Juzi juzi = new Juzi();
		
		juzi.setApplyDesc(object.getApplyDesc());
		juzi.setContent(object.getContent());
		juzi.setAuthor(object.getAuthor());
		juzi.setTags(object.getTags());
		juzi.setCategory(object.getCategory());
		juzi.setLength(object.getLength());
		juzi.setUpdateAt(object.getUpdateAt());
		juzi.setFrom(object.getFrom());
		juzi.setRemark(object.getRemark());
		return juzi;
	}

}
