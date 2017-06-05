package cn.ichazuo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jfinal.aop.Duang;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.LoginUser;
import cn.ichazuo.common.utils.model.Page;
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
public class BusinessController extends BaseController {
	private BusinessService businessService = Duang.duang(BusinessService.class);
	private LogService logService = Duang.duang(LogService.class);

	public void index() {
		int page = getParaToInt("page", 1);
		String name = getPara("name");
		int newtype = 0;
		List<Record> list = businessService.findBusinessList(page, name);
		long count = businessService.findBusinessCount(name);
		setAttr("list", list);
		setAttr("page", new Page(page, count, "/admin/course/company?s=1"));
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
	
	public void edit(){
		Long id = getParaToLong("id",0L);
		if(!NumberUtils.isNullOrZero(id)){
			setAttr("record", businessService.find(id));
		}
		render("editEmployee.jsp");
	}
}
