package cn.ichazuo.service;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

/**
 * @ClassName: CrowdfundingService 
 * @Description: (众筹Service) 
 * @author ZhaoXu
 * @date 2015年9月16日 下午1:16:40 
 * @version V1.0
 */
public class CrowdfundingService {
	
	/**
	 * @Title: findAllFailOrders 
	 * @Description: (查询失败的众筹的订单) 
	 * @param page
	 * @return
	 */
	public List<Record> findAllFailOrders(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select o.`code`,o.id,o.price,o.create_at as createAt,o.type", "from t_course_web_crowdfunding_log o left join t_course_web_crowdfunding w on w.id = o.crowdfund_id where (w.status = 1 and (w.status = 1 and HOUR(timediff(now(),w.create_at)) >= 72) or w.status = 3) and o.status = 1 order by id desc").getList();
	}
	
	/**
	 * @Title: findAllFailOrderCount 
	 * @Description: (查询失败的众筹的订单数量) 
	 * @return
	 */
	public Long findAllFailOrderCount(){
		return Db.queryLong("select count(*) from t_course_web_crowdfunding_log o left join t_course_web_crowdfunding w on w.id = o.crowdfund_id where (w.status = 1 and (w.status = 1 and HOUR(timediff(now(),w.create_at)) >= 72) or w.status = 3) and o.status = 1");
	}
	
	public List<Record> findAllCrowd(int page,int select,String name){
		String findSql = "select  ifnull(g.weixin,'') as weixin,ifnull(g.phone,'') as phone,g.id,c.course_name,g.price,g.nickname,g.create_at,if(HOUR(timediff(now(),g.create_at)) >= 72 and g.status = 1,3,g.`status`) as status,ifnull((select sum(price) from t_course_web_crowdfunding_log l where l.crowdfund_id = g.id and l.status = 1),0) as sumPrice,g.refund  ";
		if(select == 1){
			return Db.paginate(page, Result.PAGE_COUNT, findSql, "from t_course_web_crowdfunding g left join t_course c on c.id = g.course_id where g.`status` > 0 and g.refund = 0 and g.nickname like ? and if(HOUR(timediff(now(),g.create_at)) >= 72 and g.status = 1,3,g.`status`) = 3  order by g.id desc ","%"+name+"%").getList();
		}else if(select == 2){
			return Db.paginate(page, Result.PAGE_COUNT, findSql, "from t_course_web_crowdfunding g left join t_course c on c.id = g.course_id where g.`status` = 2 and g.nickname like ? order by g.id desc ","%"+name+"%").getList();
		}
		return Db.paginate(page, Result.PAGE_COUNT, findSql, "from t_course_web_crowdfunding g left join t_course c on c.id = g.course_id where g.`status` > 0 and g.nickname like ? order by g.id desc ","%"+name+"%").getList();
	}
	
	public Long findAllCrowdCount(int select,String name){
		if(select == 1){
			return Db.queryLong("select count(*) from t_course_web_crowdfunding where `status` > 0 and refund = 0 and if(HOUR(timediff(now(),create_at)) >= 72 and status = 1,3,`status`) = 3 and nickname like ?","%"+name+"%");
		}else if(select == 2){
			return Db.queryLong("select count(*) from t_course_web_crowdfunding where `status` = 2 and nickname like ?","%"+name+"%");
		}
		return Db.queryLong("select count(*) from t_course_web_crowdfunding where `status` > 0 and nickname like ?","%"+name+"%");
	}
	
	public Long findInfoCount(Long id){
		return Db.queryLong("select count(*)  from ((select c.nickname as name,o.id as id,c.`content` as content,o.`price` as price,o.code from t_course_web_crowdfunding_order o left join `t_course_web_crowdfunding` c on c.id = o.`crowdfunding_id` where c.id = ? and o.status = 1) union (select name,id,content,price,code from t_course_web_crowdfunding_log  where status = 1 and crowdfund_id = ?)) t",id,id);
	}
	
	public List<Record> findInfoList(int page,Long id){
		return Db.paginate(page, Result.PAGE_COUNT, "select t.name,t.id,t.content,t.price,t.code", " from ((select c.nickname as name,o.id as id,c.`content` as content,o.`price` as price,o.code from t_course_web_crowdfunding_order o left join `t_course_web_crowdfunding` c on c.id = o.`crowdfunding_id` where c.id = ? and o.status = 1) union (select name,id,content,price,code from t_course_web_crowdfunding_log  where status = 1 and crowdfund_id = ?)) t",id,id).getList();
	}
	
	public void updateRefund(Long id){
		Db.update("update t_course_web_crowdfunding set refund = 1 where id = ?", id);
	}
	
	public List<Record> findRefundList(Long id){
		return Db.find("select name,mobile from t_course_web_crowdfunding_user u where `crowdfund_id` = ?",id);
	}
	
	public BigDecimal findSumPrice(Long id){
		return Db.queryBigDecimal("select sum(price) from t_course_web_crowdfunding_log where crowdfund_id = ? and status = 1 ",id);
	}
}
