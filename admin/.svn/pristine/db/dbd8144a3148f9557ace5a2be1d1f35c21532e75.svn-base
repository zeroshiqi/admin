package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

public class TypeService {

	public Long findTypeCount() {
		return Db.queryLong(
				"select count(*) from s_dict_item i left join s_dict d on d.id = i.dict_id where d.`code` = ?  and i.status = 1",
				"READTYPE");
	}

	public List<Record> findTypeList(int page) {
		return Db.paginate(page, Result.PAGE_COUNT, "select i.id,i.`value`,i.cover,i.create_at,i.weight", "from s_dict_item i left join s_dict d on d.id = i.dict_id where d.`code` = ? and i.status = 1" + " order by i.weight desc", "READTYPE").getList();
	}
	
	public Long findArticleCount(Long id){
		return Db.queryLong("select count(*) from t_article where type = ? and `status` = 1",id);
	}
	
	public void deleteType(Long id){
		Db.update("update s_dict_item set status = 0 where id = ?",id);
	}
	
	public Record findType(Long id){
		return Db.findById("s_dict_item", id);
	}
	
	public List<Record> findAllArticle(){
		return Db.find("select id,title from t_article where status = 1 and show_type = 1");
	}
	
	public Long findDictId(){
		return Db.queryLong("select id from s_dict where code = 'READTYPE'");
	}
	
	public void saveType(Record record){
		Db.save("s_dict_item", record);
	}
	
	public void updateType(Record record){
		Db.update("s_dict_item", record);
	}
}
