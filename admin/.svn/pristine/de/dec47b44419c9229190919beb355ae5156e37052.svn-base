package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

public class JobService {
	
	public Long findJobCount(String job){
		return Db.queryLong("select count(*) from t_job where status = 1 and job_name like ?","%"+job+"%");
	}
	
	public List<Record> findAllType(){
		return Db.find("select i.id,i.value from s_dict_item i left join s_dict s on s.id = i.dict_id where s.`code` = 'JOBTYPE' and i.status = 1 ");
	}
	
	public List<Record> findJobList(int page,String job){
		return Db.paginate(page, Result.PAGE_COUNT, "select j.id,j.company,j.cover,j.create_at,j.job_name,j.pay,j.tag,i.`value`", " from t_job j left join s_dict_item i on i.id  = j.type_id where j.status = 1 and j.job_name like ?","%"+job+"%").getList();
	}
	
	public Long findTypeCount() {
		return Db.queryLong(
				"select count(*) from s_dict_item i left join s_dict d on d.id = i.dict_id where d.`code` = ?  and i.status = 1",
				"JOBTYPE");
	}

	public List<Record> findTypeList(int page) {
		return Db.paginate(page, Result.PAGE_COUNT, "select i.id,i.`value`,i.weight,i.create_at", "from s_dict_item i left join s_dict d on d.id = i.dict_id where d.`code` = ? and i.status = 1" + " order by i.weight desc", "JOBTYPE").getList();
	}
	
	public Record findJob(long id){
		return Db.findById("t_job", id);
	}
	
	public void deleteJob(Long id) {
		Db.update("update t_job set status = 0 where id = ?", id);
	}

	public Record findType(Long id) {
		return Db.findById("s_dict_item", id);
	}

	public Long findDictId() {
		return Db.queryLong("select id from s_dict where code = 'JOBTYPE'");
	}

	public void deleteType(Long id) {
		Db.update("update s_dict_item set status = 0 where id = ?", id);
	}

	public void saveType(Record record) {
		Db.save("s_dict_item", record);
	}

	public void updateType(Record record) {
		Db.update("s_dict_item", record);
	}
	
	public void saveJob(Record record){
		Db.save("t_job", record);
	}
	
	public void updateJob(Record record){
		Db.update("t_job",record);
	}
	
	public long findUseCount(long typeid){
		return Db.queryLong("select count(*) from t_job where type_id = ? and status = 1",typeid);
	}
}
