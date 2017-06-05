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
public class ComplexReport extends BaseController {
	private OrderService orderService = Duang.duang(OrderService.class);
	public void index() {
		render("complex.jsp");
	}
	//复购率
	public void Complex(){
		JSONObject obj = new JSONObject();
		//cou数量集合
		List ccList =orderService.findComplexCCList();
		//cou集合
		List couList = orderService.findComplexCouList();
		//cou数量
		Long cou = orderService.findComplexCou();
		//总数量
		Long zong = orderService.findComplex();
		float complexRate = 0;
		if(zong!=0){
			complexRate = ((cou * 100)/zong);
		}
		//求复购率
		//将结果放入Json对象 返回到页面
		obj.put("complexRate",complexRate);
		obj.put("couList", couList);
		obj.put("ccList",ccList);
		obj.put("cou", cou);
		obj.put("zong", zong);
		renderJson(obj);
	}
}
