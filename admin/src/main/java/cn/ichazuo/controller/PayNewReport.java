package cn.ichazuo.controller;

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
public class PayNewReport extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	public void index() {
		render("paynew.jsp");
	}
	//复购率
	public void Complex(){
		String beginTime = getPara("beginTime");
		String endTime = getPara("endTime");
		JSONObject obj = new JSONObject();
		//cou数量集合
		List ccList =orderService.findPayNewCCList(beginTime,endTime);
		//cou集合
		List couList = orderService.findPayNewCouList(beginTime,endTime);
		Long couAll = orderService.findPayNewCouALl(endTime).longValue();
		Long couTime = orderService.findPayNewCouALl1(beginTime,endTime).longValue();
		float complexRate = 0;
//		if(zong!=0){
//			complexRate = ((cou * 100)/zong);
//		}
		//求复购率
		//将结果放入Json对象 返回到页面
		obj.put("complexRate",complexRate);
		obj.put("couList", couList);
		obj.put("ccList",ccList);
		obj.put("cou", couAll+125);
		obj.put("couTime", couTime);
		renderJson(obj);
	}
}
