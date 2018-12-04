package com.juzicool.base;

public class Juzi {
	
	public String content;
	public String from;
	public String author;
	public String category; //分类
	public String remark; //点评
	public String tags; //鉴赏标签
	public String applyTags; //应用标签
	
	
	@Override
	public String toString() {
		return "Juzi [from=" + from + ", author=" + author + ", category=" + category
				+ ", remark=" + remark + ", tags=" + tags + ", applyTags=" + applyTags + "content=" + content + "]";
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getApplyTags() {
		return applyTags;
	}


	public void setApplyTags(String applyTags) {
		this.applyTags = applyTags;
	}
	
	

	
}
