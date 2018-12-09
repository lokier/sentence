package com.juzicool.search;

public class Juzi {
	
	public String content;
	public String from;
	public String author;
	public String category; //分类
	public String remark; //点评
	public String tags; //鉴赏标签
	public String applyDesc;
	public long updateTime = System.currentTimeMillis();
	
	
	@Override
	public String toString() {
		return "Juzi [from=" + from + ", author=" + author + ", category=" + category
				+ ", remark=" + remark + ", tags=" + tags + ", length=" + getLength() + ", applyDesc=" + applyDesc + "content=" + content + "]";
	}
	
	

	public long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}



	public void setLength(int length) {
		//
	}
	
	public int getLength() {
		return content != null ? content.length(): 0;
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


	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}

	
}
