package cn.ichazuo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.Result;
import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;

/**
 * @ClassName: KeywordsController
 * @Description: (企业产品关键字Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class KeywordsController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = businessService.findKeywordsList(page,name);
		long count = businessService.findKeywordsCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/keywords?s=1"));
		render("keywords.jsp");
	}
//删除关键字
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.deleteKeywords(id);
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.findKeyWords(id));
		}
		render("edit.jsp");
	}
	
	//保存关键字信息
	public void save(){
		Long id = getParaToLong("id",0L);
		String keywords = getPara("keywords");
		if(StringUtils.isNullOrEmpty(keywords)){
			renderJson(error("参数错误"));
			return;
		}
		Record record = new Record();
		Date date = new Date();
		record.set("status", 1);
		record.set("keywords", keywords);
		record.set("update_at", date);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("create_at", date);
				businessService.saveKeywords(record);
			}else{
				record.set("id", id);
				businessService.updateKeywords(record);
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
}
