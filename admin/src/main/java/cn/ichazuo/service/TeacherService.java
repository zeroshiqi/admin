package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

public class TeacherService {

	public List<Record> findTeacherList(int page) {
		return Db.paginate(page, Result.PAGE_COUNT, "select id,name,title,type,price,info,content,avatar,weight,app_hidden,web_hidden,is_hidden ", " from t_teacher where status = 1 order by weight desc").getList();
	}

	public Long findTeacherCount() {
		return Db.queryLong("select count(*) from t_teacher where status = 1 ");
	}
	/**
	 * @Title: findAllTeacher 
	 * @Description: (查询所有讲师) 
	 * @return
	 */
	public List<Record> findAllTeacher(String name){
		return Db.find("select id,name from t_teacher where status = 1 and name like ?","%"+name+"%");
	}

	public void deleteTeacher(Long id) {
		Db.update("update t_teacher set status = 0,version = version + 1,update_at = now() where id = ?", id);
	}

	public Record findTeacherById(Long id) {
		return Db.findById("t_teacher", id);
	}
	
	public void saveTeacher(Record record){
		Db.save("t_teacher", record);
	}
	
	public void updateTeacher(Record record){
		Db.update("t_teacher", record);
	}
	
	public List<Record> findTeacherInvite(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select * ", "from t_teacher_invite order by id desc").getList();
	}
	//根据名称内容模糊搜索课程包
	public List<Record> findCatalog(String title){ 
		return Db.find("SELECT o.id,o.name FROM t_business_catalog o WHERE 1 = 1 AND o. STATUS = 1 and type=1 and o.name like ?","%"+title+"%");
	}
	
	public Long findTeacherInviteCount(){
		return Db.queryLong("select count(*) from t_teacher_invite");
	}
}
