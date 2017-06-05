package cn.ichazuo.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.mchange.lang.LongUtils;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.model.Course;
import cn.ichazuo.model.OfflineCourse;
import cn.ichazuo.model.OfflineCourseImage;
import cn.ichazuo.model.OnlineCourse;

/**
 * @ClassName: CourseService 
 * @Description: (企业Service) 
 * @author LiDongYang
 * @date 2016-5-6 15:20:44 
 * @version V1.0
 */
public class BusinessService {
	
	/**
	 * @Title: findBusinessList 
	 * @Description: (查询全部企业) 
	 * @return
	 */
	public List<Record> findBusinessList(){
		return Db.find("select * from t_business where status=1 or status=2 order by status,id");
	}
	
	/**
	 * @Title: delete 
	 * @Description: (删除企业) 
	 * @param id 
	 */
	public void delete(Long id){
		Db.update("update t_business set status = 0 where id = ?", id);
	}
	
	/**
	 * @Title: deleteEmployee 
	 * @Description: (删除企业用户) 
	 * @param id 
	 */
	public void deleteEmployee(Long id){
		Db.update("update t_business_employee set status = 0 where id = ?", id);
	}
	
	/**
	 * @Title: abate 
	 * @Description: (使企业失效) 
	 * @param id 
	 */
	public void abate(Long id){
		Db.update("update t_business set status = 2 where id = ?", id);
	}
	
	/**
	 * @Title: alive 
	 * @Description: (使企业生效) 
	 * @param id 
	 */
	public void alive(Long id){
		Db.update("update t_business set status = 1 where id = ?", id);
	}
	
	/**
	 * @Title: aliveEmployeeBy 
	 * @Description: (使企业用户生效) 
	 * @param id 
	 */
	public void aliveEmployee(Long id){
		Db.update("update t_business_employee set status = 1 where business_id = ? and status=2", id);
	}
	/**
	 * @Title: aliveEmployeeById
	 * @Description: (使企业用户生效) 
	 * @param id 
	 */
	public void aliveEmployeeById(Long id){
		Db.update("update t_business_employee set status = 1 where id = ? and status=2", id);
	}
	
	/**
	 * @Title: abateEmployeeById 
	 * @Description: (使企业用户失效) 
	 * @param id 
	 */
	public void abateEmployeeById(Long id){
		Db.update("update t_business_employee set status = 2 where id = ? and status=1", id);
	}
	
	/**
	 * @Title: abateEmployee 
	 * @Description: (企业用户失效) 
	 * @param id 
	 */
	public void abateEmployee(Long id){
		Db.update("update t_business_employee set status = 2 where business_id = ? and status=1", id);
	}
	
	/**
	 * @Title: deleteKeywords 
	 * @Description: (删除关键字) 
	 * @param id 
	 */
	public void deleteKeywords(Long id){
		Db.update("update t_business_keywords set status = 0 where id = ?", id);
	}
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findBusinessList(int page,String name){
		String sql = "from t_business as o where (o.`status` =1 or o.`status` =2)";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and o.business_name like '%"+name+"%'";
		}
		sql += " order by o.status,o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id,o.status, o.business_name,o.business_level,o.business_address,o.business_nature,o.business_scale,(select count(*) from t_business_employee as a where (a.`status` = 1 or a.status=2)  and a.business_id=o.id) as employeeCount", sql).getList();
	}
	
	/**
	 * @Title: findBusinessCount 
	 * @Description: (查询企业数量) 
	 * @return
	 */
	public long findBusinessCount(String name){
		String sql = "select count(*) from t_business t where (t.status=2 or t.status=1)";
		if(!StringUtils.isNullOrEmpty(name)){ 
			sql += " and t.business_name like '%"+name+"%'";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: find 
	 * @Description: (根据id查询企业详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record find(Long id){
		return Db.findById("t_business", id);
	}
	
	/**
	 * @Title: find 
	 * @Description: (根据id查询企业详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findEmployee(Long id){
		return Db.findById("t_business_employee", id);
	}
	
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findEmployeeList(int page,int id,String mobile){
		String sql = "from t_business_employee as o where (o.`status` = 1 or o.`status` = 2)";
		if(id!=0){
			sql += " and o.business_id ="+id;
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and o.mobile like '%"+mobile+"%'";
		}
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id, o.name,o.sex,o.position,o.mobile,o.mailbox,o.business_id,o.business_name,o.status ", sql).getList();
	}
	
	public List<Record> findAllEmployeeList(int page,String mobile,String name){
		String sql = "from t_business_employee as o where (o.`status` = 1 or o.`status` = 2)";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and o.mobile like '%"+mobile+"%'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and o.name like '%"+name+"%'";
		}
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id, o.name,o.sex,o.position,o.mobile,o.mailbox,o.business_id,o.business_name,o.status ", sql).getList();
	}
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findAllEmployeeListByCompany(Long id){
		String sql = "select o.id, o.name,o.sex,o.position,o.mobile,o.mailbox,o.business_id,o.business_name,o.status from t_business_employee as o where o.`status` = 1";
		if(id!=0){
			sql += " and o.business_id ="+id;
		}
		return Db.find(sql);
	}
	public List<Record> findMemberRecordList(String employeeId,Long catalogId){
		String sql = null;
		if(catalogId==0){
			sql = "select * from t_business_member_record where employee_id="+employeeId+" and type=0 and status=1";
		}else{
			sql = "select * from t_business_member_record where employee_id="+employeeId+" and type=1 and catalog_id="+catalogId+" and status=1";
		}
		return Db.find(sql);
	}
	
	/**
	 * @Title: findEmployeeCount 
	 * @Description: (查询企业用户数量) 
	 * @return
	 */
	public long findEmployeeCount(int id,String mobile){
		String sql = "select count(*) from t_business_employee as o where (o.`status` = 1 or o.`status` = 2)";
		if(id!=0){
			sql += " and o.business_id ="+id;
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and o.mobile like '%"+mobile+"%'";
		}
		return Db.queryLong(sql);
	}
	public long findAllEmployeeCount(String mobile,String name){
		String sql = "select count(*) from t_business_employee as o where (o.`status` = 1 or o.`status` = 2)";
		if(!StringUtils.isNullOrEmpty(mobile)){
			sql += " and o.mobile like '%"+mobile+"%'";
		}
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and o.name like '%"+name+"%'";
		}
		return Db.queryLong(sql);
	}
	//根据名称内容模糊搜索企业
	public List<Record> findCompany(String title){
		return Db.find("select id,business_name from t_business where (status = 1 or status = 2) and business_name like ?","%"+title+"%");
	}
	//新增企业用户
	public void saveEmployee(Record record){
		Db.save("t_business_employee", record);
	}
	//更新企业用户
	public void updateEmployee(Record record){
		Db.update("t_business_employee", record);
	}
	//新增企业
	public void save(Record record){
		Db.save("t_business", record);
	}
	//更新企业信息
	public void update(Record record){
		Db.update("t_business", record);
	}
	
	//新增企业
	public void saveKeywords(Record record){
		Db.save("t_business_keywords", record);
	}
	//更新企业信息
	public void updateKeywords(Record record){
		Db.update("t_business_keywords", record);
	}
	//新增企业
	public void saveMemberRecord(Record record){
		Db.save("t_business_member_record", record);
	}
	/**
	 * @Title: findKeywordsList 
	 * @Description: (查询全部企业课程搜索关键字) 
	 * @return
	 */
	public List<Record> findKeywordsList(int page,String name){
		String sql = "from t_business_keywords as o where o.status = 1 ";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and o.keywords like '%"+name+"%' ";
		}
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.id, o.keywords,o.create_at,o.update_at ", sql).getList();
	}
	/**
	 * @Title: findBusinessCount 
	 * @Description: (查询企业数量) 
	 * @return
	 */
	public long findKeywordsCount(String name){
		String sql = "select count(*) from t_business_keywords t where t.status = 1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and o.keywords like '%"+name+"%' ";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: findKeyWords 
	 * @Description: (根据id查询关键字信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findKeyWords(Long id){
		return Db.findById("t_business_keywords", id);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////                 课程分类管理                                                      ///////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @Title: findCatalogList 
	 * @Description: (查询课程分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findCatalogList(int page,String name,String type){
		String sql = "from t_business_catalog as j where j.`status` = 1 and j.id !=1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and j.name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(type)){
			sql += " and j.type = '"+type+"'";
		}
		sql += " order by j.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select j.id,j.type,j.update_mark, j.name,j.flag,j.subtitle,j.create_at,j.weight,j.exam_name,(SELECT count(*)	FROM t_business_catalog_second a WHERE 1 = 1 AND a.`status` = 1 AND a.parent_id = j.id) as sonCount,(	SELECT count(*)	FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id	LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_catalog_course a ON a.course_id = t.id	WHERE	1 = 1	AND o. STATUS = 1	AND t.business_hidden = 0	AND a.`status` = 1	AND a.catalog_id = j.id) AS courseNum ", sql).getList();
	}
	/**
	 * @Title: findCatalogList 
	 * @Description: (查询课程分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findCatalogList(String name){
		String sql = " from t_business_catalog as j where j.`status` = 1 and j.id !=1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and j.name like '%"+name+"%'";
		}
		sql += " order by j.weight desc";
		return Db.find("select j.id, j.name,j.subtitle,j.create_at,j.weight,j.exam_name,(SELECT count(*) FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_catalog_course a ON a.course_id = t.id WHERE	1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 AND a.`status` = 1 AND a.catalog_id = j.id) as courseCount "+sql);
	}
	/**
	 * @Title: findSecondCatalogList 
	 * @Description: (查询课程二级分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findSecondCatalogList(int page,String name,String parentName,Long parentId){
		String sql = "from t_business_catalog_second as j JOIN t_business_catalog b ON b.id = j.parent_id where j.`status` = 1 and b.status = 1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and j.name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(parentName)){
			sql += " and b.name like '%"+parentName+"%'";
		}
		if(!NumberUtils.isNullOrZero(parentId)){
			sql += " and b.id = '"+parentId+"'";
		}
		sql += " order by j.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select j.id, j.name,j.subtitle,j.create_at,j.weight,j.exam_name,b.`name` as parentName,b.id as parentId,(SELECT count(*) FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_second_catalog_course a ON a.course_id = t.id WHERE	1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 AND a.`status` = 1 AND a.catalog_id = j.id) as courseCount", sql).getList();
	}
	/**
	 * @Title: findSecondCatalogCount 
	 * @Description: (查询课程二级分类数量)
	 * @return
	 */
	public long findSecondCatalogCount(String name,String parentName,Long parentId){
		String sql = "select count(*) from t_business_catalog_second t JOIN t_business_catalog b ON b.id = t.parent_id where t.status = 1 and b.status = 1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and t.name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(parentName)){
			sql += " and b.name like '%"+parentName+"%'";
		}
		if(!NumberUtils.isNullOrZero(parentId)){
			sql += " and b.id = '"+parentId+"'";
		}
		return Db.queryLong(sql);
	}
	/**
	 * @Title: findCatalogCount 
	 * @Description: (查询企业数量) 
	 * @return
	 */
	public long findCatalogCount(String name,String type){
		String sql = "select count(*) from t_business_catalog t where t.status = 1 and t.id !=1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and t.name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(type)){
			sql += " and t.type = '"+type+"'";
		}
		return Db.queryLong(sql);
	}
	
	//新增课程分类
	public void saveCatalog(Record record){
		Db.save("t_business_catalog", record);
	}
	//更新课程分类
	public void updateCatalog(Record record){
		Db.update("t_business_catalog", record);
	}
	//新增课程分类
	public void saveSecondCatalog(Record record){
		Db.save("t_business_catalog_second", record);
	}
	//更新课程分类
	public void updateSecondCatalog(Record record){
		Db.update("t_business_catalog_second", record);
	}
	/**
	 * @Title: findCatalog 
	 * @Description: (根据id查询课程分类详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Record> findCatalog(int page,Long id){
		String sql = "from t_business_catalog as j where j.id =1";
		sql += " order by j.weight desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select j.id,j.type, j.name,j.flag,j.subtitle,j.create_at,j.weight,j.exam_name,(SELECT count(*)	FROM t_business_catalog_second a WHERE 1 = 1 AND a.`status` = 1 AND a.parent_id = j.id) as sonCount,(	SELECT count(*)	FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id	LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_catalog_course a ON a.course_id = t.id	WHERE	1 = 1	AND o. STATUS = 1	AND t.business_hidden = 0	AND a.`status` = 1	AND a.catalog_id = j.id) AS courseNum ", sql).getList();
	}
	/**
	 * @Title: findCatalog 
	 * @Description: (根据id查询课程分类详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findSecondCatalog(Long id){
		return Db.findById("t_business_catalog_second", id);
	}
	/**
	 * @Title: findCatalog 
	 * @Description: (根据id查询课程分类详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findCatalogCourse(Long id){
		return Db.findById("t_business_catalog_course", id);
	}
	/**
	 * @Title: findSecondCatalogCourse 
	 * @Description: (根据id查询课程分类详细信息) 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findSecondCatalogCourse(Long id){
		return Db.findById("t_business_second_catalog_course", id);
	}
	/**
	 * @Title: deleteCatalog 
	 * @Description: (删除课程分类) 
	 * @param id 
	 */
	public void deleteCatalog(Long id){
		Db.update("update t_business_catalog set status = 0 where id = ?", id);
	}
	/**
	 * @Title: deleteSecondCatalog 
	 * @Description: (删除课程二级分类) 
	 * @param id 
	 */
	public void deleteSecondCatalog(Long id){
		Db.update("update t_business_catalog_second set status = 0 where id = ?", id);
	}
	
	/**
	 * @Title: findCatalogList 
	 * @Description: (查询课程分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findCourseList(int page,Long catalogId){
		String sql = "FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_catalog_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1 ";
		if(catalogId!=0){
			sql += " and a.catalog_id ="+catalogId;
		}
		sql += " order by a.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.catalog_id, a.course_id,t.course_name,o.create_at,a.weight,a.exam_name ", sql).getList();
	}
	
	/**
	 * @Title: findCatalogCourseList 
	 * @Description: (查询课程分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findCatalogCourseList(Long catalogId,Long courseId){
		String sql = "from t_business_catalog_course as o where o.`status` = 1 ";
		if(catalogId!=0){
			sql += " and o.catalog_id ="+catalogId;
		}
		if(courseId!=0){
			sql += " and o.course_id ="+courseId;
		}
		sql += " order by o.id desc";
		return Db.paginate(1,Result.PAGE_COUNT, "select o.catalog_id, o.course_id,o.course_name,o.create_at", sql).getList();
	}
	/**
	 * @Title: findSecondCatalogCourseList 
	 * @Description: (查询课程分类列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findSecondCatalogCourseList(Long catalogId,Long courseId){
		String sql = "from t_business_second_catalog_course as o where o.`status` = 1 ";
		if(catalogId!=0){
			sql += " and o.catalog_id ="+catalogId;
		}
		if(courseId!=0){
			sql += " and o.course_id ="+courseId;
		}
		sql += " order by o.id desc";
		return Db.paginate(1,Result.PAGE_COUNT, "select o.catalog_id, o.course_id,o.course_name,o.create_at", sql).getList();
	}
	/**
	 * @Title: findCatalogCount 
	 * @Description: (查询企业数量) 
	 * @return
	 */
	public long findCourseCount(Long catalogId){
		String sql = "select count(*) FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_catalog_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1";
		if(catalogId!=0){
			sql += " and a.catalog_id ="+catalogId;
		}
		return Db.queryLong(sql);
	}
	
	//根据名称内容模糊搜索录音课程
	public List<Record> findOnlineCourse(String title){
		return Db.find("SELECT t.id,t.course_name FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and t.course_name like ?  order by t.id desc","%"+title+"%");
	}
	
	//根据名称内容模糊搜索录音课程
	public List<Record> findExamList(String title){ 
		return Db.find("SELECT o.id,o.title FROM t_question_second_type o WHERE 1 = 1 AND o. STATUS = 1 and o.title like ? order by o.id desc","%"+title+"%");
	}
	
	//根据名称内容模糊搜索课程包
	public List<Record> findCatalog(String title){ 
		return Db.find("SELECT o.id,o.name FROM t_business_catalog o WHERE 1 = 1 AND o. STATUS = 1 and o.name like ?","%"+title+"%");
	}
	//根据名称内容模糊搜索公开课
	public List<Record> findCatalogGroup(String title){ 
		return Db.find("SELECT o.id,o.name FROM t_haoduoke_catalog_group o WHERE 1 = 1 AND o. STATUS = 1 and o.name like ?","%"+title+"%");
	}
	
	//新增课程分类
	public void saveCourse(Record record){
		Db.save("t_business_catalog_course", record);
	}
	//新增课程分类
	public void saveSecondCourse(Record record){
		Db.save("t_business_second_catalog_course", record);
	}
	/**
	 * @Title: deleteCatalog 
	 * @Description: (删除课程分类) 
	 * @param id 
	 */
	public void deleteCourse(Long id){
		Db.update("update t_business_catalog_course set status = 0 where id = ?", id);
	}
	/**
	 * @Title: deleteCatalog 
	 * @Description: (删除课程分类) 
	 * @param id 
	 */
	public void deleteSecondCourse(Long id){
		Db.update("update t_business_second_catalog_course set status = 0 where id = ?", id);
	}
	/**
	 * @Title: updateWeight
	 * @Description: (删除课程分类) 
	 * @param id 
	 */
	public void updateWeight(Record record){
		Db.update("t_business_catalog_course", record);
	}
	/**
	 * @Title: updateSecondWeight
	 * @Description: (删除课程二级分类课程权重) 
	 * @param id 
	 */
	public void updateSecondWeight(Record record){
		Db.update("t_business_second_catalog_course", record);
	}
	/**
	 * @Title: saveCourseExam
	 * @Description: (修改课程下的试卷) 
	 * @param id 
	 */
	public void saveCourseExam(Record record){
//		Db.update("t_business_catalog_course", record);
		Db.update("t_course", record);
	}
	
	/**
	 * @Title: saveCatalogExam
	 * @Description: (修改课程下的试卷) 
	 * @param id 
	 */
	public void saveCatalogExam(Record record){
		Db.update("t_business_catalog", record);
	}
	
	/**
	 * @Title: saveSecondCatalogExam
	 * @Description: (修改课程二级分类下的试卷) 
	 * @param id 
	 */
	public void saveSecondCatalogExam(Record record){
		Db.update("t_business_catalog_second", record);
	}
	public Record findCatalog(Long id){
		return Db.findById("t_business_catalog", id);
	}
	//********************************************************************************
	//************         以下是操作企业APP课程一级目录的方法                          *********************************
	//********************************************************************************
	
//	public List<Record> findQuestionList(int page){
//		return Db.paginate(page, Result.PAGE_COUNT, "select id,title,a,b,c,d,answer,user_name,type,parent_name,parent_id ", "from `t_question` where status = 1 order by id desc").getList();
//	}
//	
//	public long findQuestionCount(){
//		return Db.queryLong("select count(*) from `t_question` where status = 1");
//	}
//	
//	public void delete(Long id){
//		Db.update("update t_question set status = 0 where id = ?", id);
//	}
//	
//	public void save(Record record){
//		Db.save("t_question", record);
//	}
//	
//	public void update(Record record){
//		Db.update("t_question", record);
//	}
//	
//	public Record find(Long id){
//		return Db.findById("t_question", id);
//	}
	//查询课程一级分类总条数
	public long findBusinessCourseTypeOneCount(){
		return Db.queryLong("select count(*) from `t_business_first_type` where status = 1");
	}
	//查询课程一级分类列表
	public List<Record> findBusinessCourseTypeOneList(int page){
		return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.name,a.create_at,a.weight,a.show_type,a.course_id,a.course_name,a.teacher_id,a.teacher_name,a.type_id,a.type_name ", "from `t_business_first_type` a where a.status = 1 order by a.id desc").getList();
	}
	//新增问题一级目录
	public void saveFirstType(Record record){
		Db.save("t_business_first_type", record);
	}
	//更新问题一级目录
	public void updateFirstType(Record record){
		Db.update("t_business_first_type", record);
	}
	//根据Id查询问题一级目录
	public Record findFirstTypeById(Long id){
		return Db.findById("t_business_first_type", id);
	}
	//根据Id删除课程一级分类
	public void deleteFistTypeById(Long id){
		Db.update("update t_business_first_type set status = 0 where id = ?", id);
	}
	//根据title内容模糊搜索一级目录
	public List<Record> findFirstType(String title){
		return Db.find("select id,name from t_business_first_type where status = 1 and name like ?","%"+title+"%");
	}
	//根据id修改被修改一级目录下二级目录的父目录名称
	public void updateSecondTypeByParentId(Long id,String title){
		Db.update("update t_business_second_type set parent_name = ? where parent_id = ?",title,id);
	}
	
	//********************************************************************************
		//************         以下是操作问题二级目录的方法                          *********************************
		//********************************************************************************
		
		//查询题目二级分类总条数
		public long findQuestionTypeTwoCount(){
			return Db.queryLong("select count(*) from `t_business_second_type` where status = 1");
		}
		//查询题目二级分类列表
		public List<Record> findQuestionTypeTwoList(int page){
			return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.name,a.parent_id,a.parent_name,a.create_at,a.weight,((select count(*) from t_ticket b join t_business_second_type c on b.parent_id = c.id where b.parent_id = a.id and b.status =1 and c.status=1)+0) as ticketCount ", "from `t_business_second_type` a where a.status = 1 order by id desc").getList();
		}
		//新增问题二级目录
		public void saveSecondType(Record record){
			Db.save("t_business_second_type", record);
		}
		//根据Id删除问题二级目录
		public void deleteSecondTypeById(Long id){
			Db.update("update t_business_second_type set status = 0 where id = ?", id);
		}
		//根据Id查询问题二级目录
		public Record findSecondTypeById(Long id){
			return Db.findById("t_business_second_type", id);
		}
		//更新问题二级目录
		public void updateSecondType(Record record){
			Db.update("t_business_second_type", record);
		}
		//根据title内容模糊搜索一级目录
		public List<Record> findSecondType(String title){
			return Db.find("select id,title from t_question_second_type where status = 1 and title like ?","%"+title+"%");
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
		
		//根据parentId查询二级目录总数
		public long findSecondTypeCountByParentId(Long id){
			return Db.queryLong("select count(*) from `t_business_second_type` where status = 1 and parent_id = ?",id);
		}
		//根据parentId查询二级目录
		public List<Record> findSecondTypeListByParentId(int page,Long id){
			return Db.paginate(page, Result.PAGE_COUNT, "select id,name,parent_id,parent_name,create_at,weight ", "from `t_business_second_type` where status = 1 and parent_id = ? order by id desc",id).getList();
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
		/**
		 * @Title: findSecondCatalogList 
		 * @Description: (查询课程分类列表) 
		 * @param page
		 * @return
		 */
		public List<Record> findSecondCourseList(int page,Long catalogId){
			String sql = "FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_second_catalog_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1 ";
			if(catalogId!=0){
				sql += " and a.catalog_id ="+catalogId;
			}
			sql += " order by a.id desc";
			return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.catalog_id, a.course_id,t.course_name,o.create_at,a.weight,a.exam_name ", sql).getList();
		}
		/**
		 * @Title: findSecondCourseCount 
		 * @Description: (查询课程数量) 
		 * @return
		 */
		public long findSecondCourseCount(Long catalogId){
			String sql = "select count(*) FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_second_catalog_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1";
			if(catalogId!=0){
				sql += " and a.catalog_id ="+catalogId;
			}
			return Db.queryLong(sql);
		}
		
		/**
		 * @Title: findCatalogList 
		 * @Description: (查询课程分类列表) 
		 * @param page
		 * @return
		 */
		public List<Record> findTypeCourseList(int page,Long parentId){
			String sql = "FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1 ";
			if(parentId!=0){
				sql += " and a.parent_id ="+parentId;
			}
			sql += " order by a.id desc";
			return Db.paginate(page, Result.PAGE_COUNT, "select a.id,a.parent_id, a.course_id,t.course_name,o.create_at ", sql).getList();
		}
		/**
		 * @Title: findCatalogCount 
		 * @Description: (查询企业数量) 
		 * @return
		 */
		public long findTypeCourseCount(Long parentId){
			String sql = "select count(*) FROM t_course_online o LEFT JOIN t_course t ON t.id = o.course_id LEFT JOIN t_play_address p ON p.id = o.play_address_id JOIN t_business_course a ON a.course_id = t.id WHERE 1 = 1 AND o. STATUS = 1 AND t.business_hidden = 0 and a.`status` = 1";
			if(parentId!=0){
				sql += " and a.parent_id ="+parentId;
			}
			return Db.queryLong(sql);
		}
		/**
		 * @Title: findCatalogCourseList 
		 * @Description: (查询课程分类列表) 
		 * @param page
		 * @return
		 */
		public List<Record> findTypeCourseList(Long catalogId,Long courseId){
			String sql = "from t_business_course as o where o.`status` = 1 ";
			if(catalogId!=0){
				sql += " and o.parent_id ="+catalogId;
			}
			if(courseId!=0){
				sql += " and o.course_id ="+courseId;
			}
			sql += " order by o.id desc";
			return Db.paginate(1,Result.PAGE_COUNT, "select o.parent_id, o.course_id,o.course_name,o.create_at", sql).getList();
		}
		//新增课程分类
		public void saveTypeCourse(Record record){
			Db.save("t_business_course", record);
		}
		
		/**
		 * @Title: deleteTypeCourse 
		 * @Description: (删除课程分类下课程) 
		 * @param id 
		 */
		public void deleteTypeCourse(Long id){
			Db.update("update t_business_course set status = 0 where id = ?", id);
		}
		/**
		 * @Title: findCatalog 
		 * @Description: (根据id查询课程分类详细信息) 
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public Record findTypeCourse(Long id){
			return Db.findById("t_business_course", id);
		}
		/**
		 * @Title: findAllTeachers 
		 * @Description: (查询所有注册用户) 
		 * @return
		 */
		public List<Record> findAllTypes(String id,String name){
			return Db.find("select id,name from t_business_second_type where status = 1 and parent_id='"+ id+"' and name like ?","%"+name+"%");
		}
		/**
		 * @Title: updateWeight
		 * @Description: (删除课程分类) 
		 * @param id 
		 */
		public void updateTypeWeight(Record record){
			Db.update("t_business_course", record);
		}
		/**
		 * @Title: queryOnlineTypeList
		 * @Description: (查询录音课程分类列表) 
		 * @param id 
		 */
		public List<Record> queryOnlineTypeList(int page){
			String sql = "from v_online_type ";
			sql += " order by weight desc";
			return Db.paginate(page,Result.PAGE_COUNT, "select id,value,dict_id,weight,remark", sql).getList();
		}
		/**
		 * @Title: findBusinessCount 
		 * @Description: (查询企业数量) 
		 * @return
		 */
		public long findOnlineTypeCount(){
			String sql = "select count(*) from v_online_type t";
			return Db.queryLong(sql);
		}
		/**
		 * @Title: find 
		 * @Description: (根据id查询企业详细信息) 
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public Record findOnlineType(Long id){
			return Db.findById("s_dict_item", id);
		}
		//新增录音课程分类
		public void saveOnlineType(Record record){
			Db.save("s_dict_item", record);
		}
		//更新录音课程分类
		public void updateOnlineType(Record record){
			Db.update("s_dict_item", record);
		}
		/**
		 * @Title: delete 
		 * @Description: (删除录音课程分类) 
		 * @param id 
		 */
		public void deleteOnlineType(Long id){
			Db.update("update s_dict_item set status = 0 where id = ?", id);
		}
		
		/**
		 * @Title: queryOnlineTypeList
		 * @Description: (查询录音课程分类列表) 
		 * @param id 
		 */
		public List<Record> queryBannerList(int page){
			String sql = "from t_business_banner where status=1";
			sql += " order by weight desc";
			return Db.paginate(page,Result.PAGE_COUNT, "select id,name,cover,weight,show_type,create_at,teacher_name,course_name,type_name", sql).getList();
		}
		/**
		 * @Title: findBusinessCount 
		 * @Description: (查询企业数量) 
		 * @return
		 */
		public long queryBannerCount(){
			String sql = "select count(*) from t_business_banner t where t.status=1";
			return Db.queryLong(sql);
		}
		/**
		 * @Title: find 
		 * @Description: (根据id查询企业详细信息) 
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public Record findBanner(Long id){
			return Db.findById("t_business_banner", id);
		}
		//新增录音课程分类
		public void saveBanner(Record record){
			Db.save("t_business_banner", record);
		}
		//更新录音课程分类
		public void updateBanner(Record record){
			Db.update("t_business_banner", record);
		}
		/**
		 * @Title: delete 
		 * @Description: (删除录音课程分类) 
		 * @param id 
		 */
		public void deleteBanner(Long id){
			Db.update("update t_business_banner set status = 0 where id = ?", id);
		}
}
