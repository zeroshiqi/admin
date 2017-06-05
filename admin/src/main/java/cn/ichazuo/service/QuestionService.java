package cn.ichazuo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;
import cn.ichazuo.model.Question;
import cn.ichazuo.model.QuestionInfo;

public class QuestionService {
	
	public List<Record> findQuestionList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select id,title,a,b,c,d,answer,user_name,type,parent_name,parent_id ", "from `t_question` where status = 1 order by id desc").getList();
	}
	
	public long findQuestionCount(){
		return Db.queryLong("select count(*) from `t_question` where status = 1");
	}
	
	public void delete(Long id){
		Db.update("update t_question set status = 0 where id = ?", id);
	}
	
	public void save(Record record){
		Db.save("t_question", record);
	}
	
	public void update(Record record){
		Db.update("t_question", record);
	}
	
	public Record find(Long id){
		return Db.findById("t_question", id);
	}
	//查询题目一级分类总条数
	public long findQuestionTypeOneCount(){
		return Db.queryLong("select count(*) from `t_question_first_type` where status = 1");
	}
	//查询题目一级分类列表
	public List<Record> findQuestionTypeOneList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.title,a.create_at,((select count(*) from t_ticket b join t_question_first_type c on b.top_parent_id = c.id where b.top_parent_id = a.id and b.status =1 and c.status=1)+0) as ticketCount ", "from `t_question_first_type` a where a.status = 1 order by a.id desc").getList();
	}
	/**
	 * @Title: findAllMember 
	 * @Description: (查询所有注册用户) 
	 * @return
	 */
	public List<Record> findAllMember(String name){
		return Db.find("select id,nick_name as nickName from t_member where status = 1 and nick_name like ?","%"+name+"%");
	}
	//新增问题一级目录
	public void saveFirstType(Record record){
		Db.save("t_question_first_type", record);
	}
	//更新问题一级目录
	public void updateFirstType(Record record){
		Db.update("t_question_first_type", record);
	}
	//根据Id查询问题一级目录
	public Record findFirstTypeById(Long id){
		return Db.findById("t_question_first_type", id);
	}
	//根据Id删除问题一级目录
	public void deleteFistTypeById(Long id){
		Db.update("update t_question_first_type set status = 0 where id = ?", id);
	}
	//根据title内容模糊搜索一级目录
	public List<Record> findFirstType(String title){
		return Db.find("select id,title from t_question_first_type where status = 1 and title like ?","%"+title+"%");
	}
	//根据id修改被修改一级目录下二级目录的父目录名称
	public void updateSecondTypeByParentId(Long id,String title){
		Db.update("update t_question_second_type set parent_name = ? where parent_id = ?",title,id);
	}
	
	//********************************************************************************
	//************         以下是操作问题二级目录的方法                          *********************************
	//********************************************************************************
	
	//查询题目二级分类总条数
	public long findQuestionTypeTwoCount(String name){
		if(name!=null && !"".equals(name) && !"null".equals(name)&& name!="null"){
			return Db.queryLong("select count(*) from `t_question_second_type` where status = 1 and title like ?","%"+name+"%");
		}else{
			return Db.queryLong("select count(*) from `t_question_second_type` where status = 1 ");
		}
	}
	//查询题目二级分类列表
	public List<Record> findQuestionTypeTwoList(int page,String name){
		String sql = "from `t_question_second_type` a where a.status = 1 ";
		if(name!=null && !"".equals(name) && !"null".equals(name)&& name!="null"){
			sql+=" and a.title like '%"+name+"%'";
		}
		sql += " order by id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.title,a.parent_id,a.parent_name,a.create_at,a.number,((select count(*) from t_ticket b join t_question_second_type c on b.parent_id = c.id where b.parent_id = a.id and b.status =1 and c.status=1)+0) as ticketCount ", sql).getList();
	}
	//新增问题二级目录
	public void saveSecondType(Record record){
		Db.save("t_question_second_type", record);
	}
	//根据Id删除问题二级目录
	public void deleteSecondTypeById(Long id){
		Db.update("update t_question_second_type set status = 0 where id = ?", id);
	}
	//根据Id查询问题二级目录
	public Record findSecondTypeById(Long id){
		return Db.findById("t_question_second_type", id);
	}
	//更新问题二级目录
	public void updateSecondType(Record record){
		Db.update("t_question_second_type", record);
	}
	//根据title内容模糊搜索一级目录
	public List<Record> findSecondType(String title){
		return Db.find("select id,title from t_question_second_type where status = 1 and title like ? order by id desc","%"+title+"%");
	}
	//根据id修改被修改一级目录下二级目录的父目录名称
	public void updateQuestionByParentId(Long id,String title){
		Db.update("update t_question set parent_name = ? where parent_id = ?",title,id);
	}
	
	//根据parentId查询课程总数
	public long findQuestionCountByParentId(Long id){
		return Db.queryLong("select count(*) from `t_question` where status = 1 and parent_id = ?",id);
	}
	//根据parentId查询课程
	public List<Record> findQuestionListByParentId(int page,Long id){
		return Db.paginate(page, Result.PAGE_COUNT, "select id,title,a,b,c,d,answer,user_name,type,parent_name,parent_id ", "from `t_question` where status = 1 and parent_id = ? order by id desc",id).getList();
	}
	public List<Record> findAllQuestionByParentId(Long id){
		String sql="select * from t_question where status = 1 and parent_id = "+id+" order by id asc";
		return Db.find(sql);
	}
	
	//根据parentId查询二级目录总数
	public long findSecondTypeCountByParentId(Long id){
		return Db.queryLong("select count(*) from `t_question_second_type` where status = 1 and parent_id = ?",id);
	}
	//根据parentId查询二级目录
	public List<Record> findSecondTypeListByParentId(int page,Long id){
		return Db.paginate(page, Result.PAGE_COUNT, "select id,title,parent_id,parent_name,create_at ", "from `t_question_second_type` where status = 1 and parent_id = ? order by id desc",id).getList();
	}
	/**
	 * @Title: saveQuestions 
	 * @Description: (保存Excel导入的内容到数据库) 
	 * @param question
	 */
	public void saveQuestions(List<Record> questionList){
		for(int i=0;i<questionList.size();i++){
			Record record = questionList.get(i);
			Db.save("t_question", record);
		}
	}
}
