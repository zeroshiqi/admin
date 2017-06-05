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
public class OnlineController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		List<Record> list = businessService.queryOnlineTypeList(page);
		long count = businessService.findOnlineTypeCount();
		setAttr("list", list);
		setAttr("dictId", list.get(0).get("dict_id"));
		setAttr("page", new Page(page, count, "/admin/online?s=1"));
		render("index.jsp");
	}
	//删除录音课程分类
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteOnlineType(id);
		renderJson(ok());
	}
	public void edit(){
		Long id = getParaToLong("id",0L);
		Long dictId = getParaToLong("dictId",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.findOnlineType(id));
		}
		setAttr("dictId", dictId);
		render("edit.jsp");
	}
	
	public void queryUser(){
		int page = getParaToInt("page", 1);
		int id = getParaToInt("id",1);
		String mobile = getPara("mobile");
		List<Record> list = businessService.findEmployeeList(page, id,mobile);
		long count = businessService.findEmployeeCount(id,mobile);
		setAttr("list", list);
		setAttr("companyId", id);
		setAttr("page", new Page(page, count, "/admin/company/queryUser?s=1"));
		render("employee.jsp");
	}
	
	public void editEmployee(){
		Long id = getParaToLong("id",0L);
		Long companyId = getParaToLong("companyId",0L);
		if(!NumberUtils.isNullOrZero(id)){
			Record record = businessService.findEmployee(id);
//			record.set("avatarAddress", uploadFile.upload(file, "avatar"));
			setAttr("record", record);
		}
		setAttr("companyId", companyId);
		render("editEmployee.jsp");
	}
	
	//删除企业用户
	public void deleteEmployee(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteEmployee(id);
		renderJson(ok());
	}
	
	//保存录音课程分类
	public void save(){
		Long id = getParaToLong("id",0L);
		//名称
		String name = getPara("name");
		//权重值
		String weight = getPara("weight");
		Long dictId = getParaToLong("dictId",0L);
		Record record = new Record();
		Date date = new Date();
		record.set("value", name);
		record.set("weight", weight);
		record.set("update_at", date);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("status", 1);
				record.set("dict_id", dictId);
				record.set("create_at",date);
				businessService.saveOnlineType(record);
			}else{
				record.set("id", id);
				businessService.updateOnlineType(record);
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

		List<Record> memberList = businessService.findCompany(title);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getInt("id") + ""));
			json.put("value", new String(obj.getStr("business_name")));
			arr.add(json);
		}
		renderJson(arr);
	}
	
	//保存企业
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
}
