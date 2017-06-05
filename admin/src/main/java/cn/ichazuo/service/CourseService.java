package cn.ichazuo.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.model.Course;
import cn.ichazuo.model.OfflineCourse;
import cn.ichazuo.model.OfflineCourseImage;
import cn.ichazuo.model.OnlineCourse;
import cn.ichazuo.model.Spread;

/**
 * @ClassName: CourseService 
 * @Description: (课程Service) 
 * @author ZhaoXu
 * @date 2015年8月2日 下午3:01:54 
 * @version V1.0
 */
public class CourseService {
	
	/**
	 * @Title: findAllCity 
	 * @Description: (查询全部城市) 
	 * @return
	 */
	public List<Record> findAllCity(){
		return Db.find("select * from t_city where status = 1");
	}
	
	/**
	 * @Title: findCourseContent 
	 * @Description: (根据ID查询课程内容) 
	 * @param id
	 * @return
	 */
	public String findCourseContent(Long id,Integer type){
		//1.预告  2:直播 3:点播
		switch(type){
		case 1:
			return Db.queryStr("select course_content from t_course_online where course_id = ?",id);
		case 2:
			return Db.queryStr("select course_ppt from t_course_online where course_id = ?",id);
		case 3:
			return Db.queryStr("select course_back from t_course_online where course_id = ?",id);
		}
		return "";
	}
	
	/**
	 * @Title: saveCourseContent 
	 * @Description: (保存课程内容) 
	 * @param id id
	 * @param content 内容
	 * @param type 类别 1.预告  2:直播 3:点播
	 */
	public void saveCourseContent(Long id,String content,Integer type){
		switch(type){
		case 1:
			Db.update("update t_course_online set course_content = ?,version = version + 1,update_at = now() where course_id = ? ",content,id);
			break;
		case 2:
			Db.update("update t_course_online set course_ppt = ?,version = version + 1,update_at = now() where course_id = ? ",content,id);
			break;
		case 3:
			Db.update("update t_course_online set course_back = ?,version = version + 1,update_at = now() where course_id = ? ",content,id);
			break;
		}
	}
	
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findOfflineCourse(int page,String name,int newtype){
		String sql = "from t_course_offline o inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and o.newtype = ?";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.isfull, o.isnew,c.web_hidden,o.is_crowd,c.new_web_hidden,c.is_hidden,c.id as courseId,c.course_name,c.cover,o.course_time,o.price,o.star,o.comment_count,c.is_sell,c.business_hidden,((select case when sum(j.number) is null then 0 else sum(j.number) end  from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = o.course_id) + 0) as joinNumber", sql,newtype).getList();
//		return Db.paginate(page, Result.PAGE_COUNT, "select o.isfull, o.isnew,c.web_hidden,o.is_crowd,c.new_web_hidden,c.is_hidden,c.id as courseId,c.course_name,c.cover,o.course_time,o.price,o.star,o.comment_count,c.is_sell,c.business_hidden,((select count(*) from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = o.course_id) + 0) as joinNumber", sql,newtype).getList();
	}
	
	/**
	 * @Title: findOfflineCourseCount 
	 * @Description: (查询线下课程数量) 
	 * @return
	 */
	public long findOfflineCourseCount(String name,int newtype){
		String sql = "select count(*) from t_course_offline t inner join t_course c on c.id = t.course_id where t.`status` = 1 and c.`status` = 1 and t.newtype = ?";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		return Db.queryLong(sql,newtype);
	}
	
	public long findLivingCourseCount(String name,int newtype){
		String sql = "select count(*) from t_course_offline t inner join t_course c on c.id = t.course_id where t.`status` = 1 and c.`status` = 1 and t.newtype = ?";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		return Db.queryLong(sql,newtype);
	}
	
	/**
	 * @Title: deleteOfflineCourse 
	 * @Description: (删除线下课程) 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Course deleteOfflineCourse(Long courseId) throws Exception{
		Course course = Course.dao.findById(courseId);
		Db.update("update t_course_offline_join set status = 0,version=version+1,update_at=now() where course_id=?",courseId);
		if(OfflineCourse.dao.findFirst("select * from t_course_offline where course_id = ?",courseId).set("status", 0).update() && course.set("status", 0).update()){
			return course;
		}else{
			throw new Exception();
		}
	}
	/**
	 * @Title: deleteSpread 
	 * @Description: (删除推广渠道) 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public void deleteSpread(Long id) throws Exception{
//		Spread spread = Spread.dao.findById(id);
		Db.update("update t_course_spread set status = 0 where id=?",id);
//		return spread;
	}
	/**
	 * @Title: deleteOnlineCourse 
	 * @Description: (删除线上课程) 
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Course deleteOnlineCourse(Long courseId) throws Exception{
		Course course = Course.dao.findById(courseId);
		if(OnlineCourse.dao.findFirst("select * from t_course_online where course_id = ?",courseId).set("status", 0).update() && course.set("status", 0).update()){
			return course;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: findOnlineCourse 
	 * @Description: (查询线上课程) 
	 * @param page
	 * @param name
	 * @return
	 */
	public List<Record> findOnlineCourse(int page,String name){
		String sql = "from t_course_online o inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		sql += " order by o.id desc ";
		return Db.paginate(page, Result.PAGE_COUNT, "select *,c.id as courseId,c.exam_id as examId,c.exam_name as examName, (select count(*) from t_course_online_join o where o.course_id = c.id) as count,((select count(*) from t_course_online_order j where j.`status` = 1 and j.course_id = o.course_id) + 0) as joinNumber ", sql).getList();
	} 
	
	public List<Record> findOrder(Long id){
		return Db.find("select w.order_code as code,w.number,w.price,c.course_name,w.order_code,w.create_at,w.type,u.`name`,a.weixin,a.`work`,w.job,a.mobile,a.sex,w.address,w.email,w.review_status,w.refund_result,u.content,w.is_gift  from t_course_web_order_user u left join t_course_web_order w on u.order_id = w.id left join t_course c on w.course_id = c.id left join t_course_offline o on o.course_id = c.id JOIN t_course_web_order_user a ON a.order_id = w.id where w.`status` = 1 and c.id = ? order by w.create_at asc",id);
	}
	
	public void updateCatalogGroup(Long courseId,Long id){
		Db.update("update t_haoduoke_catalog_group set course_id='"+courseId+"' where id="+id);
	}
	
	/**
	 * @Title: findOnlineCourseCount 
	 * @Description: (查询线上课程数量) 
	 * @param name
	 * @return
	 */
	public long findOnlineCourseCount(String name){
		String sql = "select count(*) from t_course_online t inner join t_course c on c.id = t.course_id where t.`status` = 1 and c.`status` = 1";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		return Db.queryLong(sql);
	}
	
	/**
	 * @Title: saveOfflineCourse 
	 * @Description: (保存线下课程) 
	 * @param offline
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean saveOfflineCourse(OfflineCourse offline,Course course) throws Exception{
		if(course.save() && offline.set("course_id", course.getLong("id")).save()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveOnlineCourse 
	 * @Description: (保存线上课程) 
	 * @param online
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean saveOnlineCourse(OnlineCourse online,Course course) throws Exception{
		if(course.save() && online.set("course_id", course.getLong("id")).save()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: updateOfflineCourse 
	 * @Description: (修改线下课程) 
	 * @param offline
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updateOfflineCourse(OfflineCourse offline,Course course) throws Exception{
		offline.set("course_id", course.getLong("id"));
		if(course.update() && offline.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	public void updateOfflineCourse(OfflineCourse offline){
		offline.update();
	}
	
	/**
	 * @Title: updateOnlineCourse 
	 * @Description: (修改线上课程) 
	 * @param online
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updateOnlineCourse(OnlineCourse online,Course course) throws Exception{
		online.set("course_id", course.getLong("id"));
		if(course.update() && online.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	/**
	 * @Title: findCourseById 
	 * @Description: (根据ID查询课程) 
	 * @param id
	 * @return
	 */
	public Course findCourseById(Long id){
		return Course.dao.findById(id);
	}
	/**
	 * @Title: findSpreadById 
	 * @Description: (根据ID查询课程) 
	 * @param id
	 * @return
	 */
	public Record findSpreadById(Long id){
		return Db.findById("t_course_spread", id);
	}
	/**
	 * @Title: findSpreadByFrom1
	 * @Description: (根据ID查询课程) 
	 * @param id
	 * @return
	 */
	public List<Record> findSpreadByFrom1(String from1){
		return Db.find("select * from t_course_spread where from1='"+from1+"'");
	}
	
	/**
	 * @Title: findOfflineByCourseId 
	 * @Description: (根据课程ID查询线下课程信息) 
	 * @param id
	 * @return
	 */
	public OfflineCourse findOfflineByCourseId(Long id){
		return OfflineCourse.dao.findFirst("select * from t_course_offline where status = 1 and course_id = ?",id);
	}
	
	/**
	 * @Title: findOnlineByCourseId 
	 * @Description: (根据课程ID查询线上课程信息) 
	 * @param id
	 * @return
	 */
	public OnlineCourse findOnlineByCourseId(Long id){
		return OnlineCourse.dao.findFirst("select * from t_course_online where status = 1 and course_id = ?",id);
	}
	
	public void updateOfflineJoinStatus(Long id){
		Db.update("update t_course_offline_join set status = 0 where id = ? ",id);
		Long memberId = Db.queryLong("select member_id from t_course_offline_join where id = ?",id);
		Integer orderId = Db.queryInt("select order_id from t_course_offline_join where id = ?",id);
		//删除订单信息
		if(orderId!=0){
			Db.update("update t_course_web_order set status = 2 where id = ?",orderId);
			Db.update("update t_course_web_order_user set status = 2 where order_id = ?",orderId);
		}
//		Db.update("update t_course_web_order set status = 2 where member_id = ?",memberId);
		Db.update("update t_course_web_crowdfunding_user set `status` = 0 where member_id = ?",memberId);
	}
	
	/**
	 * @Title: updatePlayAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param online
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updatePlayAddress(OnlineCourse online) throws Exception{
		if(online.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: updateCourse 
	 * @Description: (更新课程) 
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updateCourse(Course course) throws Exception{
		if(course.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: findImagesByCourseId 
	 * @Description: (根据课程ID查询课程图片) 
	 * @param id
	 * @return
	 */
	public List<OfflineCourseImage> findImagesByCourseId(Long id){
		return OfflineCourseImage.dao.find("select * from t_course_offline_image where course_id = ? and status = 1", id);
	}
	
	/**
	 * @Title: updateImage 
	 * @Description: (修改图片) 
	 * @param image
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updateImage(OfflineCourseImage image) throws Exception{
		if(image.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: saveImage 
	 * @Description: (保存图片) 
	 * @param image
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean saveImage(OfflineCourseImage image) throws Exception{
		if(image.save()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: findImageById 
	 * @Description: (根据ID查询图片) 
	 * @param id
	 * @return
	 */
	public OfflineCourseImage findImageById(Long id){
		return OfflineCourseImage.dao.findById(id);
	}
	
	/**
	 * @Title: updateOnlineCourse 
	 * @Description: (修改线下课程) 
	 * @param course
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public boolean updateOnlineCourse(OnlineCourse course) throws Exception{
		if(course.update()){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * @Title: findAllOnlineCourse 
	 * @Description: (查询全部线上课程) 
	 * @return
	 */
	public List<Record> findAllOnlineCourse(){
		return Db.find("select id,course_name from t_course where type = 1 and status = 1 order by begin_time desc");
	}
	
	public String findCourseNameById(Long id){
		return Db.queryStr("select course_name from t_course where id = ?",id);
	}
	
	public List<Record> findOrderDetail(Long id){
		return Db.find("select * from v_offline_join where course_id = ?",id);
	}
	
	/**
	 * @Title: findOfflineCourse 
	 * @Description: (查询线下课程列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findBookList(int page,String name){
		String sql = " from t_course_offline o inner join t_course c on c.id = o.course_id where o.`status` = 1 and c.`status` = 1 and o.newtype = 3";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		sql += " order by o.id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select o.isfull, o.isnew,c.web_hidden,o.is_crowd,c.new_web_hidden,c.is_hidden,c.id as courseId,c.course_name,c.cover,o.course_time,o.price,o.star,o.comment_count,c.is_sell,c.business_hidden,(select count(*) from t_course_web_order a join t_course_web_order_user b on a.id=b.order_id join t_course c on c.id=a.course_id where a.status=1 and b.status=1 and a.course_id = o.course_id) as joinNumber", sql).getList();
//		return Db.paginate(page, Result.PAGE_COUNT, "select o.isfull, o.isnew,c.web_hidden,o.is_crowd,c.new_web_hidden,c.is_hidden,c.id as courseId,c.course_name,c.cover,o.course_time,o.price,o.star,o.comment_count,c.is_sell,c.business_hidden,((select count(*) from t_course_offline_join j left join t_member t on j.member_id = t.id left join t_member_info i on i.member_id = t.id where t.status = 1 and j.`status` = 1 and j.course_id = o.course_id) + 0) as joinNumber", sql,newtype).getList();
	}
	
	/**
	 * @Title: findOfflineCourseCount 
	 * @Description: (查询线下课程数量) 
	 * @return
	 */
	public long findBookCount(String name){
		String sql = "select count(*) from t_course_offline t inner join t_course c on c.id = t.course_id where t.`status` = 1 and c.`status` = 1 and t.newtype = 3";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and c.course_name like '%"+name+"%'";
		}
		return Db.queryLong(sql);
	}
	/**
	 * @Title: findSpreadList 
	 * @Description: (查询推广信息列表) 
	 * @param page
	 * @return
	 */
	public List<Record> findSpreadList(int page,String name,String from1){
		String sql = "from t_course_spread where status=1 ";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and course_name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(from1)){
			sql += " and from1 = '"+from1+"'";
		}
		sql += " order by id desc";
		return Db.paginate(page, Result.PAGE_COUNT, "select id,create_at,course_name,promotion_party,cooperation_mode,from1 ", sql).getList();
	}
	
	/**
	 * @Title: findSpreadCount 
	 * @Description: (查询推广信息数量) 
	 * @return
	 */
	public long findSpreadCount(String name,String from1){
		String sql = "select count(*) from t_course_spread where status = 1 ";
		if(!StringUtils.isNullOrEmpty(name)){
			sql += " and course_name like '%"+name+"%'";
		}
		if(!StringUtils.isNullOrEmpty(from1)){
			sql += " and from1 = '"+from1+"'";
		}
		return Db.queryLong(sql);
	}
	/**
	 * @Title: saveSpread 
	 * @Description: (保存推广渠道) 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public void saveSpread(Record record) throws Exception{
		Db.save("t_course_spread", record);
	}
	/**
	 * @Title: updateSpread 
	 * @Description: (修改线上课程) 
	 * @param online
	 * @param course
	 * @return
	 * @throws Exception
	 */
	public void updateSpread(Record record) throws Exception{
		Db.update("t_course_spread", record);
	}
}
