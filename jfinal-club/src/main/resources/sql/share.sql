#sql("paginate")
	select s.id,
		substring(s.title, 1, 100) as title,
		substring(s.content, 1, 180) as content,
		a.avatar,
		a.id as accountId
	from share s inner join account a on s.accountId = a.id
	where report < #para(0)
	order by s.createAt desc
#end

#sql("findById")
	select s.*, a.avatar, a.nickName 
	from share s inner join account a on s.accountId = a.id
	where s.id = #para(0) and s.report < #para(1) limit 1
#end

#sql("getHotShare")
	select id, title from share where id in (
		select shareId  from (
			select shareId from share_page_view where visitDate > #para(0) order by visitCount desc
		) as t group by shareId
	) and report < #para(1) limit 10
#end

#sql("getReplyPage")
	select sr.*, a.nickName, a.avatar
	from share_reply sr inner join account a on sr.accountId = a.id
	where shareId = #para(0)
#end



