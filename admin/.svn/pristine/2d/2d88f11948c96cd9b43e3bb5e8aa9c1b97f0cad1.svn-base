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
 * @ClassName: ComplexReport
 * @Description: (订单统计Controller)
 * @author LiDongYang
 * @date 2016-3-11 15:14:09
 * @version V1.0
 */
public class OrderReport extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	public void index() {
		render("orderreport.jsp");
	}
	//订单金额月报表
	public void OrderReport(){
		JSONObject obj = new JSONObject();
		//nian集合
		List nianList =orderService.findOrderNianList();
		System.out.println(nianList);
		//yue集合
		List yueList = orderService.findOrderMonthList();
		System.out.println(yueList);
		List ym = new ArrayList();
		for(int i=0;i<nianList.size();i++){
			String a =nianList.get(i).toString()+yueList.get(i);
			ym.add(i,a);
		}
		System.out.println(ym);
		//每月的金额
		List cou = orderService.findOrderZongList();
//		float cou =	0;	
		//将结果放入Json对象 返回到页面
		obj.put("ym",ym);
		obj.put("cou", cou);
		renderJson(obj);
	}
}
