package cn.ichazuo.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.Member;
import cn.ichazuo.service.MemberService;
import cn.ichazuo.service.OrderService;

/**
 * @ClassName: ComplexReport
 * @Description: (订单统计Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class RegisterReport extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	private MemberService memberService = Duang.duang(MemberService.class);

	public void index() {
		render("register.jsp");
	}
	//注册用户数量日报
	public void Report(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//日期
		List list =orderService.findRegisterDateList(beginTime,endTime);
		//数量集合
		List couList = orderService.findRegisterCountList(beginTime,endTime);
		//数量集合
		Long count =orderService.findRegisterCountAllList(endTime).longValue();
		//
		Long countTime = orderService.findRegisterCountAllList1(beginTime,endTime).longValue();
		obj.put("couList", couList);
		obj.put("list",list);
		obj.put("countAll",count-58);
		obj.put("countTime",countTime);
		renderJson(obj);
	}
	
	//注册用户数时段分布
	public void ReportHour(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//日期
		List list =orderService.findRegisterHourList(beginTime,endTime);
		//数量集合
		List couList = orderService.findRegisterHourCountList(beginTime,endTime);
		obj.put("couList", couList);
		obj.put("list",list);
		renderJson(obj);
	}
	public void active() {
		render("active.jsp");
	} 
	//用户日活
	public void activeReport(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//日期
		List list =orderService.findActiveDateList(beginTime,endTime);
		//数量集合
		List couList = orderService.findActiveCountList(beginTime,endTime);
		//时段内的总活跃人数
		List couAll = orderService.findActiveCountAllList(beginTime, endTime);
		//唤醒人数数量集合
		List acouList = orderService.findAwakenCountList(beginTime,endTime);
		obj.put("couList", couList);
		obj.put("acouList", acouList);
		obj.put("list",list);
		obj.put("listALl",couAll.size());
		renderJson(obj);
	}
	//App唤醒报表
	public void awaken() {
		render("awaken.jsp");
	} 
	//App唤醒
	public void awakenReport(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//日期
		List list =orderService.findAwakenDateList(beginTime,endTime);
		//数量集合
		List couList = orderService.findAwakenCountList(beginTime,endTime);
		//时段内的总活跃人数
		List couAll = orderService.findAwakenCountAllList(beginTime, endTime);
		List scouList = orderService.findActiveCountList(beginTime,endTime);
		obj.put("couList", couList);
		obj.put("list",list);
		obj.put("scouList", scouList);
		obj.put("listALl",couAll.size());
		renderJson(obj);
	}
	//日付费人数
	public void daily() {
		render("dailyPay.jsp");
	} 
	//用户日活
	public void dailyReport(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//日期
		List list =orderService.findDailyPayDateList(beginTime,endTime);
		//数量集合
		List couList = orderService.findDailyPayCountList(beginTime,endTime);
		//时段内的总活跃人数
		List couAll = orderService.findDailyPayCountAllList(beginTime, endTime);
		obj.put("couList", couList);
		obj.put("list",list);
		obj.put("listALl",couAll.size());
		renderJson(obj);
	}
	
	//线上课程复购率
	public void purchaseRate() {
		render("purchaseRate.jsp");
	} 
	
	//复购率
	public void rate(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//横坐标
		List ccList =orderService.findRateCountList(beginTime,endTime);
		//纵坐标
		List couList = orderService.findRateNumberList(beginTime,endTime);
		//cou数量
		int cou = orderService.findRateCou(beginTime,endTime).size();
		//总数量
		int zong = orderService.findRate(beginTime,endTime).size();
//		float complexRate = 0;
//		if(zong!=0){
//			complexRate = ((cou * 100)/zong);
//		}
		//求复购率
		//将结果放入Json对象 返回到页面
		obj.put("couList", couList);
		obj.put("ccList",ccList);
		obj.put("cou", cou);
		obj.put("zong", zong);
		renderJson(obj);
	}
	//销售数据日报表
	public void yesSale(){
		render("yesSale.jsp");
	}
	//复购率
	public void yesSaleRate(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//Web订单总金额和数量
		int webOrderCount = 0;
		double webOrderPriceSum=0.0;
		//App订单总金额和数量
		int appOrderCount = 0;
		double appOrderPriceSum=0.0;
		//查询Web线上课程订单
		List<Record> webOrderList =orderService.findWebOnlineOrder(beginTime,endTime);
		//新增付费人数总量
		Long couAll = orderService.findPayNewCouALl(endTime).longValue();
		//付费人数总量
		int buyCount = orderService.findBuyCouALl(beginTime,endTime).size();
		//时段内新增付费人数
		Long couTime = orderService.findPayNewCouALl1(beginTime,endTime).longValue();
		//注册总人数
		Long count =orderService.findRegisterCountAllList(endTime).longValue();
		//时段内注册总人数
		Long countTime = orderService.findRegisterCountAllList1(beginTime,endTime).longValue();
		//时段内唤醒App人数
		int awakenCount = orderService.findAwakenList(beginTime,endTime).size();
		//时段内学习人数
		int studyCount = orderService.findStudyList(beginTime,endTime).size();
		if(webOrderList.size()>0){
			for(int i=0;i<webOrderList.size();i++){
				//计算总价格
//				webOrderPriceSum+=webOrderList.get(i).getBigDecimal("price").doubleValue();
				webOrderPriceSum = addDouble(webOrderPriceSum,webOrderList.get(i).getBigDecimal("price").doubleValue());
				//查询课程讲师
//				String teachers = "";
//				String id = webOrderList.get(i).getStr("teacher_id");
//				String arr[] = id.split(",");
//				for(int j=0;j<arr.length;j++){
//					Member m = memberService.findMemberById(Long.valueOf(arr[j]));
//					if(m != null){
//						teachers += m.getStr("nick_name");
//						teachers += ",";
//					}
//					if(j == 1){
//						teachers = StringUtils.removeEndChar(teachers, ',');
//						if(arr.length > 1){
//							teachers = "众老师";
//						}
//						break;
//					}
//				}
//				//在每条记录上添加讲师姓名
//				webOrderList.get(i).set("teachers", teachers);
			}
		}
		webOrderCount=webOrderList.size();
		//查询App线上课程订单
		List<Record> appOrderList = orderService.findAppOnlineOrder(beginTime,endTime);
		if(appOrderList.size()>0){
			for(int i=0;i<appOrderList.size();i++){
				//计算总价格
//				appOrderPriceSum+=appOrderList.get(i).getBigDecimal("price").doubleValue();
				appOrderPriceSum = addDouble(appOrderPriceSum,appOrderList.get(i).getBigDecimal("price").doubleValue());
				//查询课程讲师
//				String teachers = "";
//				String id = webOrderList.get(i).getStr("teacher_id");
//				String arr[] = id.split(",");
//				for(int j=0;j<arr.length;j++){
//					Member m = memberService.findMemberById(Long.valueOf(arr[j]));
//					if(m != null){
//						teachers += m.getStr("nick_name");
//						teachers += ",";
//					}
//					if(j == 1){
//						teachers = StringUtils.removeEndChar(teachers, ',');
//						if(arr.length > 1){
//							teachers = "众老师";
//						}
//						break;
//					}
//				}
//				//在每条记录上添加讲师姓名
//				webOrderList.get(i).set("teachers", teachers);
			}
		}
		appOrderCount=appOrderList.size();
		List<Record> orderFromList = orderService.findAppOnlineOrderByFrom(beginTime,endTime);
		if(orderFromList.size()>0){
			for(int i=0;i<orderFromList.size();i++){
				//根据from1的值查询渠道总订单和总金额
				String from1 =orderFromList.get(i).get("from1");
				String courseName = orderFromList.get(i).get("course_name");
				//添加当天销售数据
				List<Record> fOrderList = orderService.findOrderCountByFrom1(beginTime,endTime,from1,courseName);
				//当天销售总单量
				orderFromList.get(i).set("cou", fOrderList.get(0).get("cou"));
				//单价
				orderFromList.get(i).set("price", fOrderList.get(0).get("price"));
				//当天销售总金额
				orderFromList.get(i).set("sprice", fOrderList.get(0).get("sprice"));
				//课程名称
				orderFromList.get(i).set("courseName", fOrderList.get(0).get("course_name"));
				List<Record> fOrderAllList = orderService.findOrderAllCountByFrom1(from1,courseName,endTime);
				//累计销售总单量
				orderFromList.get(i).set("couALl", fOrderAllList.get(0).get("cou"));
				//累计销售总金额
				orderFromList.get(i).set("spriceAll", fOrderAllList.get(0).get("sprice"));
				List<Record> spreadList = orderService.findSpreadByFrom1(from1);
				if(spreadList.size()>0){
					//推广方
					orderFromList.get(i).set("promotionParty", spreadList.get(0).get("promotion_party"));
					//合作方式
					orderFromList.get(i).set("cooperationMode", spreadList.get(0).get("cooperation_mode"));
					//课程名字
					orderFromList.get(i).set("course_name", spreadList.get(0).get("course_name"));
				}else{
					//推广方
					orderFromList.get(i).set("promotionParty", "(暂未记录)");
					//合作方式
					orderFromList.get(i).set("cooperationMode", "(暂未记录)");
				}
			}
		}
		//总的注册人数
		Long registerCount =orderService.findRegisterCountAllList(endTime).longValue();
		//将结果放入Json对象 返回到页面
		obj.put("orderCount", appOrderCount+webOrderCount);
		obj.put("webOrderPriceSum",webOrderPriceSum);
		obj.put("appOrderPriceSum",appOrderPriceSum);
		obj.put("webOrderCount",webOrderCount);
		obj.put("appOrderCount",appOrderCount);
		obj.put("orderPriceSum",addDouble(webOrderPriceSum,appOrderPriceSum));
		obj.put("registerCount", registerCount-58);
		obj.put("count", count);
		obj.put("countTime", countTime);
		obj.put("couAll", couAll+125);
		obj.put("couTime", couTime);
		obj.put("awakenCount", awakenCount);
		obj.put("studyCount", studyCount);
		obj.put("buyCount", buyCount);
		obj.put("orderFromList", orderFromList);
//		obj.put("zong", zong);
		renderJson(obj);
	}
}
