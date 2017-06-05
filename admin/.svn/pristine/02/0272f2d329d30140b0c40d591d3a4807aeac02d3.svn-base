package cn.ichazuo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.ChuanglanSMS;
import cn.ichazuo.common.utils.CodeUtils;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.MobileUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;
import cn.ichazuo.model.Question;
import cn.ichazuo.model.QuestionInfo;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.QuestionService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class CourseTypeController extends BaseController{
	private BusinessService service = Duang.duang(BusinessService.class);
	
	public void index(){
		Integer page = getParaToInt("page", 1);
		long count = service.findBusinessCourseTypeOneCount();
		List<Record> list = service.findBusinessCourseTypeOneList(page);
		list.forEach(info -> {
			info.set("cover", super.appenUrl(info.getStr("cover")));
		});
		setAttr("list",list);
		setAttr("page", new Page(page,count,"/admin/coursetype?s=1"));
		render("typeone.jsp");
	}
	
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		service.deleteFistTypeById(id);
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
//		if(!NumberUtils.isNullOrZero(id)){
//			setAttr("record", service.findFirstTypeById(id));
//		}
		Record record = service.findFirstTypeById(id);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record",record );
			record.set("cover", super.appenUrl(record.getStr("cover")));
		}
		render("editTypeOne.jsp");
	}
	
	public void save(){
		boolean isHave = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
			if (NumberUtils.isNullOrZero(getParaToLong("id", 0L))) {
				renderJson(error("参数缺失!"));
				return;
			}
		}
		Long id = getParaToLong("id",0L);
		String title = getPara("title");
		String weight = getPara("weight");
		//展示类型:0、展示课程，1展示讲师
		String showType = getPara("showType");
		//选作封面的课程Id
		Long courseId = getParaToLong("examId",0L);
		//选作封面的课程名称
		String courseName = getPara("examName");
		//选作封面的课程Id
		Long teacherId = getParaToLong("teacher",0L);
		//选作封面的课程名称
		String teacherName = getPara("teacherName");
		//选作封面的课程分类Id
		Long typeId = getParaToLong("typeId",0L);
		//选作封面的课程分类名称
		String typeName = getPara("typeName");
		if(StringUtils.isNullOrEmpty(title)){
			renderJson(error("名称不能为空！"));
			return;
		}
		Record record = new Record();
		if (isHave) {
			UploadFile file = getFile("imglive");
			if (file.getFile().length() >= 300 * 1024) {
				renderJson(error("图片太大,请选择300k以内的小图!"));
				return;
			}
			String path = new Upload().upload(file, "avatar");
			record.set("cover", path);
			// course.set("cover", "");
		}else{
			if (NumberUtils.isNullOrZero(getParaToLong("courseId", 0L))) {
				record.set("cover", "");
			}
			
		}
		Date date = new Date();
		record.set("status", 1);
		record.set("name", title);
		record.set("weight", weight);
		record.set("update_at", date);
		record.set("show_type", showType);
		record.set("teacher_id", teacherId);
		record.set("teacher_name", teacherName);
		record.set("course_id", courseId);
		record.set("course_name", courseName);
		record.set("type_id", typeId);
		record.set("type_name", typeName);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				
				service.saveFirstType(record);
			}else{
				record.set("id", id);
				service.updateFirstType(record);
				service.updateSecondTypeByParentId(id,title);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	public void query(){
		String title = getPara("demo");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> memberList = service.findFirstType(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("name")));
			arr.add(json);
		}
		renderJson(arr);
	}
	public void queryByParentId(){
		Long parentId = getParaToLong("id", 0L);
		Integer page = getParaToInt("page", 1);
		long count = service.findSecondTypeCountByParentId(parentId);
		setAttr("list",service.findSecondTypeListByParentId(page,parentId));
		setAttr("page", new Page(page,count,"/admin/coursetype/typetwo?s=1"));
		render("typetwo.jsp");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////         ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓             二级目录用                                                    ↓↓↓↓↓↓↓↓↓↓                      /////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void typetwo(){
		Integer page = getParaToInt("page", 1);
		long count = service.findQuestionTypeTwoCount();
		setAttr("list",service.findQuestionTypeTwoList(page));
		setAttr("page", new Page(page,count,"/admin/coursetype/typetwo?s=1"));
		render("typetwo.jsp");
	}
	public void deleteTypeTwo(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		service.deleteSecondTypeById(id);
		renderJson(ok());
	}
	
	public void editTypeTwo(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", service.findSecondTypeById(id));
		}
		render("editTypeTwo.jsp");
	}
	
	public void saveTypeTwo(){
		Long id = getParaToLong("id",0L);
		String title = getPara("title");
		String weight = getPara("weight");
		Long parentId = getParaToLong("parentId",0L);
		String parentName = getPara("parentName");
		if(StringUtils.isNullOrEmpty(title)){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("name", title);
		record.set("weight", weight);
		record.set("parent_id", parentId);
		record.set("parent_name", parentName);
		record.set("update_at", date);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				service.saveSecondType(record);
			}else{
				record.set("id", id);
				service.updateSecondType(record);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	
	public void queryTypeTwo(){
		String title = getPara("demo");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> memberList = service.findSecondType(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("title")));
			arr.add(json);
		}
		renderJson(arr);
	}
	public void querySonByParentId(){
		Long parentId = getParaToLong("id", 0L);
		Integer page = getParaToInt("page", 1);
		long count = service.findSecondTypeCountByParentId(parentId);
		setAttr("list",service.findSecondTypeListByParentId(page,parentId));
		setAttr("page", new Page(page,count,"/admin/coursetype/typetwo?s=1"));
		render("typetwo.jsp");
	}
	public void queryCourse() {
		int page = getParaToInt("page", 1);
		Long id = getParaToLong("id",0L);
		List<Record> list = service.findTypeCourseList(page, id);
		long count = service.findTypeCourseCount(id);
		setAttr("list", list);
		setAttr("parentTypeId", id);
		setAttr("page", new Page(page, count, "/admin/coursetype/queryCourse?s=1"));
		render("course.jsp");
	}
	//保存课程与课程分类的关联关系
	public void saveCourse() {
		Long parentId = getParaToLong("parentId",0L);
		Long catalogId = getParaToLong("catalogId",0L);
		String parentName = getPara("parentName");
		if(parentId==0){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("parent_id", catalogId);
		record.set("course_id", parentId);
		record.set("course_name", parentName);
		record.set("create_at", date);
		try{
			List<Record> list = service.findTypeCourseList(catalogId, parentId);
			if(list.size()>0){
				renderJson(error("课程已经存在此分类！"));
				return;
			}
			service.saveTypeCourse(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//删除分类下的关联课程
	public void deleteCourse(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		service.deleteTypeCourse(id);
		renderJson(ok());
	}
	//修改课程显示权重
		public void editWeight(){
			Long id = getParaToLong("id",0L);
			if(!NumberUtils.isNullOrZero(id)){
				setAttr("record", service.findTypeCourse(id));
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
				service.updateTypeWeight(record);
				renderJson(ok());
			}catch(Exception e){
				e.printStackTrace();
				renderJson(error(Result.ADMIN_SYSTEM_ERROR));
			}
		}
		
		//设置课程为一级分类封面
//		public void bannerCourse() {
//			Long id = getParaToLong("typeoneId",0L);
//			Long parentId = getParaToLong("bannerId",0L);
//			String parentName = getPara("bannerName");
//			if(parentId==0){
//				renderJson(error("请选择课程！"));
//				return;
//			}
//			Record record = new Record();
//			Date date = new Date();
//			record.set("id", id);
//			record.set("exam_id", examId);
//			record.set("exam_name", examName);
//			try{
//				service.saveCatalogExam(record);
//				renderJson(ok());
//			}catch(Exception e){
//				e.printStackTrace();
//				renderJson(error(Result.ADMIN_SYSTEM_ERROR));
//			}
//		}
		//查询所有的讲师列表
		public void findAllTypes(){
			String name = getPara("name","");
			String id = getPara("id");
			JSONArray arr = new JSONArray();

			List<Record> memberList = service.findAllTypes(id,name);
			for (Record obj : memberList) {
				JSONObject json = new JSONObject();
				json.put("id", new String(obj.getInt("id") + ""));
				json.put("value", new String(obj.getStr("name")));
				arr.add(json);
			}
			renderJson(arr);
		}
}
