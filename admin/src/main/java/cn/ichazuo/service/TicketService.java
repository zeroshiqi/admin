package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.model.Member;

public class TicketService {
	
	
	public Long findCount(Long score,String topParentName,String parentName){
		String sql = "select count(*) from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and last_time is null";
		if(score != 0){
			sql +=" and score >="+score;
		}
		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
			sql+=" and d.title like '%"+topParentName+"%'";
		}
		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
			sql+=" and c.title like '%"+parentName+"%'";
		}
		return Db.queryLong(sql);
	}
	//根据一级目录Id查询参考成绩单总数
	public Long findCountByTopParentId(Long topId){
		return Db.queryLong("select count(*) from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and o.last_time is null and o.top_parent_id= ?",topId);
	}
	//根据二级目录Id查询参考成绩单总数
	public Long findCountByParentId(Long parentId){
		return Db.queryLong("select count(*) from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and o.last_time is null and o.parent_id= ? ",parentId);
	}
	
//	public List<Record> findList(int page){
//		return Db.paginate(page, Result.PAGE_COUNT, "select status,id,nick_name,price,level,score", "from t_ticket where last_time is null").getList();
//	}
	
	public List<Record> findList(int page,Long score,String parentName,String topParentName){
		String sql="from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and last_time is null";
		if(score != 0){
			sql +=" and score >= "+score;
		}
		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
			sql+=" and c.title like '%"+parentName+"%'";
		}
		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
			sql+=" and d.title like '%"+topParentName+"%'";
		}
		sql +=" order by score desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.status, o.id,o.nick_name,o.price,o.level,o.score,c.title as typetwo,d.title as typeone", sql).getList();

	}
	//根据一级目录Id查询参考成绩单
	public List<Record> findListByTopParentId(int page,Long topId){
		return Db.paginate(page, Result.PAGE_COUNT, "select o.status, o.id,o.nick_name,o.price,o.level,o.score,c.title as typetwo,d.title as typeone", "from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and o.last_time is null and top_parent_id = ? order by o.score desc",topId).getList();
	}
	//根据二级目录Id查询参考成绩单
	public List<Record> findListByParentId(int page,Long parentId){
		return Db.paginate(page, Result.PAGE_COUNT, "select o.status, o.id,o.nick_name,o.price,o.level,o.score,c.title as typetwo,d.title as typeone", "from t_ticket o inner join t_question_second_type c on c.id = o.parent_id join t_question_first_type d on c.parent_id = d.id where o.`status` = 1 and c.`status` = 1 and o.last_time is null and o.parent_id = ? order by o.score desc",parentId).getList();
	}
	
	
	//////////////////////////////////////////   好多课App专用         //////////////////////////////////////
	
	public List<Record> findAppList(int page,String courseName,String title){
		String sql="from v_business_ticket where 1=1";
//		if(score != 0){
//			sql +=" and score >= "+score;
//		}
		if(courseName!=null && !"".equals(courseName) && !"null".equals(courseName)&& courseName!="null"){
			sql+=" and course_name like '%"+courseName+"%'";
		}
		if(title!=null && !"".equals(title) && !"null".equals(title)&& title!="null"){
			sql+=" and title like '%"+title+"%'";
		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
		sql +=" order by employee_count desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select DISTINCT examId as id,course_name,title,number,exam_count,employee_count ", sql).getList();

	}
	public Long findAppCount(String courseName,String title){
		String sql = "select count(*) from (select DISTINCT course_name,title,number,exam_count,employee_count from v_business_ticket) as a where 1=1";
//		if(score != 0){
//			sql +=" and score >="+score;
//		}
		if(courseName!=null && !"".equals(courseName) && !"null".equals(courseName)&& courseName!="null"){
			sql+=" and a.course_name like '%"+courseName+"%'";
		}
		if(title!=null && !"".equals(title) && !"null".equals(title)&&title!="null"){
			sql+=" and a.title like '%"+title+"%'";
		}
		return Db.queryLong(sql);
	}
	
	public List<Record> findAppEmployeeListByExamId(int page,Long id){
		String sql="from v_business_ticket where 1=1";
		if(id != 0){
			sql +=" and examId = "+id;
		}
//		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//			sql+=" and c.title like '%"+parentName+"%'";
//		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
		sql +=" order by employee_count desc";
		return Db.paginate(page, Result.PAGE_COUNT, "SELECT DISTINCT examId,employeeId,`name`,title,mobile ", sql).getList();

	}
	public Long findAppEmployeeCountByExamId(Long id){
		String sql = "select count(*) from (SELECT DISTINCT examId,employeeId,`name`,title,mobile from v_business_ticket) as a where 1=1";
		if(id != 0){
			sql +=" and a.examId ="+id;
		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
//		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//			sql+=" and c.title like '%"+parentName+"%'";
//		}
		return Db.queryLong(sql);
	}
	//查询最高分
	public List<Record> findAppEmployeeMaxScoreByExamId(Long id,Integer employeeId){
		String sql="SELECT examId,employeeId,`name`,title,mobile,score from v_business_ticket where 1=1";
		if(id != 0){
			sql +=" and examId = "+id;
		}
		if(employeeId !=0){
			sql +=" and employeeId = "+employeeId;
		}
//		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//			sql+=" and c.title like '%"+parentName+"%'";
//		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
		sql +=" order by score desc";
		return Db.find(sql);
	}
	//查询最低分
	public List<Record> findAppEmployeeMinScoreByExamId(Long id,Integer employeeId){
		String sql="SELECT examId,employeeId,`name`,title,mobile,score from v_business_ticket where 1=1";
		if(id != 0){
			sql +=" and examId = "+id;
		}
		if(employeeId !=0){
			sql +=" and employeeId = "+employeeId;
		}
//		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//			sql+=" and c.title like '%"+parentName+"%'";
//		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
		sql +=" order by score";
		return Db.find(sql);
	}
	//考试次数
	public Long findAppEmployeeExamCountByExamId(Long id,Integer employeeId){
		String sql = "select count(*) from v_business_ticket as a where 1=1";
		if(id != 0){
			sql +=" and a.examId ="+id;
		}
		if(employeeId !=0){
			sql +=" and a.employeeId = "+employeeId;
		}
//		if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//			sql+=" and d.title like '%"+topParentName+"%'";
//		}
//		if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//			sql+=" and c.title like '%"+parentName+"%'";
//		}
		return Db.queryLong(sql);
	}
	
	//查询学员答题明细
		public List<Record> findAppEmployeeExamDetailList(int page,Long employeeId,String courseName){
			String sql=" from v_business_ticket where 1=1";
//			if(id != 0){
//				sql +=" and examId = "+id;
//			}
			if(employeeId !=0){
				sql +=" and employeeId = "+employeeId;
			}
			if(!StringUtils.isNullOrEmpty(courseName) && !"null".equals(courseName)){
				sql +=" and course_name like '%"+courseName+"%' ";
			}
//			if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//				sql+=" and c.title like '%"+parentName+"%'";
//			}
//			if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//				sql+=" and d.title like '%"+topParentName+"%'";
//			}
			sql +=" order by create_at desc";
			return Db.paginate(page, Result.PAGE_COUNT, "SELECT id,examId,employeeId,`name`,title,mobile,score,course_name,create_at ", sql).getList();
		}
		//考试次数
		public Long findAppEmployeeExamDetailCount(Long employeeId,String courseName){
			String sql = "select count(*) from v_business_ticket as a where 1=1";
//			if(id != 0){
//				sql +=" and a.examId ="+id;
//			}
			if(employeeId !=0){
				sql +=" and a.employeeId = "+employeeId;
			}
			if(!StringUtils.isNullOrEmpty(courseName) && !"null".equals(courseName) ){
				sql +=" and a.course_name like '%"+courseName+"%' ";
			}
//			if(topParentName!=null && !"".equals(topParentName) && !"null".equals(topParentName)&& topParentName!="null"){
//				sql+=" and d.title like '%"+topParentName+"%'";
//			}
//			if(parentName!=null && !"".equals(parentName) && !"null".equals(parentName)&& parentName!="null"){
//				sql+=" and c.title like '%"+parentName+"%'";
//			}
			return Db.queryLong(sql);
		}
}
