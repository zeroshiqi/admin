package cn.ichazuo.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.OfflineCourse;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;
import cn.ichazuo.service.TeacherService;

/**
 * @ClassName: CompanyController
 * @Description: (企业管理Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class CatalogController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);
	private TeacherService service = Duang.duang(TeacherService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		String type = getPara("type");
		List<Record> list = businessService.findCatalogList(page,name,type);
		long count = businessService.findCatalogCount(name,type);
		//固定的每日最新
		Record record = businessService.findCatalog(1,1L).get(0);
		setAttr("list", list);
		setAttr("record", record);
		setAttr("type", type);
		setAttr("page", new Page(page, count, "/admin/catalog?s=1&type="+type));
		render("catalog.jsp");
	}
	//删除分类
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteCatalog(id);
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		Long type = getParaToLong("type",0L);
		if(!NumberUtils.isNullOrZero(id)){
			Record record = businessService.findCatalog(id);
			record.set("avatar", super.appenUrl(record.getStr("avatar")));
			record.set("cover", super.appenUrl(record.getStr("cover")));
			String ids = record.getStr("teacher_id");
			StringBuffer sb = new StringBuffer();
			for (String s : ids.split(",")) {
				if (StringUtils.isNullOrEmpty(s)) {
					continue;
				}
				sb.append(service.findTeacherById(Long.parseLong(s)).getStr("name")).append(",");
			}
			setAttr("teacherNames", StringUtils.removeEndChar(sb.toString(), ','));
			setAttr("record",record);
		}
		setAttr("type",type);
		render("edit.jsp");
	}
	
	//删除分类下的关联课程
	public void deleteCourse(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteCourse(id);
		renderJson(ok());
	}
	
	//保存课程分类信息
	public void save(){
		Long id = getParaToLong("id",0L);
		String name = getPara("name");
		String subtitle = getPara("subtitle");
		String weight = getPara("weight");
		String flag = getPara("flag");
		String type = getPara("type");
		String teacher = getPara("teacher");
		String teacherNames = getPara("teacherNames");
		String total = getPara("total");
		boolean isHave = false;
		boolean isHaveCover = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
		}
		try {
			isHaveCover = getFile("imglive1") != null;
		} catch (Exception e) {
			isHaveCover = false;
		}
	
		if(StringUtils.isNullOrEmpty(name)){
			renderJson(error("分类名称参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("name", name);
		record.set("subtitle", subtitle);
		record.set("weight", weight);
		record.set("update_at", date);
		record.set("flag", flag);
		record.set("type", type);
		record.set("teacher_id", teacher);
		record.set("teacher_name", teacherNames);
		record.set("total", total);
		if (isHave) {
			UploadFile file = getFile("imglive");
			String path = new Upload().uploadPdf(file, "avatar");
			record.set("avatar", path);
		}
		if(isHaveCover){
			UploadFile file = getFile("imglive1");
			String path = new Upload().uploadPdf(file, "cover");
			record.set("cover", path);
		}
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				businessService.saveCatalog(record);
			}else{
				record.set("id", id);
				businessService.updateCatalog(record);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//查询可以添加到分类的课程
	public void query(){
		String title = getPara("demo");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> memberList = businessService.findOnlineCourse(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getLong("id") + ""));
			json.put("value", new String(obj.getStr("course_name")));
			arr.add(json);
		}
		renderJson(arr);
	}
	//查询可以关联到课程的试卷
	public void queryExam(){
		String title = getPara("examDemo");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> examList = businessService.findExamList(title);
		for (Record obj : examList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("title")));
			arr.add(json);
		}
		renderJson(arr);
	}
	
	//保存企业用户
	public void saveCompany(){
		Long id = getParaToLong("id",0L);
		String bname = getPara("bname");
		String loginName = getPara("loginName");
		Long blevel = getParaToLong("blevel",0L);
		String address = getPara("address");
		String scale = getPara("scale");
		String password = PasswdEncryption.generate(loginName.trim());
//		if(StringUtils.isNullOrEmpty(title)){
//			renderJson(error("参数错误"));
//			return;
//		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("business_name", bname);
		record.set("login_name", loginName);
		record.set("business_level", blevel);
		record.set("business_address", address);
		record.set("business_scale", scale);
		record.set("update_at", date);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("password", password);
				record.set("create_at", date);
				businessService.save(record);
			}else{
				record.set("id", id);
				businessService.update(record);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	
	public void queryCourse() {
		int page = getParaToInt("page", 1);
		Long id = getParaToLong("id",0L);
		List<Record> list = businessService.findCourseList(page, id);
		long count = businessService.findCourseCount(id);
		setAttr("list", list);
		setAttr("catalogId", id);
		setAttr("page", new Page(page, count, "/admin/catalog/queryCourse?s=1"));
		render("course.jsp");
	}
	//保存课程与课程分类的关联关系
	public void saveCourse() {
		Long courseId = getParaToLong("parentId",0L);
		Long catalogId = getParaToLong("catalogId",0L);
		String parentName = getPara("parentName");
		if(courseId==0){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("catalog_id", catalogId);
		record.set("course_id", courseId);
		record.set("course_name", parentName);
		record.set("create_at", date);
		try{
			List<Record> list = businessService.findCatalogCourseList(catalogId, courseId);
			if(list.size()>0){
				renderJson(error("课程已经存在此分类！"));
				return;
			}
			businessService.saveCourse(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//修改课程显示权重
	public void editWeight(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.findCatalogCourse(id));
		}
		render("editWeight.jsp");
	}
	//修改课程显示权重
	public void saveWeight(){
		try{
			Long id = getParaToLong("id",0L);
			Long weight = getParaToLong("weight",0L);
			Record record = new Record();
			record.set("id", id);
			record.set("weight", weight);
			businessService.updateWeight(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//保存课程与课程分类的关联关系
	public void saveCourseExam() {
		Long id = getParaToLong("catalogCourseId",0L);
		Long examId = getParaToLong("examId",0L);
		String examName = getPara("examName");
		if(examId==0){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("id", id);
		record.set("exam_id", examId);
		record.set("exam_name", examName);
		try{
			businessService.saveCourseExam(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	public void isnew() {
		Long catalogId = getParaToLong("catalogId", 0L);
		if (catalogId == 0) {
			renderJson(ok());
			return;
		}
		try {
			Record record = businessService.findCatalog(catalogId);
			if (record.getInt("update_mark") == 0) {
				record.set("update_mark", 1);
			} else {
				record.set("update_mark", 0);
			}
			businessService.updateCatalog(record);
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	//保存课程与课程分类的关联关系
	public void saveCatalogExam() {
		Long id = getParaToLong("catalogId",0L);
		Long examId = getParaToLong("examId",0L);
		String examName = getPara("examName");
		if(examId==0){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("id", id);
		record.set("exam_id", examId);
		record.set("exam_name", examName);
		try{
			businessService.saveCatalogExam(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
}
