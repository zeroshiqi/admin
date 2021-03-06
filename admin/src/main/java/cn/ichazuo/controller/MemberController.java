package cn.ichazuo.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.CodeUtils;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.ExportExcel;
import cn.ichazuo.common.utils.ExportModel;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.PasswdEncryption;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.Upload;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.ClassList;
import cn.ichazuo.model.Member;
import cn.ichazuo.model.MemberInfo;
import cn.ichazuo.service.DictItemService;
import cn.ichazuo.service.LogService;
import cn.ichazuo.service.MemberService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @ClassName: MemberController 
 * @Description: (memberController) 
 * @author ZhaoXu
 * @date 2015年8月1日 下午1:25:49 
 * @version V1.0
 */
public class MemberController extends BaseController{
	private MemberService memberService = Duang.duang(MemberService.class);
	private DictItemService dictItemService = Duang.duang(DictItemService.class);
	private LogService logService = Duang.duang(LogService.class);
	
	//认可的人
	public void favour(){
		Long memberId = getParaToLong("memberId",0L);
		Integer type = getParaToInt("type",0);
		Integer page = getParaToInt("page",1);
		if(NumberUtils.isNullOrZero(memberId)){
			redirect("/member");
			return;
		}
		Member member = memberService.findMemberById(memberId);
		setAttr("page", new Page(page, memberService.findFavourMemberCount(type, memberId), "/admin/member/favour?memberId="+memberId+"&type="+type));
		List<Member> list = memberService.findFavourMember(type, memberId, page);
		list.forEach(info -> {
			info.set("avatar", super.appenUrl(info.getStr("avatar")));
		});
		setAttr("list", list);
		setAttr("type",type);
		setAttr("member", member);
		render("favour.jsp");
	}
	
	//list
	public void index(){
		String mobile = getPara("mobile","");
		String nickName = getPara("nickName","");
		String sex = getPara("sex","");
//		Integer study = getParaToInt("study",-1);
//		String weixin = getPara("weixin","");
//		Integer pay = getParaToInt("pay",-1);
		Integer page = getParaToInt("page", 1);
//		Integer oldStu = getParaToInt("oldStu",-1);
//		Integer gongzhong = getParaToInt("gongzhong",-1);
		String work = getPara("work","");
		long count = memberService.findMemberCount(mobile,nickName,sex,work);
		System.out.println("开始查询客户信息时间："+new Date());
		List<Member> memberList = memberService.findMemberList(page,mobile,nickName,sex,work);
		System.out.println("结束查询客户信息时间："+new Date());
		memberList.forEach(member->{
			member.set("nick_name", StringUtils.subString(member.get("nick_name"), 5));
		});
		setAttr("list",memberList);
		setAttr("page", new Page(page,count,"/admin/member?work="+work+"&mobile="+mobile+"&nickName="+nickName+"&sex="+sex));
		
//		setAttr("oldStu", oldStu).setAttr("gongzhong", gongzhong).setAttr("work", work).setAttr("study", study).setAttr("weixin", weixin).setAttr("pay", pay)
		setAttr("nickName",nickName).setAttr("mobile", mobile).setAttr("sex", sex);
		render("members.jsp");
	}
	
	//to edit
	public void edit(){
		long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			Member member = memberService.findMemberById(id);
			member.set("avatar", appenUrl(member.getStr("avatar")));
			setAttr("member", member);
			MemberInfo memberInfo = memberService.findMemberInfoByMemberId(id);
			setAttr("info", memberInfo);
		}
		setAttr("coreCapacity", dictItemService.findAll("CORECAPACITY"));
		render("member.jsp");
	}
	
	//to info 
	public void info(){
		long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			redirect("/member");
			return;
		}
		Member member = memberService.findMemberById(id);
		member.set("avatar", appenUrl(member.getStr("avatar")));
		setAttr("member", member);
		MemberInfo memberInfo = memberService.findMemberInfoByMemberId(id);
		setAttr("info", memberInfo);
		setAttr("core_capacity",memberService.findCoreCapacity(memberInfo.getLong("core_capacity_id")));
		setAttr("fromCount", memberService.findMemberFavourCount(member.getLong("id"), true));
		setAttr("toCount", memberService.findMemberFavourCount(member.getLong("id"), false));
		setAttr("onlineCount", memberService.findMemberJoinCourseCount1(member.getStr("mobile"), false).size());
		setAttr("offlineCount", memberService.findMemberJoinCourseCount(id, true));
		
		render("info.jsp");
	}
	
	//save
	public void save(){
		boolean isHave = false;
		try{
			isHave = getFile("imglive") != null;
		}catch(Exception e){
			isHave = false;
		}
		if(StringUtils.isNullOrEmpty(getPara("nickName")) || StringUtils.isNullOrEmpty(getPara("gender")) 
				|| NumberUtils.isNullOrZero(getParaToLong("core"))){
			renderJson(error("参数错误!"));
			return;
		}
		Long memberId = getParaToLong("memberId");
		if(NumberUtils.isNullOrZero(memberId)){
			long count = memberService.findMemberMobileCount(getPara("mobile"));
			if(StringUtils.isNullOrEmpty(getPara("password"))){
				renderJson(error("参数错误!"));
				return;
			}
			if(count > 0){
				renderJson(error("手机号已存在!"));
				return;
			}
			Member member = new Member();
			member.set("mobile", getPara("mobile")).set("password", PasswdEncryption.generate(getPara("password")));
			member.set("nick_name", getPara("nickName"));
			if(isHave){
				String path = new Upload().upload(getFile("imglive"), "avatar");
				member.set("avatar", path);
			}else{
				member.set("avatar", (CodeUtils.getRandomInt(8) + 1) + ".png");
			}
			member.set("login_number", 0).set("access_token", CodeUtils.getUUID()).set("client_version", "1.0").set("device_id", "null");
			member.set("create_at", DateUtils.getNowDate());
			
			MemberInfo info = new MemberInfo();
			info.set("gender", getPara("gender")).set("birthday", DateUtils.parseDate(getPara("birthday",DateUtils.formatDate(DateUtils.getNowDate())), DateUtils.DATE_FORMAT_NORMAL));
			info.set("company_name", getPara("company","")).set("company_id", 0L).set("job_name", getPara("job","")).set("job_year", getParaToInt("jobYear",LocalDate.now().getYear()));
			info.set("core_capacity_id", getParaToLong("core")).set("create_at", DateUtils.getNowDate());
			info.set("weixin", getPara("weixin")).set("study", getParaToInt("study",0)).set("pay_type", getParaToInt("payType",0)).set("email", getPara("email"));
			info.set("old_stu", getParaToInt("oldStu",0)).set("gongzhong", getParaToInt("gongzhong",0));
			info.set("content", getPara("content"));
			try{
				member = memberService.saveMember(member, info);
				
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 3, member.getLong("id"), "用户:"+user.getRealName()+"->添加了注册用户:" + member.getStr("mobile"));
				
				renderJson(ok());
			}catch(Exception e){
				e.printStackTrace();
				renderJson(error("系统异常!"));
			}
		}else{
			Member member = memberService.findMemberById(memberId);
			MemberInfo memberInfo = memberService.findMemberInfoByMemberId(memberId);
			member.set("nick_name", getPara("nickName"));
			if(isHave){
				String path = new Upload().upload(getFile("imglive"), "avatar");
				member.set("avatar", path);
			}
			member.set("update_at", DateUtils.getNowDate());
			member.set("version", member.getLong("version") + 1);
			
			memberInfo.set("gender", getPara("gender")).set("birthday", DateUtils.parseDate(getPara("birthday",DateUtils.formatDate(DateUtils.getNowDate())), DateUtils.DATE_FORMAT_NORMAL));
			memberInfo.set("company_name", getPara("company","")).set("company_id", 0L).set("job_name", getPara("job","")).set("job_year", getParaToInt("jobYear",LocalDate.now().getYear()));
			memberInfo.set("core_capacity_id", getParaToLong("core")).set("update_at", DateUtils.getNowDate()).set("version", memberInfo.getLong("version") + 1);
			memberInfo.set("weixin", getPara("weixin")).set("study", getParaToInt("study",0)).set("pay_type", getParaToInt("payType",0));
			memberInfo.set("old_stu", getParaToInt("oldStu",0)).set("gongzhong", getParaToInt("gongzhong",0));
			memberInfo.set("content", getPara("content"));
			try{
				member = memberService.updateMember(member, memberInfo);
				
				LoginUser user = getSessionAttr(PropKit.get("project.session"));
				logService.saveLog(user, 3, member.getLong("id"), "用户:"+user.getRealName()+"->修改了注册用户:" + memberId);
				
				renderJson(ok());
			}catch(Exception e){
				e.printStackTrace();
				renderJson(error("系统异常!"));
			}
		}
	}
	
	//导入xls
	public void saveMembers(){
		try{
			UploadFile file = getFile("XLSFile");
			Workbook book =  Workbook.getWorkbook(new FileInputStream(file.getFile()));   
			
			Sheet readsheet = book.getSheet(0); 
			//获取Sheet表中所包含的总行数   
            int rsRows = readsheet.getRows();   
            Map<Member,MemberInfo> members = new HashMap<>();
            //课程Id
            Map<Member,Long> courseIds = new HashMap<>();
            //购买途径
            Map<Member,Long> sourse = new HashMap<>();
            //购买数量
            Map<Member,Long> numbers = new HashMap<>();
            //邮箱
            Map<Member,String> emails = new HashMap<>();
            //审核结果
            Map<Member,Long> reviewStatus = new HashMap<>();
            //提问
            Map<Member,String> contents = new HashMap<>();
            //价格
            Map<Member,Double> prices = new HashMap<>();
            Long sourseFrom = 4L;
            for(int i=1;i<rsRows;i++){
            	Member member = new Member();
            	
            	Cell name = readsheet.getCell(0, i);   
            	if(StringUtils.isNullOrEmpty(name.getContents())){
            		continue;
            	}
            	String nickName = name.getContents();
            	member.set("nick_name", nickName.trim());
            	
            	Cell mobile = readsheet.getCell(1, i);   
            	member.set("mobile", mobile.getContents().replaceAll(" ", "").trim());
            	member.set("password", PasswdEncryption.generate("123456"));
            	
            	member.set("avatar", (CodeUtils.getRandomInt(8) + 1) + ".png");
            	member.set("login_number", 0);
            	member.set("access_token", CodeUtils.getUUID());
            	member.set("device_id", "null");
            	member.set("client_version", "1.0");
            	member.set("create_at",DateUtils.getNowDate());
            	
            	MemberInfo memberInfo = new MemberInfo();
            	memberInfo.set("create_at",DateUtils.getNowDate());
            	
            	Cell gender = readsheet.getCell(2, i);   
            	String genderStr = gender.getContents();
            	memberInfo.set("gender", genderStr.trim());
            	
//            	Cell birthday = readsheet.getCell(3, i);   
//            	String temp = birthday.getContents();
//            	if(StringUtils.isNullOrEmpty(temp)){
//            		temp = "2015-1-1";
//            		memberInfo.set("birthday", DateUtils.parseDate(temp,DateUtils.DATE_FORMAT_NORMAL));
//            	}else{
//            		memberInfo.set("birthday", DateUtils.parseDate(birthday.getContents(),DateUtils.DATE_FORMAT_NORMAL));
//            	}
            	memberInfo.set("birthday", DateUtils.getNowDate());
            	Cell companyName = readsheet.getCell(4, i);   
            	String companyNameStr = companyName.getContents();
            	memberInfo.set("company_name", companyNameStr.trim());
            	
            	memberInfo.set("company_id", 0L);
            	
            	Cell jobName = readsheet.getCell(5, i); 
            	String jobNameStr = jobName.getContents();
            	memberInfo.set("job_name", jobNameStr.trim());
            	
            	Cell jobYear = readsheet.getCell(6, i);
            	memberInfo.set("job_year", Long.parseLong(StringUtils.isNullOrEmpty(jobYear.getContents()) ? "2015" : jobYear.getContents() ));
            	
            	memberInfo.set("core_capacity_id", 0L);
            	
            	Cell courseIdCell = readsheet.getCell(7, i);   
            	Long courseId = Long.parseLong(StringUtils.isNullOrEmpty(courseIdCell.getContents()) ? "0" : courseIdCell.getContents());
            	
            	//信息来源
            	Cell sourseFromCell = readsheet.getCell(8, i);   
            	sourseFrom = Long.parseLong(StringUtils.isNullOrEmpty(sourseFromCell.getContents()) ? "0" : sourseFromCell.getContents());
            	
            	//购买数量
            	Cell numberCell = readsheet.getCell(9, i);   
            	Long number = Long.parseLong(StringUtils.isNullOrEmpty(numberCell.getContents()) ? "1" : numberCell.getContents());
            	
            	//邮箱
            	Cell emailCell = readsheet.getCell(10, i);   
            	String email = emailCell.getContents();
            	
            	//审核状态：0、未审核；1、审核通过；2、审核未通过
            	Cell reviewCell = readsheet.getCell(11, i);   
            	Long review = Long.parseLong(StringUtils.isNullOrEmpty(reviewCell.getContents()) ? "1" : reviewCell.getContents());
            	
            	//提问
            	Cell contentCell = readsheet.getCell(12, i);   
            	String content = contentCell.getContents();
            	
            	//提问
            	Cell priceCell = readsheet.getCell(13, i);   
            	Double price = Double.valueOf(priceCell.getContents());
            	
            	courseIds.put(member, courseId);
            	members.put(member, memberInfo);
            	sourse.put(member, sourseFrom);
            	numbers.put(member, number);
            	emails.put(member, email);
            	reviewStatus.put(member, review);
            	contents.put(member, content);
            	prices.put(member, price);
            }
            List<String> str = memberService.saveMembers(members,courseIds,sourse,numbers,emails,reviewStatus,contents,prices);
            StringBuffer buffer = new StringBuffer();
            for(String s : str){
            	buffer.append(s).append(",");
            }
            renderJson(ok("重复数据为:"+buffer.toString()));
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	//delete
	public void delete(){
		long id = getParaToLong("id",0L);
		if(NumberUtils.isNullOrZero(id)){
			renderJson(error("参数缺失"));
			return;
		}
		try{
			Member member = memberService.deleteMember(id);
			LoginUser user = getSessionAttr(PropKit.get("project.session"));
			logService.saveLog(user, 3, member.getLong("id"), "用户:"+user.getRealName()+"->删除了注册用户:" + member.getStr("mobile"));
			renderJson(ok());
		}catch(Exception e){
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
	
	// 参加线下课程列表
	public void offlineList(){
		long memberId = getParaToLong("memberId",0L);
		if(NumberUtils.isNullOrZero(memberId)){
			redirect("/member");
			return;
		}
		Member member = memberService.findMemberById(memberId);
		int page = getParaToInt("page", 1);
		List<Record> list = memberService.findOfflineCourse(page, memberId);
		list.forEach(info -> {
			info.set("cover", super.appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page,memberService.findMemberJoinCourseCount(memberId, true),"/admin/member/offlineList?memberId="+memberId));
		setAttr("member", member);
		render("offline.jsp");
	}
	
	public void onlineList(){
		long memberId = getParaToLong("memberId",0L);
		if(NumberUtils.isNullOrZero(memberId)){
			redirect("/member");
			return;
		}
		Member member = memberService.findMemberById(memberId);
		setAttr("member", member);
		int page = getParaToInt("page", 1);
		List<Record> list = memberService.findOnlineCourse(page, member.getStr("mobile"));
		list.forEach(info -> {
			info.set("cover", super.appenUrl(info.getStr("cover")));
		});
		setAttr("list", list);
		setAttr("page", new Page(page,memberService.findMemberJoinCourseCount1(member.getStr("mobile"), false).size(),"/admin/member/onlineList?memberId="+memberId));
		
		render("online.jsp");
	}
	public void onlineList1() throws UnsupportedEncodingException{
		String mobile = getPara("memberId");
		String name = getPara("memberId");
		List<Record> list = memberService.findOnlineCourseAll(mobile);
//		List<Record> list = memberService.findComment(mobile);
//		if(list.size()>0){
//			for(int i=0;i<list.size();i++){
////				list.get(i).set("comment", java.net.URLDecoder.decode(list.get(i).getStr("comment"),"utf-8"));
//				//查询课程讲师
//				String teachers = "";
//				String id = list.get(i).getStr("teacher_id");
//				String arr[] = id.split(",");
//				for(int j=0;j<arr.length;j++){
//					Member m = memberService.findMemberById(Long.valueOf(arr[j]));
//					if(m != null){
//						teachers += m.getStr("nick_name");
//					}
//					if(j == 1){
//						teachers = StringUtils.removeEndChar(teachers, ',');
//						if(arr.length > 100){
//							teachers = "众老师";
//						}
//						break;
//					}
//				}
//				//在每条记录上添加讲师姓名
//				list.get(i).set("teacher_id", teachers);
//			}
//		}
//		list.forEach(info -> {
//			info.set("cover", super.appenUrl(info.getStr("cover")));
//		});
		setAttr("list", list);
		render("online.jsp");
	}
	public void findAllMember(){
		String name = getPara("name","");
		JSONArray arr = new JSONArray();

		List<Record> memberList = memberService.findAllMember(name);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getLong("id") + ""));
			json.put("value", new String(obj.getStr("nickName")));
			arr.add(json);
		}
		renderJson(arr);
	}
	//查询所有的讲师列表
	public void findAllTeachers(){
		String name = getPara("name","");
		JSONArray arr = new JSONArray();

		List<Record> memberList = memberService.findAllTeachers(name);
		for (Record obj : memberList) {
			JSONObject json = new JSONObject();
			json.put("id", new String(obj.getLong("id") + ""));
			json.put("value", new String(obj.getStr("nickName")));
			arr.add(json);
		}
		renderJson(arr);
	}
}
