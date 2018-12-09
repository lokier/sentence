/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.juzicool.search.plugin;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.juzicool.search.Juzi;
import com.jfinal.club.common.safe.JsoupFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.club.common.model.Feedback;
import com.jfinal.club.common.model.Project;
import com.jfinal.club.common.model.Share;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;import java.util.PrimitiveIterator.OfDouble;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

/**
 * 首页业务，主要为了方便做缓存，以及排序逻辑
 */
public class SearchService {

	public static final SearchService me = new SearchService();

	ElasticSearch es = new ElasticSearch();
	
	/**
	 * 
	 * @param query
	 * @param pageIndex  从1开始。
	 * @param pageSize
	 * @return
	 */
	public Page<Juzi> query(String query,final int currenPage,final int pageSize){
		
		if(currenPage < 1 || pageSize < 1) {
			throw new RuntimeException("error index must > 0 ");
		}
		int pageIndex = currenPage - 1;
		
		RestClient client = es.requestClient();
		try {
			int offset = pageIndex * pageIndex;
			
			String jsonResult = query(client,query,offset,pageSize);
			System.out.println("jsonResult:\n" + jsonResult);
			
			//为空：{"took":4,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":0,"max_score":null,"hits":[]}}
			//{"took":26,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":1717,"max_score":9.646362,"hits":[{"_index":"juzicool","_type":"juzi","_id":"2848","_score":9.646362,"_source":{"applyDesc":"","author":"","category":"感悟","content":"岁月因温良而静好；时光因唯美而温馨，愿爱情的花朵的在春花秋月的过往里开的更加艳丽，未免多了几分人世间烟火的味道，少了几许沧桑和惆怅。我们来到这个世界并不是只会索取，而是要像花儿一样时刻学会绽放，把最美的容颜留给世人来欣赏。","from":"","length":111,"remark":"唯美语录","tags":"伤感，唯美","updateTime":1544242751394}},{"_index":"juzicool","_type":"juzi","_id":"2932","_score":9.646362,"_source":{"applyDesc":"","author":"","category":"感悟","content":"婉约月下，幽蓝意境，如水心情在夜下慢舞，诗意化身点缀星光灿烂，思在微风中浅行，念在月光中飘远，诗情画意是笔尖的缠绵，灯火阑珊是相遇的旖旎，迷了心扉，醉了脚步，灵魂相遇，爱染流年，温润岁月诗行，心缱绻如水光环的星空，踩着唯美韵脚，步入爱的银河。","from":"","length":121,"remark":"唯美语录","tags":"伤感，唯美","updateTime":1544242751731}},{"_index":"juzicool","_type":"juzi","_id":"2909","_score":9.172866,"_source":{"applyDesc":"","author":"","category":"感悟","content":"取一份云水的清欢，放逐于心灵的陌上，安放一颗尘心，去淡一抹铅华，淡雅的人生；此时心好似阳光下朵朵彩蝶，曼舞着红尘，这一路浅歌，暖暖的入了眸，也入了画；欣喜着，这朵岁月烟火如此唯美；心在阳光下，微笑，微笑所有的凡事浮华，原来事事安然与否，在于心；顿悟中禅了心境，盈了岁月！","from":"","length":135,"remark":"唯美语录","tags":"伤感，唯美","updateTime":1544242751629}},{"_index":"juzicool","_type":"juzi","_id":"2864","_score":9.068253,"_source":{"applyDesc":"","author":"","category":"感悟","content":"有一种遇见，于千万人中，只此一眼，便是眼睛与眼睛的重逢；是心与心的相依。一场花开，一场相爱，穿过季节的长廊，幻化成一抹暗香，妖娆了每一个清晨与黄昏，芬芳了似水流年，唯美了指尖岁月。折一段时光，写一抹眷恋，碾过红尘悲欢，绕过眉心不悔，将相遇的感动静收心间，在心灵深处，温暖生香。","from":"","length":138,"remark":"唯美语录","tags":"伤感，唯美","updateTime":1544242751510}},{"_index":"juzicool","_type":"juzi","_id":"2397","_score":7.4827576,"_source":{"applyDesc":"","author":"","category":"心语","content":"懂得，是一种沉淀的情怀，一种豁达的感觉，一种寂静的美丽，温暖的阳光，唯美如蓝天，平静如大海。","from":"","length":46,"remark":"那些在我心房停留过的美丽文字，一字、一词，一句，打动心扉。","tags":"伤感，唯美","updateTime":1544242748279}},{"_index":"juzicool","_type":"juzi","_id":"1214","_score":5.639571,"_source":{"applyDesc":"写情、伤感","author":"","category":"情感","content":"歌手:暗恋你，我求而不得的感悟，隐晦表达，你却说我讲的歌词很生动。 作家:喜欢你，我暗藏已久的告白，终于倾诉，你却说我写的小说很感人。 画师:思念你，我细腻温柔的心意，笔墨描绘，你却说我画的意境很唯美。 演员:爱上你，我精心准备的开场，浪漫展现，你却说我演的戏码很逼真。","from":"","length":135,"remark":"生活中总有一些事，会让你刹那间的感动，刹那间的心疼，忽然间就泪流满面","tags":"伤感，唯美","updateTime":1544242742326}},{"_index":"juzicool","_type":"juzi","_id":"341","_score":5.639571,"_source":{"applyDesc":"","author":"","category":"心语","content":"谁为你画一副中国风的韵味，勾勒古典的唯美；而我在画中沉醉，不理会，孰是孰非；  谁为你写一句中国风的诗词，传说古典的妩媚，而我用押韵点缀，不理会，你的虚伪；  谁为你填一曲中国风的歌谣，歌颂古典的颓废，而我在曲中心碎，不理会，伤痕累累。","from":"","length":118,"remark":"陶人喜欢、治愈的句子","tags":"治愈、唯美","updateTime":1544242736661}},{"_index":"juzicool","_type":"juzi","_id":"168","_score":5.518466,"_source":{"applyDesc":"","author":"","category":"心语","content":"嫁衣如火灼伤了天涯，从此残阳烙我心上如朱砂； 你笑颜如花倾醉了天下，从此红尘寄我诗笺传佳话； 你心如止水披上了袈裟，从此菩提开满寂寞成镜花； 你红妆如画诗化了婚纱，从此相濡以沫相伴至白发； 你朱唇如血染红了牵挂，从此红尘万丈执念忘韶华； 你回眸如梦唯美了烟花，从此落花流水空负了刹那。","from":"","length":142,"remark":"陶人喜欢、治愈的句子","tags":"治愈、唯美","updateTime":1544242735448}},{"_index":"juzicool","_type":"juzi","_id":"111","_score":5.1889396,"_source":{"applyDesc":"","author":"","category":"心语","content":"【琵琶】弹一曲琵琶妩媚了谁的多情泪眼；【鸳鸯】绣一副鸳鸯等瘦了谁的似水流年； 【折扇】执一纸折扇诗化了谁的如花美眷；【纸伞】撑一次纸伞邂逅了谁的前世遇见； 【玉簪】赠一饰玉簪传说了谁的千古美言；【胭脂】涂一缕胭脂唯美了谁的倾世容颜； 【珠帘】卷一世珠帘寂寞了谁的千年执念。","from":"","length":136,"remark":"陶人喜欢、治愈的句子","tags":"治愈、唯美","updateTime":1544242734823}},{"_index":"juzicool","_type":"juzi","_id":"261","_score":5.1889396,"_source":{"applyDesc":"","author":"","category":"心语","content":"掬一泓流水，饮尽三千心碎，这半分虚伪，在意境中颓废，在眼泪中唯美； 吟一曲伤悲，冷却一生憔悴，这一丝妩媚，在琉璃中沉醉，在结局中入睡； 锁一缕芳菲，寂寞一世轮回，这一声卑微，在沉默中描绘，在无情中妄为； 许一世相随，吟咏一生无悔，这一滴眼泪，在多情中入味，在真情中回馈。","from":"","length":135,"remark":"陶人喜欢、治愈的句子","tags":"治愈、唯美","updateTime":1544242736105}}]}}

			JSONObject json = JSONObject.parseObject(jsonResult);
			
			
			if(json.containsKey("hits")) {
				JSONObject hitsObject = json.getJSONObject("hits");
				ArrayList<Juzi> juziList = new ArrayList<>();
				
				int total = hitsObject.getInteger("total");
				JSONArray hitsArray = hitsObject.getJSONArray("hits");
				int length = hitsArray!= null ? hitsArray.size():0;
				for(int i = 0; i < length;i++) {
					JSONObject juziItem = hitsArray.getJSONObject(i).getJSONObject("_source");
					
					Juzi juzi = new Juzi();
					juzi.applyDesc = juziItem.getString("applyDesc");
					juzi.author = juziItem.getString("author");
					juzi.category = juziItem.getString("category");
					juzi.content = toHtml(juziItem.getString("content"));
					juzi.from = juziItem.getString("from");
					//juzi.length = juziItem.getIntValue("length");
					juzi.remark = juziItem.getString("remark");
					juzi.tags = juziItem.getString("tags");
					juzi.updateTime = juziItem.getLongValue("updateTime");
					juziList.add(juzi);
				}
				
				int totalPage = total / pageSize;
				if(total%pageSize != 0) {
					totalPage++;
				}
				
				return new Page<Juzi>(juziList,pageIndex,pageSize,totalPage,total);
			}
			
			//json.get

		}catch(Exception ex){
			System.err.println(ex);
		}finally {
			es.releaseClient(client);
		}
		
		return null;
	} 
	
	public static String query(RestClient restClient,String keyword,int offset,int size)throws Exception {
		
		Request r = new Request("GET", "/juzicool/juzi/_search");
		String json = "{" + 
				"     \"query\": {" + 
				"        \"multi_match\": {" + 
				"            \"query\":  \""+keyword+"\"," + 
				"            \"type\":   \"most_fields\", " + 
				"            \"fields\": [ \"tags\",\"remark\",\"applyDesc\",\"content\",\"category\",\"author\" ,\"from\"]," + 
				"            \"analyzer\": \"ik_smart\"" + 
				"          " + 
				"        }" + 
				"    }," + 
				"    \"size\":"+size+"," + 
				"    \"from\":"+offset+"" + 
				"}";
		r.setJsonEntity(json);
				
		Response reponse = restClient.performRequest(r);

		HttpEntity entity = reponse.getEntity();
		
		return EntityUtils.toString(entity, "utf8");
	
	}
	
	private static String toHtml(String text) {
		if(text == null) {
			return null;
		}
		
		text = text.replaceAll("\r\n", "<br/>");
		text = text.replaceAll("\r", "<br/>");
		text = text.replaceAll("\n", "<br/>");

		return text;
	}
}



