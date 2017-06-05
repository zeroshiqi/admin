package cn.ichazuo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Employee;
import cn.ichazuo.service.BusinessService;
import cn.ichazuo.service.CourseService;
import cn.ichazuo.service.LogService;

/**
 * @ClassName: CompanyController
 * @Description: (企业管理Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class CompanyController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);
	private LogService logService = Duang.duang(LogService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = businessService.findBusinessList(page, name);
		long count = businessService.findBusinessCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/company?s=1"));
		render("company.jsp");
	}
	//删除企业
	public void delete(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		businessService.delete(id);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->删除了id为:" + id + " 的企业");
		renderJson(ok());
	}
	//使企业失效
	public void abate(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		//使企业失效
		businessService.abate(id);
		//使企业下的用户失效
		businessService.abateEmployee(id);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->使id为:" + id + " 的企业和此企业下的用户账号失效了。");
//		renderJson(ok());
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = businessService.findBusinessList(page, name);
		long count = businessService.findBusinessCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/company?s=1"));
		render("company.jsp");
	}
	//使某个企业用户失效
	public void abateEmployee(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		//使企业下的用户失效
		businessService.abateEmployeeById(id);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->使id为:" + id + " 的企业用户账号失效了。");
		renderJson(ok());
	}
	//使企业生效
	public void alive(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		//使企业生效
		businessService.alive(id);
		//生效企业下的用户
		businessService.aliveEmployee(id);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->使id为:" + id + " 的企业和此企业下的用户账号生效了。");
//		renderJson(ok());
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		List<Record> list = businessService.findBusinessList(page, name);
		long count = businessService.findBusinessCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/company?s=1"));
		render("company.jsp");
	}
	//使某个企业用户生效
	public void aliveEmployee(){
		Long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("error"));
			return;
		}
		//使企业下的用户失效
		businessService.aliveEmployeeById(id);
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->使id为:" + id + " 的好多课用户账号生效了。");
		renderJson(ok());
	}
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.find(id));
		}
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
		setAttr("page", new Page(page, count, "/admin/company/queryUser?s=1&id="+id));
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
		LoginUser user = getSessionAttr(PropKit.get("project.session"));
		logService.saveLog(user,1,id,
				"用户:" + user.getRealName() + "->删除了id为:" + id + " 的好多课用户账号");
		renderJson(ok());
	}
	
	//保存企业用户
	public void save(){
		boolean isHave = false;
		try{
			isHave = getFile("imglive") != null;
		}catch(Exception e){
			isHave = false;
		}
		Long id = getParaToLong("id",0L);
		String name = getPara("name");
		String sex = getPara("sex");
		int businessId = getParaToInt("bid",0);
		String parentName = getPara("parentName");
		String mobile = getPara("mobile");
		String mailbox = getPara("mailbox");
		String position = getPara("position");
		String password = PasswdEncryption.generate(mobile.trim());
		long count = businessService.findEmployeeCount(businessId,null);
		Record bRecord = businessService.find(Long.parseLong(String.valueOf(businessId)));
		int level = bRecord.getInt("business_level");
		if(count>(level*10)){
			renderJson(error("此企业只能创建"+level*10+"个用户！"));
			return;
		}
//		if(StringUtils.isNullOrEmpty(title)){
//			renderJson(error("参数错误"));
//			return;
//		}
		Record record = new Record();
		Date date = new Date();
		if(isHave){
			String path = new Upload().upload(getFile("imglive"), "avatar");
			record.set("avatar", path);
		}else{
			record.set("avatar", (CodeUtils.getRandomInt(8) + 1) + ".png");
		}
		record.set("status", 1);
		record.set("name", name);
		record.set("sex", sex);
		record.set("mobile", mobile);
		record.set("business_id", businessId);
		record.set("business_name", parentName);
		record.set("mailbox", mailbox);
		record.set("position", position);
		record.set("update_at", date);
		record.set("expiry_date", 185);
		try{
			if(NumberUtils.isNullOrZero(id)){
				record.set("password", password);
				record.set("create_at", date);
				//查询手机号码是否已经被注册过App账号
				List<Record> exist = Db.find("select id from t_business_employee where mobile = ? and status = 1 limit 1",mobile);
				if(exist.size()>0){
					renderJson(error("此手机号码已经被注册过！"));
					return;
				}
				businessService.saveEmployee(record);
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->创建了手机号为:" + mobile + " 的好多课用户账号。");
			}else{
				record.set("id", id);
				businessService.updateEmployee(record);
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->修改了手机号为:" + mobile + " 的好多课用户账号。");
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
	public void quanxian(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.find(id));
		}
		render("quanxian.jsp");
	}
	public void power(){
		int page = getParaToInt("page", 1);
		String mobile = getPara("mobile");
		String name = getPara("name");
		List<Record> list = businessService.findAllEmployeeList(page,mobile,name);
		long count = businessService.findAllEmployeeCount(mobile,name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/company/power?s=1"));
		render("employeeList.jsp");
	}
	//权限开通
	public void kaitong(){
		//企业ID
		Long id = getParaToLong("id",0L);
		//课程包ID
		Long catalogId = getParaToLong("catalogId",0L);
		//开通期限
		Long expiryDate = getParaToLong("expiryDate",0L);
		StringBuffer result = new StringBuffer();
		StringBuffer result1 = new StringBuffer();
		StringBuffer result2 = new StringBuffer();
		//一、根据企业ID查询出该企业下所有的用户
		try{
			List<Record> list = businessService.findAllEmployeeListByCompany(id);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Integer employeeId = list.get(i).get("id");
					//取手机号
					String mobile = list.get(i).get("mobile");
					//查询企业下每个用户的权限是否包含将要分配的课程包权限
					//①、catalogId等于0时为年费会员权限
					if(catalogId==0){
						//根据学员ID和课程包ID查询学员是否已经开通了权限
						List<Record> recordlist = businessService.findMemberRecordList(employeeId.toString(),catalogId);
						if(recordlist.size()>0){
							result.append(mobile).append(",");
						}else{
							Record record = new Record();
							Date date = new Date();
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Calendar calendar1 = Calendar.getInstance();
							//取账号创建时间
					    	calendar1.setTime(new Date());
					    	//账号创建时间加上会员日期为会员过期日期
					    	calendar1.add(Calendar.DAY_OF_MONTH,  Integer.valueOf(expiryDate.toString()));
							record.set("status", 1);
							record.set("employee_id", employeeId);
							record.set("end_date", sdf1.format(calendar1.getTime()));
							record.set("expiry_date", expiryDate);
							record.set("type", "0");
							record.set("course_id", "0");
							record.set("catalog_id", "0");
							record.set("gain_way", "1001");
							record.set("create_at",date);
							businessService.saveMemberRecord(record);
						}
					}else{
						//根据学员ID和课程包ID查询学员是否已经开通了权限
						List<Record> recordlist = businessService.findMemberRecordList(employeeId.toString(),0L);
						//先判断学员是否有年票会员权限
						if(recordlist.size()>0){
							result1.append(mobile).append(",");
						}else{
							List<Record> recordHavelist = businessService.findMemberRecordList(employeeId.toString(),catalogId);
							if(recordHavelist.size()>0){
								result2.append(mobile).append(",");
							}else{
								Record record = new Record();
								Date date = new Date();
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Calendar calendar1 = Calendar.getInstance();
								//取账号创建时间
						    	calendar1.setTime(new Date());
						    	//账号创建时间加上会员日期为会员过期日期
						    	calendar1.add(Calendar.DAY_OF_MONTH,  Integer.valueOf(expiryDate.toString()));
								record.set("status", 1);
								record.set("employee_id", employeeId);
								record.set("end_date", sdf1.format(calendar1.getTime()));
								record.set("expiry_date", expiryDate);
								record.set("type", "1");
								record.set("course_id", "0");
								record.set("catalog_id", catalogId);
								record.set("gain_way", "1001");
								record.set("create_at",date);
								businessService.saveMemberRecord(record);
							}
						}
					}
				}
				if(catalogId==0){
					renderJson(ok("以下手机号码因为已经注册过好多课年费会员开通失败:"+result.toString()));
				}else{
					renderJson(ok("以下手机号码因为已经注册过好多课年费会员开通失败:"+result1.toString()+"以下手机号码因为已经拥有相关权限开通失败:"+result2.toString()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常请联系管理员！"));
		}
		
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
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->创建了名为:" + bname + " 的企业。");
			}else{
				record.set("id", id);
				businessService.update(record);
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user,1,id,
						"用户:" + user.getRealName() + "->修改了名为:" + bname + " 的企业资料。");
			}
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error(Result.ADMIN_SYSTEM_ERROR));
		}
	}
}
