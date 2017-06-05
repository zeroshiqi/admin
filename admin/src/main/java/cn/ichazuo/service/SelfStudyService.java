package cn.ichazuo.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.model.SelfStudy;
import cn.ichazuo.model.SelfStudy;

/**
 * @ClassName: SelfStudyService 
 * @Description: (文章Service) 
 * @author ZhaoXu
 * @date 2015年8月4日 下午6:52:27 
 * @version V1.0
 */
public class SelfStudyService {
	
	/**
	 * @Title: findSelfStudyList 
	 * @Description: (查询文章列表) 
	 * @param page
	 * @param title
	 * @return
	 */
//	public List<SelfStudy> findSelfStudyList(int page,String title){
//		String sql = " from t_article a where a.status = 1";
//		if(!StringUtils.isNullOrEmpty(title)){
//			sql += " and a.title like '%"+title+"%'";
//		}
//		sql += " order by a.id desc";
//		return SelfStudy.dao.paginate(page, Result.PAGE_COUNT, "select *", sql,title).getList();
//	}
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findSelfStudyList(int page,String title){
		String sql = " from t_self_study a where a.status = 1";
		if(!StringUtils.isNullOrEmpty(title)){
			sql += " and a.title like '%"+title+"%'";
		}
		sql += " order by a.id desc";
//		return Db.paginate(page, Result.PAGE_COUNT, "select o.id, o.name,o.sex,o.position,o.mobile,o.mailbox,o.business_id,o.business_name,o.status ", sql).getList();
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.subtitle,a.title,a.cover,a.level,a.type,a.url,a.tag,a.synopsis,a.show_type,a.is_hidden ", sql).getList();
	}
	/**
	 * @Title: findSelfStudyCount 
	 * @Description: (查询文章总数) 
	 * @param title
	 * @return
	 */
	public Long findSelfStudyCount(String title){
		String sql = "select count(*) from t_self_study a where a.status = 1";
		if(!StringUtils.isNullOrEmpty(title)){
			sql += " and a.title like '%"+title+"%'";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: findSelfStudyById 
	 * @Description: (根据id查询文章) 
	 * @param id
	 * @return
	 */
	public SelfStudy findSelfStudyById(Long id){
		return SelfStudy.dao.findById(id);
	}
	
	/**
	 * @Title: updateSelfStudy 
	 * @Description: (修改文章) 
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public SelfStudy updateSelfStudy(SelfStudy article) throws Exception{
		if(article.update()){
			return article;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveSelfStudy 
	 * @Description: (保存文章) 
	 * @param article
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public SelfStudy saveSelfStudy(SelfStudy article) throws Exception{
		if(article.save()){
			return article;
		}else{
			throw new Exception();
		}
	}
	/**
	 * @Title: find 
	 * @Description: (根据id查询企业详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findSelfStudy(Long id){
		return Db.findById("t_self_study", id);
	}
	
	//新增企业用户
	public void saveSelfStudy(Record record){
		Db.save("t_self_study", record);
	}
	//更新企业用户
	public void updateEmployee(Record record){
		Db.update("t_self_study", record);
	}
}
