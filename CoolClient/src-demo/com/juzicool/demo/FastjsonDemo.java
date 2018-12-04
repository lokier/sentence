package com.juzicool.demo;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class FastjsonDemo {

	 public static void main(String[] args) {
	        serialize();
	        deserialize();
	    }

	    public static void serialize() {
	        User user = new User();
	        user.setId(11L);
	        user.setName("西安");
	        user.setCreateTime(new Date());
	        String jsonString = JSON.toJSONString(user);
	        System.out.println(jsonString);
	    }

	    public static void deserialize() {
	        String jsonString = "{\"createTime\":\"2018-08-17 14:38:38\",\"id\":11,\"name\":\"西安\"}";
	        User user = JSON.parseObject(jsonString, User.class);
	        System.out.println(user.getName());
	        System.out.println(user.getCreateTime());
	    }
	    
	    
	    public static class User {

	        private Long   id;

	        private String name;

	        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	        private Date   createTime;

	        public Long getId() {
	            return id;
	        }

	        public void setId(Long id) {
	            this.id = id;
	        }

	        public String getName() {
	            return name;
	        }

	        public void setName(String name) {
	            this.name = name;
	        }

	        public Date getCreateTime() {
	            return createTime;
	        }

	        public void setCreateTime(Date createTime) {
	            this.createTime = createTime;
	        }
	        
	    }

}
