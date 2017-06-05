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
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;

/**
 * @ClassName: SecondCatalogController
 * @Description: (课程二级分类Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class SecondCatalogController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = businessService.findSecondCatalogList(page, name,null,0L);
		long count = businessService.findSecondCatalogCount(name,null,0L);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/catalogtwo?s=1"));
		render("catalog.jsp");
	}
	//删除二级分类
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteSecondCatalog(id);
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.findSecondCatalog(id));
		}
		render("edit.jsp");
	}
	
	//删除分类下的关联课程
	public void deleteCourse(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteSecondCourse(id);
		renderJson(ok());
	}
	
	//保存课程二级分类信息
	public void save(){
		Long id = getParaToLong("id",0L);
		String name = getPara("name");
		String subtitle = getPara("subtitle");
		String weight = getPara("weight");
		String parentId = getPara("parentId");
		String parentName = getPara("parentName");
		boolean isHave = false;
		try {
			isHave = getFile("imglive") != null;
		} catch (Exception e) {
			isHave = false;
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
		record.set("parent_id", parentId);
		record.set("parent_name", parentName);
		if (isHave) {
			UploadFile file = getFile("imglive");
	        BufferedImage sourceImg;
//			try {
//				sourceImg = ImageIO.read(new FileInputStream(file.getFile()));
//				System.out.println(sourceImg.getWidth());  
//		        System.out.println(sourceImg.getHeight()); 
//		        if(sourceImg.getWidth()!=750){
//		        	renderJson(error("图片长度不是750像素，请选择长度为750像素，高度为370像素的图片"));
//					return;
//		        }
//		        if(sourceImg.getHeight()!=370){
//		        	renderJson(error("图片高度不是370像素，请选择长度为750像素，高度为370像素的图片"));
//					return;
//		        }
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}   
	       // 750 370
//			if (file.getFile().length() >= 100 * 1024) {
//				renderJson(error("图片太大,请选择300k以内的小图!"));
//				return;
//			}
			String path = new Upload().uploadPdf(file, "avatar");
			record.set("avatar", path);
		}
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				businessService.saveSecondCatalog(record);
			}else{
				record.set("id", id);
				businessService.updateSecondCatalog(record);
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

		List<Record> memberList = businessService.findCatalogList(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("name")));
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
		List<Record> list = businessService.findSecondCourseList(page, id);
		long count = businessService.findSecondCourseCount(id);
		setAttr("list", list);
		setAttr("catalogId", id);
		setAttr("page", new Page(page, count, "/admin/catalogtwo/queryCourse?s=1"));
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
			List<Record> list = businessService.findSecondCatalogCourseList(catalogId, courseId);
			if(list.size()>0){
				renderJson(error("课程已经存在此分类！"));
				return;
			}
			businessService.saveSecondCourse(record);
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
			setAttr("record", businessService.findSecondCatalogCourse(id));
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
			businessService.updateSecondWeight(record);
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
			businessService.saveSecondCatalogExam(record);
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//根据一级分类Id查询二级分类列表
	public void querySecond() {
		int page = getParaToInt("page", 1);
		Long id = getParaToLong("id",0L);
		List<Record> list = businessService.findSecondCatalogList(page,null,null,id);
		long count = businessService.findSecondCatalogCount(null,null,id);
		setAttr("list", list);
		setAttr("parentId", id);
		setAttr("page", new Page(page, count, "/admin/catalogtwo?s=1"));
		render("catalog.jsp");
	}
}
