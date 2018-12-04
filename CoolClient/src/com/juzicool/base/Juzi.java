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

	
}
