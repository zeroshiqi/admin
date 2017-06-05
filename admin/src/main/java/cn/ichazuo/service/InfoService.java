package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;

public class InfoService {
	
	public long findCount(){
		return Db.queryLong("select count(*) from t_info");
	}
	public long findSmsCount(String mobile,String coursename){
		String sql="select count(*) from t_sms_report where 1=1";
		if(!StringUtils.isNullOrEmpty(mobile) && mobile!=null){
			sql+=" and mobile like '%"+mobile+"%'";
		}
		if(!StringUtils.isNullOrEmpty(coursename) && coursename!=null){
			sql+=" and course_name like '%"+coursename+"%'";
		}
		return Db.queryLong(sql);
	}
	
	public List<Record> findList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select i.id,i.create_at as createAt,i.text,i.text as alltext,c.course_name ", " from t_info i left join t_course c on i.course_id = c.id").getList();
	}
	public List<Record> findSmsList(int page,String mobile,String coursename){
		String sql=" from t_sms_report as i where 1=1";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql+=" and mobile like '%"+mobile+"%'";
		}
		if(!StringUtils.isNullOrEmpty(coursename)){
			sql+=" and course_name like '%"+coursename+"%'";
		}
		sql+=" order by i.create_at desc";
		return Db.paginate(page, Result.PAGE_COUNT,"select i.id,i.create_at as createAt,i.content,i.success ,i.course_name,i.mobile,i.recode", sql).getList();
	}
	
	public void save(Record record){
		Db.save("t_info", record);
	}
	
	public List<Record> findOfflineCourse(){
		return Db.find("select c.course_name as value,c.id from t_course_offline o left join t_course c on c.id = o.course_id where o.`status` = 1 and c.status = 1");
	}
	
	public List<Record> findCourseMember(Long courseId){
		return Db.find("select b.name,b.mobile from t_course_web_order a join t_course_web_order_user b on a.id = b.order_id where a.course_id = ? and a.status=1 and b.status=1 and a.review_status=1",courseId);
	}
	
	public void saveSms(Record record){
		Db.save("t_sms_report", record);
	}
}
