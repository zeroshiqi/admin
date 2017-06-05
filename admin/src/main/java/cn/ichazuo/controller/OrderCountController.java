package cn.ichazuo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.service.OrderService;

/**
 * @ClassName: OrderCountController
 * @Description: (订单统计Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class OrderCountController extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	public void index() {
		Integer page = getParaToInt("page",1);
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String city = getPara("city");
		if(startTime !=null || endTime!=null || city !=null){
			long count = orderService.findAllOrderCountByTime(startTime,endTime,city);
			List<Record> list = orderService.findAllOrderByTime(page,startTime,endTime,city);
			long officeCount = orderService.findAllOfficeCount(startTime,endTime,city);
			double moneyCount = 0;
			if(officeCount !=0){
				moneyCount = orderService.findAllMoneyCount(startTime,endTime,city);
			}
			setAttr("officeCount",officeCount);
			setAttr("moneyCount",moneyCount);
			setAttr("list",list);
			setAttr("page", new Page(page,count,"/admin/ordercount?s="+1+"&startTime="+startTime+"&endTime="+endTime+"&city="+city));
			render("ordercount.jsp");
		}else{
			long count = orderService.findAllOrderCount();
			List<Record> list = orderService.findAllOrder(page);
			long officeCount = orderService.findAllOfficeCount();
			double moneyCount = orderService.findAllMoneyCount();
			setAttr("officeCount",officeCount);
			setAttr("moneyCount",moneyCount);
			setAttr("list",list);
			setAttr("page", new Page(page,count,"/admin/ordercount?s=1"));
			render("ordercount.jsp");
		}
		
	}
	
//	public void month(){
//		JSONObject obj = new JSONObject();
//		List list =orderService.findMonthList();
//		List price = orderService.findPriceByMonth();
//		List orders = orderService.findOrdersByMonth();
//		obj.put("month",list);
//		obj.put("price",price);
//		obj.put("orders", orders);
//		renderJson(obj);
//	}
	
}
