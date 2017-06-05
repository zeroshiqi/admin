package cn.ichazuo.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;

public class ImageService {
	
	public List<Record> findImage(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select id,url,image_url,title,create_at", " from t_image where status = 1").getList();
	}
	
	public Long findImageCount(){
		return Db.queryLong("select count(*) from t_image where status = 1");
	}
	
	public void updateImage(Long id){
		Db.update("update t_image set status = 0,update_at = now(),version = version + 1 where id = ?" ,id);
	}
	
	public Record findImageById(Long id){
		return Db.findById("t_image", id);
	}
	
	public void saveImage(Record record){
		Db.save("t_image", record);
	}
	
	public void updateImageInfo(Record record){
		Db.update("t_image", record);
	}
}
