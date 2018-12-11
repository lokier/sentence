package com.juzicool.search;

public interface JuziObject {

   public void setContent(java.lang.String content);
	
	public java.lang.String getContent();

	public void setLength(java.lang.Integer length);
	
	public java.lang.Integer getLength();

	public void setAuthor(java.lang.String author);
	
	
	public java.lang.String getAuthor();

	public void setFrom(java.lang.String from) ;
	
	public java.lang.String getFrom() ;
	

	public void setCategory(java.lang.String category);
	
	public java.lang.String getCategory() ;

	public void setRemark(java.lang.String remark) ;
	
	public java.lang.String getRemark() ;

	public void setTags(java.lang.String tags);
	
	public java.lang.String getTags() ;
	
	public void setApplyDesc(java.lang.String applyDesc);
	
	public java.lang.String getApplyDesc() ;

	public void setUpdateAt(java.util.Date updateAt) ;
	
	public java.util.Date getUpdateAt() ;

	


}
