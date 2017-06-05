package cn.ichazuo.controller;

import com.jfinal.aop.Duang;

import cn.ichazuo.common.base.BaseController;
import cn.ichazuo.common.utils.DateUtils;
import cn.ichazuo.common.utils.NumberUtils;
import cn.ichazuo.common.utils.StringUtils;
import cn.ichazuo.common.utils.model.Page;
import cn.ichazuo.model.PushLog;
import cn.ichazuo.service.CourseService;
import cn.ichazuo.service.PushService;
import cn.ichazuo.service.PushService.ClientEnum;

/**
 * @ClassName: PushController 
 * @Description: (推送Controller) 
 * @author ZhaoXu
 * @date 2015年8月19日 下午8:04:22 
 * @version V1.0
 */
public class PushController extends BaseController{
	//推送Service 
	private PushService pushService = Duang.duang(PushService.class);
	private CourseService courseService = Duang.duang(CourseService.class);
	
	//列表
	public void index(){
		Integer page = getParaToInt("page",1);
		long count = pushService.findPushLogCount();
		
		setAttr("list",pushService.findPushLog(page));
		setAttr("page", new Page(page,count,"/admin/push?s=1"));
		render("push.jsp");
	}
	
	// 修改
	public void edit(){
		setAttr("list", courseService.findAllOnlineCourse());
		render("edit.jsp");
	}
	
	
	// 保存
	public void save(){
		Integer type = getParaToInt("type");
		String content = getPara("content");
		Long courseId = getParaToLong("courseId",0L);
		if(type == null || StringUtils.isNullOrEmpty(content)){
			renderJson(error("参数错误"));
			return;
		}
		if(type == 0 && NumberUtils.isNullOrZero(courseId)){
			renderJson(error("参数错误"));
			return;
		}
		
		PushLog log = new PushLog();
		log.set("content", content);
		log.set("create_at", DateUtils.getNowDate());
		log.set("type", type);
		
		try {
			
			pushService.savePushLog(log);
			pushService.pushIOSMessage(ClientEnum.ChaZuo, content, type, courseId);
			
			renderJson(ok());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(error("系统异常"));
		}
	}
}
