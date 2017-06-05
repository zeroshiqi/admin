package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

public class StudentService {

	public List<Record> findStudentList(int page) {
		return Db.paginate(page, Result.PAGE_COUNT, "select id,name,title,industry,job,content,cover,city,weight ", " from t_student where status = 1 order by weight desc").getList();
	}

	public Long findStudentCount() {
		return Db.queryLong("select count(*) from t_student where status = 1 ");
	}

	public void deleteStudent(Long id) {
		Db.update("update t_student set status = 0,update_at = now() where id = ?", id);
	}

	public Record findStudentById(Long id) {
		return Db.findById("t_student", id);
	}
	
	public void saveStudent(Record record){
		Db.save("t_student", record);
	}
	
	public void updateStudent(Record record){
		Db.update("t_student", record);
	}
	
	public List<Record> findTeacherInvite(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select * ", "from t_teacher_invite order by id desc").getList();
	}
	
	public Long findTeacherInviteCount(){
		return Db.queryLong("select count(*) from t_teacher_invite");
	}
}
