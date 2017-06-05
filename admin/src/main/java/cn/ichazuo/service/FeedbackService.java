package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

/**
 * @ClassName: FeedbackService 
 * @Description: (意见反馈Service) 
 * @author ZhaoXu
 * @date 2015年8月12日 上午11:35:29 
 * @version V1.0
 */
public class FeedbackService {
	
	/**
	 * @Title: findFeedBackList 
	 * @Description: (查询意见反馈列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findFeedBackList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select f.id,f.content,f.create_at,m.mobile,m.id as memberId,m.nick_name ", "from t_feedback f left join t_member m on f.member_id = m.id order by f.id desc").getList();
	}
	
	/**
	 * @Title: findFeedBackCount 
	 * @Description: (查询意见反馈总数) 
	 * @return
	 */
	public Long findFeedBackCount(){
		return Db.queryLong("select count(*) from t_feedback");
	}
	/**
	 * @Title: findBusinessFeedBackCount 
	 * @Description: (查询企业版APP意见反馈总数) 
	 * @return
	 */
	public Long findBusinessFeedBackCount(){
		return Db.queryLong("select count(*) from t_business_feedback");
	}
	/**
	 * @Title: findBusinessFeedBackList 
	 * @Description: (查询企业版APP意见反馈列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findBusinessFeedBackList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select f.id,f.content,f.create_at,f.platform,m.mobile,m.id as employeeId,m.name, m.position,a.business_name as businessName ", "from t_business_feedback f left join t_business_employee m on f.employee_id = m.id join t_business a on a.id = m.business_id order by f.id desc").getList();
	}
}
