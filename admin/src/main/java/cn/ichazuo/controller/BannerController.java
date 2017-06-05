package cn.ichazuo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.CodeUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Employee;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;

/**
 * @ClassName: OnlineController
 * @Description: (线上课程管理Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class BannerController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		List<Record> list = businessService.queryBannerList(page);
		list.forEach(info -> {
			info.set("cover", super.appenUrl(info.getStr("cover")));
		});
		long count = businessService.queryBannerCount();
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/banner?s=1"));
		render("index.jsp");
	}
	//删除录音课程分类
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteBanner(id);
		renderJson(ok());
	}
	public void edit(){
		Long id = getParaToLong("id",0L);
		Record record = businessService.findBanner(id);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record",record );
			record.set("cover", super.appenUrl(record.getStr("cover")));
		}
		render("edit.jsp");
	}
	
	//保存录音课程分类
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
		//名称
		String name = getPara("name");
		//权重值
		String weight = getPara("weight");
		//展示类型
		String showType = getPara("showType");
		//选作封面的课程Id
		Long courseId = getParaToLong("examId",0L);
		//选作封面的课程名称
		String courseName = getPara("examName");
		//选作封面的课程Id
		Long teacherId = getParaToLong("teacher",0L);
		//选作封面的课程名称
		String teacherName = getPara("teacherName");
		//选作封面的课程包Id
		Long typeId = getParaToLong("typeId",0L);
		//选作封面的课程包名称
		String typeName = getPara("typeName");
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
		record.set("name", name);
		record.set("weight", weight);
		record.set("update_at", date);
		record.set("show_type", showType);
		record.set("teacher_id", teacherId);
		record.set("teacher_name", teacherName);
		record.set("course_id", courseId);
		record.set("teacher_name", teacherName);
		record.set("type_id", typeId);
		record.set("type_name", typeName);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("status", 1);
				record.set("create_at",date);
				businessService.saveBanner(record);
			}else{
				record.set("id", id);
				businessService.updateBanner(record);
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
	//查询可以关联到Banner的课程包
	public void query(){
		String title = getPara("name");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> examList = businessService.findCatalog(title);
		for (Record obj : examList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("name")));
			arr.add(json);
		}
		renderJson(arr);
	}
	//查询公开课
	public void queryCatalogGroup(){
		String title = getPara("name");
		if(title==null){
			title="";
		}
		JSONArray arr = new JSONArray();

		List<Record> examList = businessService.findCatalogGroup(title);
		for (Record obj : examList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getLong("id") + ""));
			json.put("value", new String(obj.getStr("name")));
			arr.add(json);
		}
		renderJson(arr);
	}
}
